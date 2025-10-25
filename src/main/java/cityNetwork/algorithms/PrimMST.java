package cityNetwork.algorithms;

import cityNetwork.model.Edge;
import cityNetwork.model.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PrimMST {

    public static class Result {
        public List<Edge> edges;
        public int totalCost;
        public long operationsCount;
        public long executionTimeMs;
        public int vertexCount;
        public int edgeCount;

        @Override
        public String toString() {
            return "MST cost = " + totalCost + "; vertices = " + vertexCount +
                    "; edges = " + edgeCount + "; operationsCount = " + operationsCount +
                    "; executionTimeMs = " + executionTimeMs +" ms" + " listOfEdges: " + edges;
        }
    }

    public Result findPrimMST(Graph graph) {
        long startTime = System.currentTimeMillis();

        int vertices = graph.vertices;
        boolean[] visited = new boolean[vertices];
        List<Edge> mst = new ArrayList<>();

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight)); //edge queue in ascending weight order

        visited[0] = true;
        for (Edge e: graph.getEdges()) {
            if (e.from == 0 || e.to == 0) { //add all edges from 0
                pq.add(e);
            }
        }

        long operations = 0;
        int totalCost = 0;


        while (!pq.isEmpty() && mst.size() < vertices -1){
            Edge minEdge = pq.poll();
            operations++;

            int next = -1;

            if(visited[minEdge.from] && !visited[minEdge.to]) {
                next = minEdge.to;
            }else if (!visited[minEdge.from] && visited[minEdge.to]) {
                next = minEdge.from;
            }else{
                continue;
            }

            mst.add(minEdge); //add an edge to the MST
            totalCost += minEdge.weight;
            visited[next] = true;

            for(Edge e : graph.getEdges()) { //add all edges leading from the new vertex
                if((e.from == next && !visited[e.to]) || (e.to == next && !visited[e.from])) {
                    pq.add(e);
                }
            }
        }

        Result resultR = new Result();
        resultR.edges = mst;
        resultR.totalCost = totalCost;
        resultR.operationsCount = operations;
        resultR.executionTimeMs = System.currentTimeMillis() - startTime;
        resultR.vertexCount = vertices;
        resultR.edgeCount = graph.getEdges().size();

        return resultR;
    }

}
