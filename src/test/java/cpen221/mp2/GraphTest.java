package cpen221.mp2;

import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.Vertex;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphTest {

    //test Edge.java constructor and exceptions

    //test Vertex.java constructor

    //test Graph.java constructor
    @Test
    public void testCreateGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        List<Vertex> expectedPath = new ArrayList<>();
        expectedPath.add(v3);
        expectedPath.add(v2);
        expectedPath.add(v1);
        expectedPath.add(v4);

        assertEquals(e2, g.getEdge(v2, v3));
        assertEquals(expectedPath, g.shortestPath(v3, v4));
        assertEquals(21, g.pathLength(g.shortestPath(v3, v4)));
    }

    //test Graph.allEdges and Graph.allVertices and Graph.remove
    @Test
    public void testAllEdges() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 3);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 2);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        Set<Edge> expectedEdgeSet = new HashSet<>();
        expectedEdgeSet.add(e1);
        expectedEdgeSet.add(e2);
        expectedEdgeSet.add(e3);
        assertEquals(expectedEdgeSet, g.allEdges());

        expectedEdgeSet.remove(e2);
        assertEquals(expectedEdgeSet, g.allEdges(v1));

        Set<Vertex> expectedVertexSet = new HashSet<>();
        expectedVertexSet.add(v2);
        expectedVertexSet.add(v3);
        g.remove(v1);
        assertEquals(expectedVertexSet, g.allVertices());

    }

    //testing Graph.shortestPath using Lists
    @Test
    public void testShortestDistance() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 3);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 2);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        List<Vertex> expectedPath = new ArrayList<>();
        expectedPath.add(v1);
        expectedPath.add(v3);
        assertEquals(expectedPath, g.shortestPath(v1, v3));

        assertEquals(6, g.edgeLengthSum());
    }

    //testing Graph.shortestPath using Lists and some simple IGraph methods
    @Test
    public void testShortestDistance2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 1);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 3);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        List<Vertex> expectedPath = new ArrayList<>();
        expectedPath.add(v1);
        expectedPath.add(v2);
        expectedPath.add(v3);

        assertTrue(g.edge(e1));
        assertTrue(g.vertex(v1));

        v1.updateName("test");
        assertEquals("test", v1.name());

        assertEquals(expectedPath, g.shortestPath(v1, v3));
    }

    //simple Graph.minimumSpanningTree test
    @Test
    public void testMST1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v1, v4, 2);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 3);
        Edge<Vertex> e4 = new Edge<>(v3, v4, 15);
        Edge<Vertex> e5 = new Edge<>(v2, v4, 16);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);

        List<Edge> expectedMST = new ArrayList<>();
        expectedMST.add(e1);
        expectedMST.add(e2);
        expectedMST.add(e3);

        assertEquals(expectedMST, g.minimumSpanningTree());
    }

    //simple Graph.minimumSpanningTree test
    @Test
    public void testMST2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 5);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 2);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        List<Edge> expectedMST = new ArrayList<>();
        expectedMST.add(e1);
        expectedMST.add(e3);

        assertEquals(expectedMST, g.minimumSpanningTree());
    }

    //uses for loop to test equality of MST lists to allow equality with different orders in lists
    @Test
    public void testMST3() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");
        Vertex v6 = new Vertex(6, "F");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v1, v3, 1);
        Edge<Vertex> e3 = new Edge<>(v3, v4, 1);
        Edge<Vertex> e4 = new Edge<>(v3, v5, 1);
        Edge<Vertex> e5 = new Edge<>(v3, v6, 1);
        Edge<Vertex> e6 = new Edge<>(v2, v4, 10);
        Edge<Vertex> e7 = new Edge<>(v1, v5, 10);
        Edge<Vertex> e8 = new Edge<>(v1, v5, 10);
        Edge<Vertex> e9 = new Edge<>(v5, v6, 10);


        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);
        g.addEdge(e7);
        g.addEdge(e8);
        g.addEdge(e9);

        List<Edge> expectedMST = new ArrayList<>();
        expectedMST.add(e1);
        expectedMST.add(e2);
        expectedMST.add(e3);
        expectedMST.add(e4);
        expectedMST.add(e5);

//        Collections.sort(expectedMST, new Comparator<Edge>(){
//           @Override
//           public int compare(Edge e1, Edge e2){
//               return e1.length() - e2.length();
//           }
//        });

        List<Edge<Vertex>> actualMST = g.minimumSpanningTree();

        for (int i = 0; i < actualMST.size(); i++) {
            assertTrue(expectedMST.contains(actualMST.get(i)));
        }

        //assertEquals(expectedMST, actualMST);
    }

    //testing Graph.search
    @Test
    public void testSearch(){
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");
        Vertex v6 = new Vertex(6, "F");
        Vertex v7 = new Vertex(7, "G");


        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 1);
        Edge<Vertex> e3 = new Edge<>(v2, v4, 2);
        Edge<Vertex> e4 = new Edge<>(v2, v5, 7);
        Edge<Vertex> e5 = new Edge<>(v1, v6, 7);
        Edge<Vertex> e6 = new Edge<>(v1, v7, 10);


        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addVertex(v7);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);

        Set<Vertex> expectedNodes = new HashSet<Vertex>();
        expectedNodes.add(v2);
        expectedNodes.add(v3);
        expectedNodes.add(v4);

        Assert.assertTrue(expectedNodes.equals(g.search(v1, 3)));
    }

    //test diameter method in Graph.java
    @Test
    public void testDiameter() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 2);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 4);
        Edge<Vertex> e4 = new Edge<>(v2, v4, 5);
        Edge<Vertex> e5 = new Edge<>(v3, v4, 6);


        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);

        List<Vertex> shortestPath = g.shortestPath(v1, v3);

        assertEquals(3, g.pathLength(shortestPath));
        assertEquals(6, g.diameter());
    }

}
