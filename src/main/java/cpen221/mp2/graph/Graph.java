package cpen221.mp2.graph;

import java.util.*;


/**
 * Represents a mutable graph with vertices of type V.
 *
 * @param <V> represents a vertex type
 * @param <E> represents an edge type
 */
public class Graph<V extends Vertex, E extends Edge<V>> implements ImGraph<V, E>, IGraph<V, E> {

    /** Set of all vertices in graph */
    private HashSet<V> vertexSet = new HashSet<>();

    /** Set of all edges in graph */
    private HashSet<E> edgeSet = new HashSet<>();

    // Representation Invariant
    //      E instanceof Edge<V> && V instanceof Vertex.
    //
    // Abstraction Function:
    //      represents a simple, undirected graph g where
    //      g.vertexSet represents all the nodes in g and
    //      g.edgeSet represents all the node-to-node connections that exist in g



    ////////// methods from IGraph Interface //////////
    /**
     * Add vertex to the graph if no vertex with the same id already exists in graph.
     *
     * @param v vertex to add
     * @return true if the vertex was added successfully and false otherwise
     */
    public boolean addVertex(V v) {
        if (v == null) {
            return false;
        }
        for (V ver: vertexSet) {
            if (v.checkID(ver)) {
                return false;
            }
        }
        vertexSet.add(v);
        return vertexSet.contains(v);
    }

    /**
     * Checks if a vertex is part of the graph
     *
     * @param v vertex to check in the graph
     * @return true of v is part of the graph and false otherwise
     */
    public boolean vertex(V v) {
        return vertexSet.contains(v);
    }

