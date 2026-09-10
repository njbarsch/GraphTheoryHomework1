//This is the edge class.  It defines the connection between 2 verticies.

public class Edge {
    //in the hashmap, what the src vertex is pointing to
    private String destVertex;
    //the weight of the path
    private int weight;

    public Edge(String aDestVert, int aWeight) {
        destVertex = aDestVert;
        weight = aWeight;
    }

    public String getDestVertName() {
        return destVertex;
    }

    public int getWeight() {
        return weight;
    }

    public void setDestVertName(String newDestVertex) {
        destVertex = newDestVertex;
    }

    public void setWeight(int aWeight) {
        weight = aWeight;
    }

    //String builder?
    @Override
    public String toString() {
        String tuple = "(";
        tuple += destVertex;
        tuple += ", ";
        tuple += weight;
        tuple += ')';
        return tuple;
    }

}