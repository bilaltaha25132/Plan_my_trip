// Class for edge in Graphs
public class Edge{
    public int weight; //Stores the distance of the edge
    public int time; // Stores the length of time for the edge

    public int cost; // Stores the cost for the edge

    // Constructor that initializes all the values
    public Edge(Graph graph, String n1, String n2, int weight,int time,int cost) {
        this.weight = weight;
        this.time=time;
        this.cost=cost;
        int i = graph.getVertexIndex(n1); // Getting the index of the vertex1 and vertex2
        int j = graph.getVertexIndex(n2);

        //Connects the vertex with the other and adds the vertex to
        // List of adjacent vertices for a vertex
        if (i != -1 && j != -1) {
            Vertex v1 = graph.adjList.get(i);
            Vertex v2 = graph.adjList.get(j);
            v1.vertices.add(v2);
            v2.vertices.add(v1);
        }
    }

}
