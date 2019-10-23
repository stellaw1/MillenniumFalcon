package cpen221.mp2.graph;

import java.util.*;

/**
 * Represents a graph with vertices of type V.
 *
 * @param <V> represents a vertex type
 */
public class Graph<V extends Vertex, E extends Edge<V>> implements ImGraph<V, E>, IGraph<V, E> {

    HashSet<V> vertexSet = new HashSet<V>();
    HashSet<E> edgeSet = new HashSet<E>();

    public Graph (){

    }


    //methods from IGraph
    /**
     * Add a vertex to the graph
     *
     * @param v vertex to add
     * @return true if the vertex was added successfully and false otherwise
     */
    public boolean addVertex(V v) {
        vertexSet.add(v);
    }

    /**
     * Check if a vertex is part of the graph
     *
     * @param v vertex to check in the graph
     * @return true of v is part of the graph and false otherwise
     */
    public boolean vertex(V v) {
        return vertexSet.contains(v);
    }

    /**
     * Add an edge of the graph
     *
     * @param e the edge to add to the graph
     * @return true if the edge was successfully added and false otherwise
     */
    public boolean addEdge(E e) {
        if (e == null){
            return false;
        }
        if (edgeSet.contains(e)){
            return false;
        }
        if (vertexSet.contains(e.v2()) && vertexSet.contains(e.v1())){
            edgeSet.add(e);
            return true;
        }
        return false;
    }

    /**
     * Check if an edge is part of the graph
     *
     * @param e the edge to check in the graph
     * @return true if e is an edge in the graoh and false otherwise
     */
    public boolean edge(E e) {
        return edgeSet.contains(e);
    }

    /**
     * Check if v1-v2 is an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return true of the v1-v2 edge is part of the graph and false otherwise
     */

    public boolean edge(V v1, V v2) {
        for (E e : edgeSet){
            if (e.v1().equals(v1) && e.v2().equals(v2)){
                return true;
            }
        }
        return false;
    }

    /**
     * Determine the length on an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return the length of the v1-v2 edge, if this edge is part of the graph, and 0 otherwise
     */
    public int edgeLength(V v1, V v2) {
        if (edge(v1, v2)){
            for (E e : edgeSet){
                if (e.v1().equals(v1) && e.v2().equals(v2)){
                    return e.length();
                }
            }
        }
        return 0;
    }

    /**
     * Obtain the sum of the lengths of all edges in the graph
     *
     * @return the sum of the lengths of all edges in the graph
     */
    public int edgeLengthSum() {
        int sum = 0;
        for (E e: edgeSet){
            sum += e.length();
        }
        return sum;
    }

    /**
     * Remove an edge from the graph
     *
     * @param e the edge to remove
     * @return true if e was successfully removed and false otherwise
     */
    public boolean remove(E e) {
        if (edge(e)){
            edgeSet.remove(e);
            return true;
        }
        return false;
    }

    /**
     * Remove a vertex from the graph
     *
     * @param v the vertex to remove
     * @return true if v was successfully removed and false otherwise
     */
    public boolean remove(V v) {
        if (vertex(v)){
            vertexSet.remove(v);
            return true;
        }
        return false;
    }


    /**
     * Obtain a set of all vertices in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return a set of all vertices in the graph
     */
    public Set<V> allVertices() {
        return new HashSet<>(vertexSet);
        //TODO: check this is valid
    }

    /**
     * Obtain a set of all edges incident on v.
     * Access to this set **should not** permit graph mutations.
     *
     * @param v the vertex of interest
     * @return all edges incident on v
     */
    public Set<E> allEdges(V v) {
        Set<E> thisESet = new HashSet<E>();
        for (E e : edgeSet){
            if (e.incident(v)){
                thisESet.add(e);
            }
        }
        return thisESet;
    }

    /**
     * Obtain a set of all edges in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return all edges in the graph
     */
    public Set<E> allEdges() {
        return new HashSet<E>(edgeSet);
    }

