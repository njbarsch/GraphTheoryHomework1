import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class App {
    public static void main(String[] args) {
        Graph[] graphs = null;
        graphs = constructGraphs(graphs);
        printGraphs(graphs);
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
        System.out.println("Test 1: Deleting an edge from an undirected graph that exists");
        System.out.println("Before");
        graphs[0].printGraph();
        System.out.println("After");
        graphs[0].delEdge("A", "B");
        graphs[0].printGraph();
        
        System.out.println("\nTest 2: Deleting an edge from an undirected graph that does not exist");
        System.out.println("Before");
        graphs[0].printGraph();
        System.out.println("After");
        graphs[0].delEdge("A", "B");
        graphs[0].printGraph();
        
        System.out.println("\nTest 3: Deleting an edge from a directed graph that exists");
        System.out.println("Before");
        graphs[1].printGraph();
        System.out.println("After");
        graphs[1].delEdge("B", "C");
        graphs[1].printGraph();
        
        System.out.println("\nTest 4: Deleting an edge from a directed graph that does not exist");
        System.out.println("Before");
        graphs[1].printGraph();
        System.out.println("After");
        graphs[1].delEdge("B", "C");
        graphs[1].printGraph();
        
        System.out.println("\nTest 5: Deleting a vertex from a undirected graph that exists");
        System.out.println("Before");
        graphs[3].printGraph();
        System.out.println("After");
        graphs[3].delVertex("B");
        graphs[3].printGraph();

        System.out.println("\nText 6: Deleting a vertex from an undirected graph that does not exist");
        System.out.println("Before");
        graphs[3].printGraph();
        System.out.println("After");
        graphs[3].delVertex("B");
        graphs[3].printGraph();

        System.out.println("\nTest 7: Deleting a vertex from a directed graph that exists");
        System.out.println("Before");
        graphs[4].printGraph();
        System.out.println("After");
        graphs[4].delVertex("B");
        graphs[4].printGraph();

        System.out.println("\nTest 8: Deleting a vertex from a directed graph that does not exist");
        System.out.println("Before");
        graphs[4].printGraph();
        System.out.println("After");
        graphs[4].delVertex("B");
        graphs[4].printGraph();

        System.out.println("\nTest 9: Changing an edge's weight from a directed graph that exists");
        System.out.println("Before");
        graphs[1].printGraph();
        System.out.println("After");
        graphs[1].setEdge("E", "A", 40);
        graphs[1].printGraph();

        System.out.println("\nTest 10: Changing an edge's weight from a directed graph that does not exist");
        System.out.println("Before");
        graphs[1].printGraph();
        System.out.println("After");
        graphs[1].setEdge("B", "C", 40);
        graphs[1].printGraph();

        System.out.println("\nTest 11: Deletes a self edge from a undirected graph");
        System.out.println("Before");
        graphs[5].printGraph();
        System.out.println("After");
        graphs[5].delEdge("A", "A");
        graphs[5].printGraph();
        
        System.out.println("\nTest 12: Deletes a self edge from a directed graph");
        System.out.println("Before");
        graphs[6].printGraph();
        System.out.println("After");
        graphs[6].delEdge("A", "A");
        graphs[6].printGraph();

        System.out.println("\nTest 13: Add new edge to undirected graph : source does not exist");
        System.out.println("Before");
        graphs[3].printGraph();
        System.out.println("After");
        graphs[3].setEdge("Z", "B", 1);
        graphs[3].printGraph();

        System.out.println("\nTest 14: Add new edge to undirected graph : destination does not exist");
        System.out.println("Before");
        graphs[3].printGraph();
        System.out.println("After");
        graphs[3].setEdge("A", "Z", 1);
        graphs[3].printGraph();

        System.out.println("\nTest 15: Add new edge to directed graph : source does not exist");
        System.out.println("Before");
        graphs[4].printGraph();
        System.out.println("After");
        graphs[4].setEdge("Z", "B", 40);
        graphs[4].printGraph();
        
        System.out.println("\nTest 16: Add new edge to directed graph : destination does not exist");
        System.out.println("Before");
        graphs[4].printGraph();
        System.out.println("After");
        graphs[4].setEdge("A", "Z", 40);
        graphs[4].printGraph();
            //13, add-edge, undir, no src
    //14, add-edge, undir, no dest
    //15, add-edge, dirct, no src
    //16, add-edge, dirct, no dest
    }
}