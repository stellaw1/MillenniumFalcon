package cpen221.mp2;

import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.Vertex;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GraphTest {

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

        assertEquals(e2, g.getEdge(v2, v3));
        assertEquals(21, g.shortestPath(v2, v2));
    }

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

        //assertEquals(e2, g.getEdge(v2, v3));

        List<Vertex> expectedPath = new ArrayList<>();
        expectedPath.add(v1);
        expectedPath.add(v3);
        assertEquals(expectedPath, g.shortestPath(v1, v3));
    }


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
        assertEquals(expectedPath, g.shortestPath(v1, v3));
    }


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


}
