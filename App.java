import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        Graph[] graphs = null;
        graphs = constructGraphs(graphs);
        printGraphs(graphs);
        ArrayList<String> path1d = graphs[0].depthFirstSearch("A", "I");
        ArrayList<String> path2d = graphs[1].depthFirstSearch("A", "E");
        ArrayList<String> path1b = graphs[0].breadthFirstSearch("A", "I");
        ArrayList<String> path2b = graphs[1].breadthFirstSearch("A", "E");
        System.out.println(path1d);
        System.out.println(path1b);
        System.out.println(path2d);
        System.out.println(path2b);
    }
    //Creates our graphs read in from the file, and returns an array of them
    public static Graph[] constructGraphs(Graph[] graphs) {
        File data = new File("data.csv");
        Scanner reader;
        try {
            reader = new Scanner(data);
            graphs = new Graph[reader.nextInt()];
            reader.nextLine();
            int graphIndex = 0;
            while (reader.hasNextLine()) {
                Graph graph = new Graph(reader.nextLine().equals("D"));
                String[] verticies = reader.nextLine().split(", ");
                // gives all (key, value) pairs in graph keys
                addVerticies(graph, verticies);
                for (int i = 0; i < verticies.length; i++) {
                    String[] edges = reader.nextLine().split(", ");
                    // gives all (key, value) pairs in graph list[values]
                    //printArr(edges);
                    addEdges(graph, edges);
                }
                graphs[graphIndex] = graph;
                graphIndex++;
            }
            reader.close();
            return graphs;
        } catch (FileNotFoundException e) {
            System.out.println("Failure, file not found!");
            e.printStackTrace();
        }
        //this line should hopefully never run
        return null;
    }

    //adds all the verticies of a graph to the hashmap
    public static void addVerticies(Graph graph, String[] verticies) {
        for (int i = 0; i < verticies.length; i++) {
            graph.addVertex(verticies[i]);
        }
    }

    //for each vertex in the hashmap, adds all possible destinations for the vertex
    //and their respecteve weights
    public static void addEdges(Graph graph, String[] edges) {
        for (int i = 1; i < edges.length; i += 2) {
            int weight = Integer.parseInt(edges[i + 1]);
            graph.setEdge(edges[0], edges[i], weight);
        }
    }

    public static void printGraphs(Graph[] arr) {
        System.out.print("Printing ");
        System.out.print(arr.length);
        System.out.println(" Graphs:\n---");
        for(int i = 0; i < arr.length; i++) {
            if(arr[i].knowIfDirectedGraph()) System.out.println("Is a Directed Graph");
            else System.out.println("Is Not a Directed Graph");
            arr[i].printGraph();
            System.out.println("---");
        }
        System.out.println("All Graphs Printed.\n");
    }

    //print out a string array, for debugging
    public static void printArr(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print('[');
            System.out.print(arr[i]);
            System.out.print(']');
        }
        System.out.println();
    }

    //01, del-edge, undir, exist
    //02, del-edge, undir, !exist
    //03, del-edge, dirct, exist
    //04, del-edge, dirct, !exist
    //05, del-vert, undir, exist
    //06, del-vert, undir, !exist
    //07, del-vert, dirct, exist
    //08, del-vert, dirct, !exist
    //09, chg-wght, dirct, exist
    //10, chg-wght, dirct, !exist (adds the edge, pres wanted behavior)
    //11, del-edge, undir, self
    //12, del-edge, dirct, self
    //13, add-edge, undir, no src
    //14, add-edge, undir, no dest
    //15, add-edge, dirct, no src
    //16, add-edge, dirct, no dest
    public static void testing(Graph[] graphs) {
       
    }
}