// Vertex class for Graph
public class Vertex {
    public String name; // The name of the vertex

    public int index;//This will help to identify the vertex in array
    public LinkedList<Edge> edges=new LinkedList<>(); // Stores the list of the edges connected to this node

    public LinkedList<Vertex> vertices= new LinkedList<>(); // Stores the list all adjacent vertices

    public Vertex(String name) {
        this.name = name;
    } // Constructor for vertex

    @Override
    public String toString() {
        return name;
    } // prints the name of the vertex
}
