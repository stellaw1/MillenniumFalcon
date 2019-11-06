package cpen221.mp2;

import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.Vertex;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class GraphTest {

    //test Graph methods that involve Vertices
    @Test
    public void testVertices() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v4id = new Vertex (4, "E");

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

        assertFalse(g.addVertex(v4id));

        Set<Vertex> expectedVertices = new HashSet<>();
        expectedVertices.add(v1);
        expectedVertices.add(v2);
        expectedVertices.add(v3);
        expectedVertices.add(v4);

        assertEquals(expectedVertices, g.allVertices());

        Assert.assertTrue(g.remove(v1));

    }

    //test Graph methods that involve Edges
    @Test
    public void testEdges() {
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

        Set<Edge> expectedEdges = new HashSet<>();
        expectedEdges.add(e1);
        expectedEdges.add(e2);
        expectedEdges.add(e3);

        assertEquals(expectedEdges, g.allEdges());

        expectedEdges.remove(e2);
        assertEquals(expectedEdges, g.allEdges(v1));

        assertEquals(e3, g.getEdge(v1, v4));
    }

    //testing some IGraph methods with null parameters
    @Test
    public void testGraphElements(){
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");
        Vertex v6 = new Vertex(6, "F");
        Vertex v7 = new Vertex(7, "G");
        Vertex v8 = new Vertex(8, "H");

        Vertex fakeVertex = new Vertex(100, "fake vertex");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 1);
        Edge<Vertex> e3 = new Edge<>(v3, v4, 1);
        Edge<Vertex> e4 = new Edge<>(v2, v4, 1);
        Edge<Vertex> e5 = new Edge<>(v2, v6, 1);
        Edge<Vertex> e6 = new Edge<>(v2, v5, 10);
        Edge<Vertex> e7 = new Edge<>(v5, v6, 10);
        Edge<Vertex> e8 = new Edge<>(v4, v6, 10);
        Edge<Vertex> e9 = new Edge<>(v6, v7, 10);
        Edge<Vertex> e10 = new Edge<>(v7, v8, 10);

        Edge<Vertex> fakeEdge = new Edge<>(fakeVertex, v1);
        Edge<Vertex> edgeToRemove = new Edge<>(v2, v7, 200);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addVertex(v7);
        g.addVertex(v8);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);
        g.addEdge(e7);
        g.addEdge(e8);
        g.addEdge(e9);
        g.addEdge(edgeToRemove);

        Assert.assertTrue(g.addEdge(e10));

        //adding null edge or vertex to graph returns false
        Assert.assertFalse(g.addEdge(null));
        Assert.assertFalse(g.addVertex(null));

        //adding edge with a vertex that does not exist in graph g returns false
        Assert.assertFalse(g.addEdge(fakeEdge));

        //get an edge that does not exit in graph returns false
        Assert.assertFalse(g.edge(v1, fakeVertex));

        //getting length of edge from vertex not in graph g returns false
        Assert.assertEquals(0, g.edgeLength(fakeVertex, v2));

        //Remove an edge in graph g returns true
        Assert.assertTrue(g.remove(edgeToRemove));

        //Removing edge not in graph g returns false
        Assert.assertFalse(g.remove(fakeEdge));

        //Removing vertex not in graph g returns false
        Assert.assertFalse(g.remove(fakeVertex));

        //Finding an edge with an endpoint not in graph g returns null
        assertNull(g.getEdge(v1, fakeVertex));
    }

    //test Graph.getNeighbours
    @Test
    public void testNeighbours() {
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

        Map<Vertex, Edge<Vertex>> expectedMap = new HashMap<>();
        expectedMap.put(v2, e1);
        expectedMap.put(v3, e3);

        Map<Vertex, Edge<Vertex>> neighbourMap = g.getNeighbours(v1);

        assertEquals(expectedMap, neighbourMap);
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

        List<Edge<Vertex>> actualMST = g.minimumSpanningTree();

        for (int i = 0; i < actualMST.size(); i++) {
            assertTrue(expectedMST.contains(actualMST.get(i)));
        }

        for (int i = 0; i < expectedMST.size(); i++) {
            assertTrue(actualMST.contains(expectedMST.get(i)));
        }
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

        Set<Vertex> expectedNodes = new HashSet<>();
        expectedNodes.add(v2);
        expectedNodes.add(v3);
        expectedNodes.add(v4);

        assertEquals(expectedNodes, g.search(v1, 3));
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
