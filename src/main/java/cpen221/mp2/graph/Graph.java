package cpen221.mp2.graph;

import java.util.*;

/**
 * Represents a graph with vertices of type V.
 *
 * @param <V> represents a vertex type
 */
public class Graph<V extends Vertex, E extends Edge<V>> implements ImGraph<V, E>, IGraph<V, E> {

    // TODO: Implement this type

    //// add all new code above this line ////

    /**
     * This method removes some edges at random while preserving connectivity
     * <p>
     * DO NOT CHANGE THIS METHOD
     * </p>
     * <p>
     * You will need to implement allVertices() and allEdges(V v) for this
     * method to run correctly
     *</p>
     * <p><strong>requires:</strong> this graph is connected</p>
     *
     * @param rng random number generator to select edges at random
     */
    public void pruneRandomEdges(Random rng) {
        class VEPair {
            V v;
            E e;

            public VEPair(V v, E e) {
                this.v = v;
                this.e = e;
            }
        }
        /* Visited Nodes */
        Set<V> visited = new HashSet<>();
        /* Nodes to visit and the cpen221.mp2.graph.Edge used to reach them */
        Deque<VEPair> stack = new LinkedList<VEPair>();
        /* Edges that could be removed */
        ArrayList<E> candidates = new ArrayList<>();
        /* Edges that must be kept to maintain connectivity */
        Set<E> keep = new HashSet<>();

        V start = null;
        for (V v : this.allVertices()) {
            start = v;
            break;
        }
        if (start == null) {
            // nothing to do
            return;
        }
        stack.push(new VEPair(start, null));
        while (!stack.isEmpty()) {
            VEPair pair = stack.pop();
            if (visited.add(pair.v)) {
                keep.add(pair.e);
                for (E e : this.allEdges(pair.v)) {
                    stack.push(new VEPair(e.distinctVertex(pair.v), e));
                }
            } else if (!keep.contains(pair.e)) {
                candidates.add(pair.e);
            }
        }
        // randomly trim some candidate edges
        int iterations = rng.nextInt(candidates.size());
        for (int count = 0; count < iterations; ++count) {
            int end = candidates.size() - 1;
            int index = rng.nextInt(candidates.size());
            E trim = candidates.get(index);
            candidates.set(index, candidates.get(end));
            candidates.remove(end);
            remove(trim);
        }
    }
}