    /**
     * Obtain all the neighbours of vertex v.
     * Access to this map **should not** permit graph mutations.
     *
     * @param v is the vertex whose neighbourhood we want.
     * @return a map containing each vertex w that neighbors v and the edge between v and w.
     */
    public Map<V, E> getNeighbours(V v) {
        Map<V, E> neighbourMap = new HashMap<V, E>();
        for (E e : edgeSet){
            if (e.v1().equals(v)) {
                neighbourMap.put(e.v2(), e);
            }
            else if (e.v2().equals(v)) {
                neighbourMap.put(e.v1(), e);
            }
        }
        return neighbourMap;
    }







    //ImGraph
    /**
     * Compute the shortest path from source to sink, using Dijkstra's algorithm
     *
     * @param source the start vertex
     * @param sink   the end vertex
     * @return the vertices, in order, on the shortest path from source to sink (both end points are part of the list)
     */
    public List<V> shortestPath(V source, V sink) {

//        List<V> shortestPathVertices = new ArrayList<>();
//        Set<V> visitedNodes = new HashSet<>();
//        List<V> visitingNodesOrder = new ArrayList<>();
//        Map<V, Integer> nodeWeights = new HashMap<V, Integer>();
//
//        //initialize nodeWeights to contain every vertex with every entry set to infinity and source vertex set to 0
//        for (V v: vertexSet) {
//            if (v.equals(source)) {
//                nodeWeights.put(source, 0);
//            } else {
//                nodeWeights.put(v, Integer.MAX_VALUE);
//            }
//        }
//
//        visitingNodesOrder.add(source);
//
//        //comparing node weights to corresponding edge weights and assigning nodeWeights the smaller value
//        for (V atNode: visitingNodesOrder) {
//            for (V neighbour: getNeighbours(atNode).keySet()) {
//                visitedNodes.add(neighbour);
//                nodeWeights.replace(neighbour, Math.min(nodeWeights.get(neighbour), edgeLength(atNode, neighbour) + nodeWeights.get(atNode)));
//
//                for (V nextNeighbour: getNeighbours(neighbour).keySet()) {
//                    if (!visitingNodesOrder.contains(nextNeighbour)) {
//                        visitingNodesOrder.add(nextNeighbour);
//                    }
//                }
//            }
//        }
//
//        //scroll through and find smallest sum of nodeWeights
//        V thisSmallestNeighbourNode;
//        int thisSmallestWeight = 0;
//        for (V neighbour: getNeighbours(source).keySet()) { //change source
//            if (nodeWeights.get(neighbour) ) {
//
//            }
//        }
//
//
//        return shortestPathVertices;
    }

    /**
     * Compute the minimum spanning tree of the graph.
     * See https://en.wikipedia.org/wiki/Minimum_spanning_tree
     *
     * @return a list of edges that forms a minimum spanning tree of the graph
     */
    public List<E> minimumSpanningTree() {
        List<E> mstEdges = new ArrayList<>();


        return mstEdges;
    }

    /**
     * Compute the length of a given path
     *
     * @param path indicates the vertices on the given path
     * @return the length of path
     */
    public int pathLength(List<V> path) {
        int pathLength = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            pathLength += getEdge(path.get(i), path.get(i + 1)).length();
        }

        return pathLength;
    }

    /**
     * Obtain all vertices w that are no more than a <em>path distance</em> of range from v.
     *
     * @param v     the vertex to start the search from.
     * @param range the radius of the search.
     * @return a set of vertices that are within range of v (this set does not contain v).
     */
    public Set<V> search(V v, int range) {
        Set<V> rangeVertices = new HashSet<V>();


        return rangeVertices;
    }

    /**
     * Compute the diameter of the graph.
     * <ul>
     * <li>The diameter of a graph is the length of the longest shortest path in the graph.</li>
     * <li>If a graph has multiple components then we will define the diameter
     * as the diameter of the largest component.</li>
     * </ul>
     *
     * @return the diameter of the graph.
     */
    public int diameter() {

    }

    /**
     * Find the edge that connects two vertices if such an edge exists.
     * This method should not permit graph mutations.
     *
     * @param v1 one end of the edge
     * @param v2 the other end of the edge
     * @return the edge connecting v1 and v2
     */
    public E getEdge(V v1, V v2){

    }



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
