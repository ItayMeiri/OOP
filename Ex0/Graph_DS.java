package ex0;
import java.util.Collection;
import java.util.HashMap;

public class Graph_DS implements graph{

    //Instance variables

    private HashMap<Integer, node_data> NodeMap;
    private int MC;
    private int edges;


    public Graph_DS()
    {
        this.MC = 0;
        this.edges = 0;
        this.NodeMap = new HashMap<Integer, node_data>();
    }
    @Override
    public node_data getNode(int key) {
        return NodeMap.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {

        return NodeMap.get(node1).hasNi(node2);
    }

    @Override
    public void addNode(node_data n) {
        if(n == null)
        {
            return;
        }
        NodeMap.put(n.getKey(), n);
    }

    @Override
    public void connect(int node1, int node2) {

        if(node1 == node2)
        {
           return;
        }
        if(NodeMap.get(node1).hasNi(node2))
        {
            return;
        }

        if(NodeMap.containsKey(node1) && NodeMap.containsKey(node2))
        {
            NodeMap.get(node1).addNi(NodeMap.get(node2));
            edges++;
            MC++;
        }
        else
        {

            return;
        }


    }

    @Override
    public Collection<node_data> getV() {
        return NodeMap.values();
    }

    @Override
    public Collection<node_data> getV(int node_id) {
        return NodeMap.get(node_id).getNi();
    }

    @Override
    public node_data removeNode(int key) {
        if(NodeMap.get(key) == null)
        {
            return null;
        }
        MC++;
        edges -= NodeMap.get(key).getNi().size();
        return NodeMap.remove(key);
    }

    @Override
    public void removeEdge(int node1, int node2){
        if(NodeMap.get(node1).hasNi(node2))
        {
            NodeMap.get(node1).removeNode(NodeMap.get(node2));
            edges--;
            MC++;
        }

        else return;

    }

    @Override
    public int nodeSize() {
        return NodeMap.size();
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
     * Overriding toString to print the keys.
     * @return
     */
    public String toString()
    {
        String str = "";
        for(node_data e: NodeMap.values())
        {
            str = str + e.getKey() + " ";
        }
        return str;
    }

}
