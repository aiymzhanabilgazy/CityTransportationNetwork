package cityNetwork.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    public int vertices;
    public List<Edge> edges;
    public List<String> nodeNames;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
        this.nodeNames = new ArrayList<>();
    }
    public void addEdge(int from, int to, int weight) {
        edges.add(new Edge(from, to, weight));

    }
    public List<Edge> getEdges() {
        return edges;
    }
    public void setNodeNames (List<String> nodeNames) {
        this.nodeNames = nodeNames;
    }
    public List<String> getNodeNames() {
        return nodeNames;
    }

}
