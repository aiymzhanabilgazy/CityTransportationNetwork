package cityNetwork.visual;

import cityNetwork.model.Graph;
import cityNetwork.model.Edge;
import cityNetwork.reader.GraphReader;
import cityNetwork.algorithms.KruskalMST;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class GraphVisualizer extends Application {
    private static final String fileName = "small_graphs.json";

    @Override
    public void start(Stage primaryStage) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                    System.out.println("File not found: " + fileName);
                return;
            }
            List<Graph> graphs = GraphReader.readAllGraph(new InputStreamReader(inputStream));
            if (graphs.isEmpty()) {
                System.out.println("JSON file is empty");
                return;
            }
            int toDisplay = Math.min(graphs.size(), 5);
            for (int i = 0; i < toDisplay; i++) {
                Graph graph = graphs.get(i);
                Stage stage = new Stage();
                stage.setTitle("Graph " + (i + 1) + " — " + graph.vertices + " vertices");
                stage.setScene(createGraphScene(graph, 600, 400));
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Scene createGraphScene(Graph graph, int width, int height) {
        Group root = new Group();
        Random rand = new Random();

        double[][] coords = new double[graph.vertices][2];
        for (int i = 0; i < graph.vertices; i++) {
            coords[i][0] = 100 + rand.nextInt(width - 200);
            coords[i][1] = 80 + rand.nextInt(height - 160);
        }
        KruskalMST mstAlgo = new KruskalMST();
        KruskalMST.Result mstResult = mstAlgo.findKruskalMST(graph);

        Set<String> mstEdgesSet = new HashSet<>();
        for (Edge e : mstResult.edges) {
            mstEdgesSet.add(edgeKey(e.from, e.to));
            mstEdgesSet.add(edgeKey(e.to, e.from));
        }
        for (Edge e : graph.getEdges()) {
            boolean isMST = mstEdgesSet.contains(edgeKey(e.from, e.to));

            Line line = new Line(coords[e.from][0], coords[e.from][1],
                    coords[e.to][0], coords[e.to][1]);
            line.setStroke(isMST ? Color.LIMEGREEN : Color.LIGHTGRAY);
            line.setStrokeWidth(isMST ? 3 : 1.5);
            Text weightText = new Text(
                    (coords[e.from][0] + coords[e.to][0]) / 2,
                    (coords[e.from][1] + coords[e.to][1]) / 2,
                    String.valueOf(e.weight)
            );
            weightText.setFill(isMST ? Color.DARKGREEN : Color.DARKBLUE);
            root.getChildren().addAll(line, weightText);
        }
        for (int i = 0; i < graph.vertices; i++) {
            Circle c = new Circle(coords[i][0], coords[i][1], 10, Color.SKYBLUE);
            c.setStroke(Color.DARKBLUE);
            Text t = new Text(coords[i][0] - 4, coords[i][1] + 4, String.valueOf(i + 1));
            root.getChildren().addAll(c, t);
        }
        return new Scene(root, width, height, Color.WHITE);
    }
    private String edgeKey(int from, int to) {
        return from + "-" + to;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
