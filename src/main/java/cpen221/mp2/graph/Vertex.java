package cpen221.mp2.graph;

/**
 * Represents a graph vertex. Each vertex has an associated id and name.
 * No two vertices in the same graph should have the same id.
 */
public class Vertex {

    /** a unique ID to identify the Vertex */
    private final int id;

    /** a mutable name for the Vertex*/
    private String name;

    // Representation Invariant:
    //
    // Abstract Function:
    //      An instance v represents a node in a graph where
    //      v.id is a numeric ID that uniquely identifies the node
    //      v.name is the name of the node


    /**
     * Create a new vertex
     *
     * @param id   is a numeric identifier for the vertex
     * @param name is a name for the vertex
     */
    public Vertex(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vertex) {
            Vertex other = (Vertex) o;
            if (other.id == this.id && other.name.equals(this.name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + id;
    }

    public int id() {
        return id;
    }

    /**
     * Obtain the vertex name
     *
     * @return the vertex name
     */
    public String name() {
        return name;
    }

    /**
     * Update the vertex name
     *
     * @param name the new vertex name
     */
    public void updateName(String name) {
        this.name = name;
    }

    //// --- any new methods, if necessary, go below this link --- ////

    /**
     * Checks if two Vertices have the same ID
     *
     * @param v is a Vertex to check
     * @return true if v has the same id as this Vertex and false otherwise.
     */
    public boolean checkID(Vertex v) {
        if (id == v.id()) {
            return true;
        } else {
            return false;
        }
    }

}
