package cityNetwork.reader;

import cityNetwork.model.Edge;
import cityNetwork.model.Graph;
import com.google.gson.Gson;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class GraphReader {
    public static List<Graph> readAllGraph(Reader reader) {
        Gson gson = new Gson();
        Root root = gson.fromJson(reader, Root.class);

        if (root == null || root.graphs == null || root.graphs.isEmpty()) {
            throw new IllegalArgumentException("No graphs found");
        }
        List<Graph> allGraphs = new ArrayList<>();

        for (GraphData graphData : root.graphs) {
            Graph graph = new Graph(graphData.nodes.size());
            graph.setNodeNames(graphData.nodes);
            for (EdgeData e : graphData.edges) {
                graph.addEdge(e.from, e.to, e.weight);
            }
            allGraphs.add(graph);
        }
        return allGraphs;
    }
    private static class Root {
        List <GraphData> graphs;
    }
    private static class GraphData {
        int id;
        List<String> nodes;
        List<EdgeData> edges;
    }
    private static class EdgeData {
        int from;
        int to;
        int weight;
    }
}
