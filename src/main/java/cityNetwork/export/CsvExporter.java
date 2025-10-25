package cityNetwork.export;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;
import java.io.*;

public class CsvExporter {
    public static void main(String[] args) {
        String inputPath = "src/test/resources/output.json";
        String outputPath = "src/test/resources/results.csv";

        try {
            exportToCSV(inputPath, outputPath);
            System.out.println("CSV successfully exported to " + outputPath);
        }catch (Exception e){
            System.err.println("Error while exporting CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void exportToCSV(String inputPath, String outputPath) throws IOException {
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(new FileReader(inputPath), JsonObject.class);

        JsonArray results = root.getAsJsonArray("results");
        if (results == null || results.size() == 0) {
            throw new IllegalArgumentException("No results found" + inputPath);
        }

        try(PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {

            writer.println("graph_id,vertices,edges,prim_cost,kruskal_cost,prim_time_ms,kruskal_time_ms,prim_operations,kruskal_operations");

            for (JsonElement element : results) {
                JsonObject graphObj = element.getAsJsonObject();

                int graphId = graphObj.get("graph_id").getAsInt();

                JsonObject inputStats = graphObj.getAsJsonObject("input_stats");
                int vertices = inputStats.get("vertices").getAsInt();
                int edges = inputStats.get("edges").getAsInt();

                JsonObject prim = graphObj.getAsJsonObject("prim");
                JsonObject kruskal = graphObj.getAsJsonObject("kruskal");

                int primCost = prim.get("total_cost").getAsInt();
                int kruskalCost = kruskal.get("total_cost").getAsInt();

                double primTime = prim.get("execution_time_ms").getAsDouble();
                double kruskalTime = kruskal.get("execution_time_ms").getAsDouble();

                long primOps = prim.get("operations_count").getAsLong();
                long kruskalOps = kruskal.get("operations_count").getAsLong();

                String primTimeStr = String.format(Locale.US, "%.4f ms", primTime);
                String kruskalTimeStr = String.format(Locale.US, "%.4f ms", kruskalTime);


                writer.printf(Locale.US,
                        "%d,%d,%d,%d,%d,%s,%s,%d,%d%n",
                        graphId, vertices, edges,
                        primCost, kruskalCost,
                        primTimeStr, kruskalTimeStr,
                        primOps, kruskalOps
                );
            }
        }
    }
}
