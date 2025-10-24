package cityNetwork.algorithms;

import cityNetwork.model.Edge;
import cityNetwork.model.Graph;

import java.util.*;

public class KruskalMST {

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
                    "; edges = " + edgeCount + "; operations = " + operationsCount +
                    "; time = " + executionTimeMs + " listOfEdges: " + edges;
        }
    }

    private int find(int[] parent, int i) { //search a root
        if (parent[i] == i) {
            return i;
        }
        return find(parent, parent[i]);
    }

    private void union(int[] parent, int[] rank, int x, int y) {
        int xroot = find(parent, x);
        int yroot = find(parent, y);

        if (rank[xroot] < rank[yroot]) { //add a smaller tree to a larger one
            parent[xroot] = yroot;
        }else if(rank[xroot] > rank[yroot]){
            parent[yroot] = xroot;
        }else{
            parent[yroot] = xroot;
            rank[xroot]++;
        }
    }


    public Result findKruskalMST(Graph graph) {
        long startTime = System.currentTimeMillis();

        List<Edge> result = new ArrayList<>();
        int totalCost = 0;
        long operations = 0;

        List<Edge> edges = new ArrayList<>(graph.getEdges()); //sort edges by weights
        Collections.sort(edges);

        int[] parent = new int[graph.vertices];
        int[] rank = new int[graph.vertices];
        for (int i=0;i<graph.vertices; i++){
            parent[i] = i;
            rank[i] = 0;
        }

        int e = 0;
        int i = 0;

        while (e <graph.vertices -1 && i < edges.size()) { //v-1 edges
            Edge nextEdge = edges.get(i++);
            int x = find(parent,nextEdge.src);
            int y = find(parent,nextEdge.dest);
            operations++;

            if(x != y){
                result.add(nextEdge);
                totalCost += nextEdge.weight;
                union(parent,rank,x,y);
                e++;
            }
        }

        Result resultR = new Result();
        resultR.edges = result;
        resultR.totalCost = totalCost;
        resultR.operationsCount = operations;
        resultR.executionTimeMs = System.currentTimeMillis() - startTime;
        resultR.vertexCount = graph.vertices;
        resultR.edgeCount = edges.size();

        return resultR;
    }

}
