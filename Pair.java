// This class helps us to implement the dijkstra Algorithm
public class Pair implements Comparable<Pair> {
    public Vertex v; // Vertex traversing
    public String psf; // Path that has been taken to reach the vertex
    public int wsf; // The total weight of all the edges travelled through to reach the vertex

    public Pair(Vertex v, String psf, int wsf) {
        this.v = v;
        this.psf = psf;
        this.wsf = wsf;
    }

    // This allows the comparison of the pair objects
    // Used for priority queue implementation
    @Override
    public int compareTo(Pair o) {
        return this.wsf - o.wsf;
    }
}
