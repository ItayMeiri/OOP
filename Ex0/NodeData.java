package ex0;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;


public class NodeData implements node_data, Serializable {


    static int global = 0;
    private int key;
    private HashMap<Integer, node_data> neighbors;

    private String info;
    private int tag;

    public NodeData() {
        this.neighbors = new HashMap<Integer, node_data>();
        this.key = global++;
        this.info = "";
        this.tag = 0;
    }

    public NodeData(int key)
    {
        this.neighbors = new HashMap<Integer, node_data>();
        this.key = key;
        this.info = "";
        this.tag = 0;
    }

    /**
     * This is a copy constructor.
     * It creates a new NodeData with a different key but with the exact same neighbors(shallow copy) as well as the tag and info.
     * @param nd
     */
    public NodeData(node_data nd)
    {
        this.neighbors = new HashMap<Integer, node_data>();
        for(node_data copy: nd.getNi())
        {
            neighbors.put(copy.getKey(), copy);
        }
        this.key = global++;
        this.info = nd.getInfo();
        this.tag = nd.getTag();

    }


    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public Collection<node_data> getNi() {

        return neighbors.values();
    }

    @Override
    public boolean hasNi(int key) {
        return neighbors.get(key) != null;
    }

    @Override
    public void addNi(node_data t) {
        if(t == null)
        {
            return;
        }
        if(neighbors.get(t) != null)
        {
            return;
        }
        neighbors.put(t.getKey(), t);
        if(t.hasNi(this.getKey())) {
            return;
        }
        t.addNi(this);
    }

    @Override
    public void removeNode(node_data node) {
        neighbors.remove(node.getKey());
        node.getNi().remove(this);

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
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }







}
