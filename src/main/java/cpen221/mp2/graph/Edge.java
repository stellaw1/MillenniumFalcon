package cpen221.mp2.graph;

import java.util.NoSuchElementException;

public class Edge<V extends Vertex> {

    /** One endpoint of the Edge */
    private V v1;

    /** Other endpoint of the Edge */
    private V v2;

    /** Weight of the edge */
    private int length;

    // Representation Invariant:
    //      for v1: v1 != null && !v1.equals(v2)
    //      for v2: v2 != null
    //      for length: length >= 0
    //
    // Abstract Function:
    //      An instance e represents a line joining a pair of nodes where
    //      e.v1 represents one node of the edge
    //      e.v2 represents the other node of the edge
    //      e.length represents the weight of an edge (ie, cost, distance, capacity, etc)


    public Edge(V v1, V v2) {
        this(v1, v2, 1);
    }

    public Edge(V v1, V v2, int length) {
        if (v1 == null || v2 == null) {
            throw new IllegalArgumentException("Vertices cannot be null");
        }
        if (v1.equals(v2)) {
            throw new IllegalArgumentException("The same vertex cannot be at both ends of an edge");
        }
        if (length < 0) {
            throw new IllegalArgumentException("Edge weight cannot be negative");
        }
        this.v1 = v1;
        this.v2 = v2;
        this.length = length;
    }

    public V v1() {
        return v1;
    }

    public V v2() {
        return v2;
    }

    public int length() {
        return length;
    }

    public boolean equals(Object o) {
        if (o instanceof Edge<?>) {
            Edge<?> other = (Edge<?>) o;
            if (other.v1.equals(this.v1) && other.v2.equals(this.v2)) {
                return true;
            }
            if (other.v1.equals(this.v2) && other.v2.equals(this.v1)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return v1.hashCode() + v2.hashCode();
    }

    public boolean incident(V v) {
        if (v == null) {
            return false;
        }
        if (v.equals(v1) || v.equals(v2)) {
            return true;
        }
        return false;
    }

    public boolean intersects(Edge<V> e) {
        if (e == null) {
            return false;
        }
        return this.incident(e.v1) || this.incident(e.v2);
    }

    public V intersection(Edge<V> e) throws NoSuchElementException {
        if (e == null) {
            throw new NoSuchElementException("No common vertex");
        }
        if (this.v1.equals(e.v1) || this.v1.equals(e.v2)) {
            return this.v1;
        }
        if (this.v2.equals(e.v1) || this.v2.equals(e.v2)) {
            return this.v2;
        }
        throw new NoSuchElementException("No common vertex");
    }

    public V distinctVertex(V v) {
        if (this.v1.equals(v)) {
            return this.v2;
        } else {
            return this.v1;
        }
    }

    public V distinctVertex(Edge<V> e) {
        if (this.equals(e)) {
            throw new NoSuchElementException("No distinct vertex");
        }
        V sv;
        try {
            sv = this.intersection(e);
        }
        catch (NoSuchElementException nse) {
            // when there is no common vertex,
            // return any vertex (deterministic choice of v1 is okay).
            return v1;
        }
        if (v1.equals(sv)) {
            return v2;
        } else {
            return v1;
        }
    }

}
