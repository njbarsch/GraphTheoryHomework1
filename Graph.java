import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.ArrayDeque;

public class Graph {
    //hashmap is key, value
    //this will be the representation of our graph (src | (dest1, weight1), (destn, weightn))
    private HashMap<String, ArrayList<Edge>> graph;
    //is the graph directed?
    private boolean isDirectedGraph;
    
    public Graph(boolean aDirectedGraph) {
        graph = new HashMap<String, ArrayList<Edge>>();
        isDirectedGraph = aDirectedGraph;    
    }
    
    public boolean knowIfDirectedGraph() {
        return isDirectedGraph;
    }

    //adds a vertex to the map
    public void addVertex(String aName) {
        graph.put(aName, new ArrayList<Edge>());
    }
    
    //deletes a vertex from the map
    //it also deletes the node's outgoing edges
    //and it deletes the edges coming into it
    public void delVertex(String target) {
        System.out.print("Deleting vertex ");
        System.out.println(target);
        if(!graph.containsKey(target)) {
            System.out.print("Vertex ");
            System.out.print(target);
            System.out.println(" does not exist.");
            return;
        }
        graph.remove(target);
        //loop through every (src, (dest, weight)) pair in the table
        for(Map.Entry<String, ArrayList<Edge>> key_value : graph.entrySet()) {
            //for each src vetex, find all of its dest verticies
            ArrayList<Edge> edges = key_value.getValue();
            //loop through all the src's dest verticies
            for(int i = 0; i < edges.size(); i++) {
                //if any of the dest verticies is the one we deleted, get rid of it
                //break out of inner loop, as 1 vertex can't go to the same vertex twice
                if(edges.get(i).getDestVertName().equals(target)) {
                    edges.remove(i);
                    break;
                }
            }
        }
    }

    //add edge from src to dest with weight
    //if src or dest DNE, rhow Exdeption(IllegalArgumentException)
    //if edge already exists, update the weight
    public void setEdge(String src, String dest, int weight) {
        //if the vertex has no edges
        if(!graph.containsKey(src)) {
            System.out.print("Error, source vertex '");
            System.out.print(src);
            System.out.println("' does not exist");
            return;
            
        }
        if(!graph.containsKey(dest)) {
            System.out.print("Error, destination vertex '");
            System.out.print(dest);
            System.out.println("' does not exist");
            return;
            
        }
        if(graph.get(src).size() == 0) {
            //give it an empty list of edges, 
            // then insert a new edge into that list
            graph.get(src).add(new Edge(dest, weight));
        }
        else {
            ArrayList<Edge> edges = graph.get(src);
            //first, try to change the weight of an existing edge
            for(Edge edge : edges) {
                if(edge.getDestVertName().equals(dest)) {
                    System.out.print("Changing weight of edge from ");
                    System.out.print(src);
                    System.out.print("->");
                    System.out.print(dest);
                    System.out.print(" to ");
                    System.out.println(weight);
                    edge.setWeight(weight);
                    return;
                }
            }
            //if we could not change the weight of an existing edge
            //add a new edge
            graph.get(src).add(new Edge(dest, weight));
        }
    }

    //removes an edge from the map, if it exists
    public void delEdge(String src, String dest) {
        printSrcToDest(src, dest);
        //for both directed and undirected, remove src->dest
        ArrayList<Edge> dests = graph.get(src);
        int popIndex = this.getDestVertexIndex(dest, dests);
        if(popIndex == -1) {
            System.out.print("Edge to '");
            System.out.print(dest);
            System.out.println("' not found.");
            return;
        }
        dests.remove(popIndex);
        graph.put(src, dests);
        //for undirected, remove dest->src
        if(!isDirectedGraph && !src.equals(dest)) {
            dests = graph.get(dest);
            popIndex = this.getDestVertexIndex(src, dests);
            dests.remove(popIndex);
            graph.put(dest, dests);
        }
    }

    public void printSrcToDest(String aSrc, String aDest) {
        System.out.print("Deleting edge: ");
        System.out.print(aSrc);
        if(isDirectedGraph) System.out.print("->");
        else System.out.print("-");
        System.out.println(aDest);
    }

    //Prints out all verticies and their respective (dest verts, weight of path to vert)
    public void printGraph() {
        for(Map.Entry<String, ArrayList<Edge>> entry : graph.entrySet()) {
            if(entry.getValue().size() == 0) {
                System.out.print(entry.getKey());
                System.out.println(": (No connected verticies)");
            }
            else {
                System.out.print(entry.getKey());
                System.out.print(": ");
                for(int i = 0; i < entry.getValue().size(); i++) {
                    System.out.print(entry.getValue().get(i).toString());
                    if(i != entry.getValue().size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
        }
    }

    public int getDestVertexIndex(String target, ArrayList<Edge> destVerts) {
        for(int i = 0; i < destVerts.size(); i++) {
            String debug = destVerts.get(i).getDestVertName();
            if(target.equals(destVerts.get(i).getDestVertName())) {
                return i;
            }
        }
        return -1;
    }

    public Queue<String> depthFirstSearch(String startVertex) {
        //for the dfs
        Stack<String> stack = new Stack<String>();
        //our ultimate path we take
        Queue<String> path = new ArrayDeque<String>();
        //places we've gone
        Stack<String> visited = new Stack<String>();

        stack.push(startVertex);

        while(!stack.isEmpty()) {
            String vertex = stack.pop();
            if(visited.contains(vertex)) {
                continue;
            }
            visited.push(vertex);
            ArrayList<Edge> moves = graph.get(vertex);
            for(Edge e : moves) {
                stack.push(e.getDestVertName());
            }

        }
        return path;
    }
}