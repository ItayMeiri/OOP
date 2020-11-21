package ex1.src;


import java.io.Serializable;
import java.util.*;

/**
 * An implementation of the weighted_graph interface.
 */
public class WGraph_DS implements weighted_graph, Serializable {


    /**
     * Inner class NodeInfo, which implements the node_info interface and Serializable.
     * Please keep in mind that NodeInfo has additional fields to store the distance and previous nodes, these are used for various algorithms.
     */
    private class NodeInfo implements node_info, Serializable{

        final int key;
        private String info;
        private double tag;
        private double dist;
        private int prev;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeInfo nodeInfo = (NodeInfo) o;
            return key == nodeInfo.key &&
                    Double.compare(nodeInfo.tag, tag) == 0 &&
                    Objects.equals(info, nodeInfo.info);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }

        public NodeInfo(int key)
        {
            this.key = key;
        }

        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {

            this.info = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        public double getDist()
        {
            return dist;
        }

        public void setDist(double dist)
        {
           this.dist = dist;
        }

        public void setPrev(int prevKey)
        {
            this.prev = prevKey;
        }
        public int getPrev()
        {
            return prev;
        }





    }

    //instance variables

    private HashMap<Integer, node_info> nodes;
    private HashMap<Integer, HashMap<node_info, Double>> neighbors;
    private int edges;
    private int MC;
    private static final long serialVersionUID = 1L;


    /**
     * Constructor - this constructor will automatically create a list of nodes and neighbors.
     */
    public WGraph_DS()
    {
        nodes = new HashMap<Integer, node_info>();
        neighbors = new HashMap<Integer, HashMap<node_info, Double>>();
        MC = 0;
        edges = 0;
    }


    @Override
    public node_info getNode(int key) {

        return nodes.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if(node1 == node2)
        {
            return false;
        }
        return (neighbors.get(node1).get(nodes.get(node2)) != null) || (neighbors.get(node2).get(nodes.get(node1)) != null);
    }


    @Override
    public double getEdge(int node1, int node2) {

        if(nodes.get(node1) == null || nodes.get(node2) == null)
        {
            return -1;
        }
        if(node1 == node2)
        {
            return 0;
        }
        if(!this.hasEdge(node1, node2))
        {
            return -1;
        }
        return neighbors.get(node1).get(nodes.get(node2));

    }

    @Override
    public void addNode(int key) {

        if(nodes.get(key) != null)
        {
            return;
        }

        this.nodes.put(key, new NodeInfo(key));
        this.neighbors.put(key, new HashMap<node_info, Double>());
        MC++;
    }

    @Override
    public void connect(int node1, int node2, double w) {

        if(w < 0)
        {
            return;
        }
        if(nodes.get(node1) == null || nodes.get(node2) == null)
        {
            return;
        }

        if(node1 == node2)
        {
            return;
        }

//        if(neighbors.get(node1).get(node2) != null || neighbors.get(node2).get(node1) != null)
//        {
//            return;
//        }

        if(this.hasEdge(node1, node2))
        {
            neighbors.get(node1).put(nodes.get(node2), w);
            neighbors.get(node2).put(nodes.get(node1), w);
            MC++;
        }
        else
        {
            neighbors.get(node1).put(nodes.get(node2), w);
            neighbors.get(node2).put(nodes.get(node1), w);
            edges++;
            MC++;
        }

    }

    @Override
    public Collection<node_info> getV() {

        return nodes.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {

        if(nodes.get(node_id) == null)
        {
            return null;
        }
        return neighbors.get(node_id).keySet();
    }

    @Override
    public node_info removeNode(int key) {

        if(nodes.get(key) == null)
        {
            return null;
        }

        //Remove all edges from the neighbors
        for(node_info ni: this.getV(key))
        {
            neighbors.get(ni.getKey()).remove(nodes.get(key));
        }

        edges-=neighbors.get(key).size();
        neighbors.get(key).clear();
        MC++;

        return nodes.remove(key);
    }

    @Override
    public void removeEdge(int node1, int node2) {

        //TODO: MAKE SURE THIS EDGE EXISTS

        if(nodes.get(node1) == null || nodes.get(node2) == null)
        {
            return;
        }
       if(neighbors.get(node1).remove(nodes.get(node2)) == null)
       {
           return;
       }
        neighbors.get(node2).remove(nodes.get(node1));
        edges--;
        MC++;
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edges;
    }

    @Override
    public int getMC() {
        return MC;
    }

    /**
     * This function gets the 'dist' double from the inner class object for the specific node.  Please note that these may change when using algorithms on this graph.
     * @param node - the target node
     * @return the distance associated with target node.
     */
    public double getDist(int node)
    {
        return ((NodeInfo) nodes.get(node)).getDist();
    }

    /**
     * This function sets the 'dist' double from the inner class object for the specific node.  Please note that these may change when using algorithms on this graph.
     * @param node - the target node
     * @param dist - the new distance value
     */
    public void setDist(int node, double dist)
    {
        ((NodeInfo)nodes.get(node)).setDist(dist);
    }

    /**
     * This function gets the 'prev' integer from the inner class object for the specific node. Please note that these may change when using algorithms on this graph.
     * @param node - the target node
     * @return the prev stored at target node.
     */
    public int getPrev(int node)
    {
        return ((NodeInfo) nodes.get(node)).getPrev();
    }

    /**
     * This functions sets the 'prev' in the inner class object for node. Please note that these may change when using algorithms on this graph.
     * @param node - the target node
     * @param prev - the new prev value
     */
    public void setPrev(int node, int prev)
    {
        ((NodeInfo)nodes.get(node)).setPrev(prev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, neighbors, edges, MC);
    }


    /**
     * The equals function checks the edges, nodes and neighbors are equal using HashMap equal function.
     * @param o - the other WGraph_DS to compare
     * @return true if the graphs are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return edges == wGraph_ds.edges &&
                nodes.equals(wGraph_ds.nodes) &&
                neighbors.equals(wGraph_ds.neighbors);
    }
}
