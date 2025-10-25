package cityNetworkTest;

import cityNetwork.model.Graph;
import cityNetwork.model.Edge;
import cityNetwork.reader.GraphReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import cityNetwork.algorithms.KruskalMST;
import cityNetwork.algorithms.PrimMST;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.*;

public class AlgorithmsTest {
    private static final String[] DATASETS = {
            "small_graphs.json",
            "medium_graphs.json",
            "large_graphs.json",
            "extra_large_graphs.json",
    };

    @Test
    void testMSTAlgorithms() throws IOException {
        Map<String, Object> root = new LinkedHashMap<>();
        List<Map<String, Object>> resultsList = new ArrayList<>();

        int globalGraphId = 1;

        for (String dataset : DATASETS) {
            System.out.println("dataset:" + dataset);

            InputStream input = getClass().getClassLoader().getResourceAsStream(dataset);
            assertNotNull(input,"File not found");

            List<Graph> graphs = GraphReader.readAllGraph(new InputStreamReader(input));
            assertFalse(graphs.isEmpty(),"No graphs in " + dataset);

            for( Graph graph : graphs ) {
                System.out.println("graph:" + globalGraphId + "(" + graph.vertices + "vertices)");

                KruskalMST.Result kr = new KruskalMST().findKruskalMST(graph);
                PrimMST.Result pr = new PrimMST().findPrimMST(graph);

                //correctness test

                assertEquals(kr.totalCost, pr.totalCost, "Total cost must match for both algorithms");
                if (graph.vertices > 1) {
                    assertEquals(graph.vertices - 1, kr.edges.size(), "Kruskal edges must equal V-1");
                    assertEquals(graph.vertices - 1, pr.edges.size(), "Prim edges must equal V-1");
                }
                Map<String, Object> graphResult = new LinkedHashMap<>();
                graphResult.put("graph_id", globalGraphId);

                // graph's details
                Map<String, Object> inputStats = new LinkedHashMap<>();
                inputStats.put("vertices", graph.vertices);
                inputStats.put("edges", graph.getEdges().size());
                graphResult.put("input_stats", inputStats);

                // Prim
                Map<String, Object> prim = new LinkedHashMap<>();
                prim.put("mst_edges", convertEdges(pr.edges,graph));
                prim.put("total_cost", pr.totalCost);
                prim.put("operations_count", pr.operationsCount);
                prim.put("execution_time_ms", pr.executionTimeMs);
                graphResult.put("prim", prim);

                // Kruskal
                Map<String, Object> kruskal = new LinkedHashMap<>();
                kruskal.put("mst_edges", convertEdges(kr.edges,graph));
                kruskal.put("total_cost", kr.totalCost);
                kruskal.put("operations_count", kr.operationsCount);
                kruskal.put("execution_time_ms", kr.executionTimeMs);
                graphResult.put("kruskal", kruskal);

                resultsList.add(graphResult);
                globalGraphId++;

                System.out.println("Graph processed | MST cost = " + kr.totalCost);

            }
        }
        root.put("results", resultsList);

        File outputFile = new File("src/test/resources/output.json");
        outputFile.getParentFile().mkdirs();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(outputFile)) {
            gson.toJson(root, writer);
        }
        System.out.println("\n Results written to: " + outputFile.getAbsolutePath());
    }
    private List<Map<String, Object>> convertEdges(List<Edge> edges,Graph graph) {
        List<Map<String, Object>> edgeList = new ArrayList<>();
        List<String> names = graph.getNodeNames();

        for (Edge e : edges) {
            Map<String, Object> map = new LinkedHashMap<>();
            String fromName = (names != null && e.from < names.size()) ? names.get(e.from) : String.valueOf(e.from);
            String toName = (names != null && e.to < names.size()) ? names.get(e.to) : String.valueOf(e.to);
            map.put("from",fromName);
            map.put("to",toName);
            map.put("weight", e.weight);
            edgeList.add(map);
        }
        return edgeList;
    }

}