    /**
     * Add an edge of the graph and check if added successfully.
     *
     * @param e the edge to add to the graph
     * @return true if the edge was successfully added and false otherwise
     */
    public boolean addEdge(E e) {
        if (e == null) {
            return false;
        }
        if (edgeSet.contains(e)) {
            return false;
        }
        if (vertexSet.contains(e.v2()) && vertexSet.contains(e.v1())) {
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
     * Check if v1-v2 or v2-v1 is an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return true if the v1-v2 or v2-v1 edge is part of the graph and false otherwise
     */
    public boolean edge(V v1, V v2) {
        for (E e : edgeSet) {
            if (e.v1().equals(v1) && e.v2().equals(v2) || e.v1().equals(v2) && e.v2().equals(v1)) {
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
     * @return the length of the v1-v2 or v2-v1 edge, if this edge is part of the graph
     *              and 0 otherwise
     */
    public int edgeLength(V v1, V v2) {
        if (edge(v1, v2) || edge(v2, v1)) {
            for (E e : edgeSet) {
                if (e.v1().equals(v1) && e.v2().equals(v2)
                        || e.v1().equals(v2) && e.v2().equals(v1)) {
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
        for (E e: edgeSet) {
            sum += e.length();
        }
        return sum;
    }

    /**
     * Remove an edge from the graph and check if removed successfully.
     *
     * @param e the edge to remove
     * @return true if e was successfully removed and false otherwise
     */
    public boolean remove(E e) {
        if (edge(e)) {
            edgeSet.remove(e);
            return true;
        }
        return false;
    }

    /**
     * Remove a vertex from the graph and check if removed successfully.
     * Also remove all edges associated with that vertex if removed successfully.
     *
     * @param v the vertex to remove
     * @return true if v was successfully removed and false otherwise
     */
    public boolean remove(V v) {
        if (vertex(v)) {
            vertexSet.remove(v);
            for (E e: this.allEdges()) {
                if (e.incident(v)) {
                    edgeSet.remove(e);
                }
            }
            return true;
        }
        return false;
    }


    /**
     * Obtain a set of all vertices in the graph.
     * Access to this set does not permit graph mutations.
     *
     * @return a set of all vertices in the graph
     */
    public Set<V> allVertices() {
        return new HashSet<>(vertexSet);
    }

    /**
     * Obtain a set of all edges incident on v.
     * Access to this set does not permit graph mutations.
     *
     * @param v the vertex of interest
     * @return all edges incident on v
     */
    public Set<E> allEdges(V v) {
        Set<E> thisESet = new HashSet<>();
        for (E e : edgeSet) {
            if (e.incident(v)) {
                thisESet.add(e);
            }
        }
        return thisESet;
    }

    /**
     * Obtain a set of all edges in the graph.
     * Access to this set does not permit graph mutations.
     *
     * @return all edges in the graph
     */
    public Set<E> allEdges() {
        return new HashSet<>(edgeSet);
    }

    /**
     * Obtain all the neighbours of vertex v.
     * Access to this map may permit graph mutations.
     *
     * @param v is the vertex whose neighbourhood we want.
     * @return a map containing each vertex w that neighbors v and the edge between v and w.
     */
    public Map<V, E> getNeighbours(V v) {
        Map<V, E> neighbourMap = new HashMap<>();
        for (E e : edgeSet) {
            if (e.incident(v)) {
                neighbourMap.put(e.distinctVertex(v), e);
            }
        }
        return neighbourMap;
    }



    ////////// methods from ImGraph Interface //////////
    /**
     * Compute the shortest path from source to sink, using Dijkstra's algorithm
     *
     * @param source the start vertex
     * @param sink   the end vertex
     * @return the vertices, in order, on the shortest path from source to sink
     *              (both end points are part of the list)
     */
    public List<V> shortestPath(V source, V sink) {
        Set<V> visitedNodes = new HashSet<>();
        Set<V> unvisitedNodes = new HashSet<>();
        Map<V, Integer> nodeWeights = new HashMap<>();
        Map<V, V> previousNode = new HashMap<>();

        //Initialize all nodeWeights to be infinity (represented by Integer.MAX_VALUE)
        // expect source vertex is set to a weight of 0
        for (V v: vertexSet) {
            unvisitedNodes.add(v);
            if (v.equals(source)) {
                nodeWeights.put(source, 0);
            } else {
                nodeWeights.put(v, Integer.MAX_VALUE);
            }
        }

        V currentNode = source;
        while (!unvisitedNodes.isEmpty()) {
            //get the node in unvistedNodes that has the lowest weight and assign as current node
            int i = Integer.MAX_VALUE;
            for (V v : unvisitedNodes) {
                if (nodeWeights.get(v) < i) {
                    currentNode = v;
                    i = nodeWeights.get(v);
                }
            }

            //check for shorter path at each neighbour of currentNode
            // that is not already in visitedNodes
            for (V neighbourNode : getNeighbours(currentNode).keySet()) {
                if (!visitedNodes.contains(neighbourNode)) {
                    if (nodeWeights.get(currentNode) + edgeLength(currentNode, neighbourNode)
                            < nodeWeights.get(neighbourNode)) {
                        nodeWeights.replace(neighbourNode, nodeWeights.get(currentNode)
                                + edgeLength(currentNode, neighbourNode));
                        if (previousNode.containsKey(neighbourNode)) {
                            previousNode.replace(neighbourNode, currentNode);
                        } else {
                            previousNode.put(neighbourNode, currentNode);
                        }
                    }
                }
            }
            visitedNodes.add(currentNode);
            unvisitedNodes.remove(currentNode);
        }

        List<V> shortestPath = new ArrayList<>();
        V thisNode = sink;
        while (!thisNode.equals(source)) {
            shortestPath.add(0, thisNode);
            thisNode = previousNode.get(thisNode);
        }
        shortestPath.add(0, source);

        return shortestPath;
    }

    /**
     * Compute the minimum spanning tree of the graph.
     *
     * @return a list of edges that forms a minimum spanning tree of the graph
     */
    public List<E> minimumSpanningTree() {
        List<E> mstEdges = new ArrayList<>();
        List<Set<V>> unvisitedNodes = new ArrayList<>();
        E shortestEdge = null;

        for (V v: vertexSet) {
            Set<V> thisNodeSet = new HashSet<>();
            thisNodeSet.add(v);
            unvisitedNodes.add(thisNodeSet);
        }

        List<E> allEdges = new ArrayList<>(edgeSet);

        //sort allEdges by length from shortest to longest
        allEdges.sort(new Comparator<>() {
            @Override
            public int compare(E e1, E e2) {
                return e1.length() - e2.length();
            }
        });

        //add shortest edge to unvisitedNodes set only if the edge does not form a loop
        // ie its two nodes are in different sets
        // then merge the two sets that contain its two nodes
        for (E e : allEdges) {
            Set<V> v1Set = new HashSet<>();
            Set<V> v2Set = new HashSet<>();
            for (int i = 0; i < unvisitedNodes.size(); i++) {
                if (unvisitedNodes.get(i).contains(e.v1())) {
                    v1Set = unvisitedNodes.get(i);
                }
            }

            for (int j = 0; j < unvisitedNodes.size(); j++) {
                if (unvisitedNodes.get(j).contains(e.v2())) {
                    v2Set = unvisitedNodes.get(j);
                }
            }

            if (!v1Set.equals(v2Set)) {
                for (int i = 0; i < unvisitedNodes.size(); i++) {
                    if (unvisitedNodes.get(i).equals(v1Set)) {
                        for (int j = 0; j < unvisitedNodes.size(); j++) {
                            if (unvisitedNodes.get(j).equals(v2Set)) {
                                unvisitedNodes.get(i).addAll(unvisitedNodes.get(j));
                                unvisitedNodes.remove(unvisitedNodes.get(j));
                                mstEdges.add(e);
                            }
                        }
                    }
                }
            }
        }
        return mstEdges;
    }


    /**
     * Compute the length of a given path
     *
     * @param path is a List of Vertices that indicate the vertices of a given path (order matters)
     * @return integer that represents the length of path
     */
    public int pathLength(List<V> path) {
        int pathLength = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            pathLength += edgeLength(path.get(i), path.get(i + 1));
        }

        return pathLength;
    }

    /**
     * Obtain all vertices w that are no more than a path distance of range from v.
     *
     * @param v     the vertex to start the search from.
     * @param range the radius of the search.
     * @return a set of vertices that are within range of v (this set does not contain v).
     */
    public Set<V> search(V v, int range) {
        Set<V> rangeVertices = new HashSet<>();
        for (V neighborNode : getNeighbours(v).keySet()) {
            rangeVertices.addAll(searchWithMemory(neighborNode, range, rangeVertices));
        }
        rangeVertices.remove(v);

        return rangeVertices;

    }

    /**
     * Recursive helper method for Graph.search that finds all vertices w that are no more than
     * a path distance of range from v while remembering nodes that have already been searched.
     * Modifies visitedNodes by adding new nodes after visiting them
     *
     * @param v the vertex to start the search from.
     * @param range the radius of the search.
     * @param visitedNodes Set of vertices that have been searched already
     * @return a set of vertices that are within range of v (this set may contain v)
     */
    private Set<V> searchWithMemory(V v, int range, Set<V> visitedNodes) {
        Set<V> rangeVertices = new HashSet<>();
        for (V neighbourNode : getNeighbours(v).keySet()) {
            if (!visitedNodes.contains(neighbourNode)) {
                int thisEdgeLength = edgeLength(v, neighbourNode);
                if (range >= thisEdgeLength) {
                    visitedNodes.add(neighbourNode);
                    rangeVertices.add(neighbourNode);
                    rangeVertices.addAll(
                            searchWithMemory(neighbourNode, range - thisEdgeLength, visitedNodes));
                }
            }
        }
        return rangeVertices;
    }

    /**
     * Compute the diameter of the graph, namely the length of the longest shortest
     * path in the graph.
     *
     * @return the diameter of the graph. (diameter of the largest component
     *              if graph has multiple components)
     */
    public int diameter() {
        Set<Integer> shortestPaths = new HashSet<>();
        int diam = 0;

        //add all vertices to a list
        List<V> vertices = new ArrayList<>(vertexSet);

        //calculate shortest path length for each vertices pair
        for (V source: vertices) {
            for (int i = 0; i < vertices.size(); i++) {
                V sink = vertices.get(i);
                int length = 0;
                if (!sink.equals(source)) {
                    List<V> shortestPath = shortestPath(source, sink);
                    for (int j = 0; j < shortestPath.size() - 1; j++) {
                        length += edgeLength(shortestPath.get(j), shortestPath.get(j + 1));
                    }
                    shortestPaths.add(length);
                }

            }
        }

        //find longest value in shortestPaths
        for (int length: shortestPaths) {
            if (length > diam) {
                diam = length;
            }
        }

        return diam;
    }

    /**
     * Finds the edge that connects two vertices if such an edge exists.
     * This method may permit graph mutations to returned edge in edgeSet.
     *
     * @param v1 one end of the edge
     * @param v2 the other end of the edge
     * @return the edge connecting v1 and v2, and returns null
     *              if edge does not exist in graph
     */
    public E getEdge(V v1, V v2) {
        if (edge(v1, v2)) {
            for (E edge: edgeSet) {
                if (edge.v1().equals(v1) && edge.v2().equals(v2)
                        || edge.v1().equals(v2) && edge.v2().equals(v1)) {
                    return edge;
                }
            }
        }
        return null;
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
