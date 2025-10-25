package cityNetwork.reader;

import cityNetwork.model.Graph;
import com.google.gson.Gson;
import java.io.Reader;
import java.util.List;

public class GraphReader {
    public static Graph readGraph(Reader reader) {
        Gson gson = new Gson();
        Root root = gson.fromJson(reader, Root.class);

        GraphData graphData = root.graphs.get(0);

        Graph graph = new Graph(graphData.nodes.size());
        for (EdgeData e : graphData.edges){
            graph.addEdge(e.from, e.to, e.weight);
        }
        return graph;
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
