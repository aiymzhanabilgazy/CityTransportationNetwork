package cityNetwork.model;

public class Edge implements Comparable<Edge> {
    public int src;
    public int dest;
    public int weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
    @Override
    public String toString() {
        return "Edge [src=" + src + ", dest=" + dest + ", weight=" + weight + "]";
    }

}
