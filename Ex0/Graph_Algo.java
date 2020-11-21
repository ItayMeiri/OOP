package ex0;

import java.util.*;

/**
 * Graph_Algo implements the graph_algorithm interface
 */
public class Graph_Algo implements graph_algorithms{

    private graph gr;

    @Override
    public void init(graph g) {
        if(g == null)
        {
            gr = new Graph_DS();
        }
        else
        {
            gr = g;
        }
    }

    /**
     * This constructor takes a graph of type Graph_DS that has already been created. If given null, it creates a new Graph_DS instead.
     * @param g
     */
    public Graph_Algo(graph g)
    {
        this.init(g);
    }

    /**
     * This constructor builds a new Graph_DS on its own.
     */
    public Graph_Algo()
    {
        init(null);
    }

    @Override
    public graph copy() {

        graph g = new Graph_DS(); // The new graph we'll return

        for(node_data nd: gr.getV())
        {
            node_data temp = new NodeData(nd.getKey());

            /*
            Checking if a NodeData has been added already.
            We do this because inside the inner loop we're adding neighbors to the graph.
            This means some of the times 'nd' was already added as a neighbor to another node.
             */
            if(g.getNode(nd.getKey()) == null) {
                temp.setInfo(nd.getInfo());
                temp.setTag(nd.getTag());
                g.addNode(temp);
            }
                for (node_data ni : nd.getNi()) {
                    node_data tempNi = new NodeData(ni.getKey());
                    /*
                     *Same as above comment, we don't need to add a neighbor if it was already added previously.
                     */
                    if(g.getNode(tempNi.getKey()) == null){
                        tempNi.setTag(ni.getTag());
                        tempNi.setInfo(ni.getInfo());
                        g.addNode(tempNi);

                    }
                    g.connect(temp.getKey(), tempNi.getKey());
                }

        }


        return g;
    }

    @Override
    public boolean isConnected() {

        // Adapted from http://math.hws.edu/eck/cs327_s04/chapter9.pdf



        //Check for the trivial graph of size 1 or an empty graph.
        if(this.gr.getV().size() == 1 || gr.getV().isEmpty())
        {
            return true;
        }

        Queue<node_data> que = new LinkedList<node_data>();


        for(node_data nd: gr.getV())
        {
            nd.setTag(0); // Set everything to unvisited
        }

        int connected = 0;

        /*
        In this loop, we pick an unvisited node and traverse the graph. Then, we go over all the nodes to ensure they have been visited.
        The 'connected' variable acts as a counter to the maximal connected sub graphs - if connected == 1, that means we only have one such subgraph, that is the full graph itself.

         */
        for(node_data nd: gr.getV())
        {
            if(nd.getTag() == 0) // We have an unvisited node
            {
                connected++; // In a connected graph, this incrementation will only happen once since all nodes will be visited afterwards.
                que.add(nd);
                nd.setTag(1);

                while( !que.isEmpty()) // breadth-first search traversal
                {
                    node_data temp = que.remove();
                    for(node_data ni : temp.getNi())
                    {
                        if(ni.getTag() == 0)
                        {
                            ni.setTag(1);  //Set neighbor as visited, add it to the queue so we can traverse its own neighbors.
                            que.add(ni);
                        }
                    }
                }

            }
        }

        return connected == 1; //We only have one connected subgraph, the full graph.
    }


    @Override
    public int shortestPathDist(int src, int dest) {

        if(gr.getNode(src) == null || gr.getNode(dest) == null)
        {
            return -1; // src/dest aren't nodes in the graph
        }
        int length = 0;


        Queue<node_data> Q = new LinkedList<>();

        for(node_data nd: gr.getV())
        {
            nd.setTag(0); // Set all nodes unvisited
        }


        Q.add(gr.getNode(src));
        gr.getNode(src).setTag(1);

        // The flag helps us figure out how long from the source we have traveled.
        // Whenever we see a flag in the queue, we will remove it and add it at the bottom of the queue.
        node_data flag = null;


        Q.add(null);

        while(!Q.isEmpty())
        {
            node_data currentNode = Q.remove();

            if(currentNode == null)
            {
                if(Q.isEmpty())
                {
                    return -1; // if Q is empty, and we got a flag, that means there aren't any neighbors to continue - there is no path.
                }
                length++; // Since we got to the flag, we are incrementing length by 1
                currentNode = Q.remove(); // We don't actually want to use this null Node, so we'll continue to the neighbors

                Q.add(flag); // We add a new  flag. This is the same as adding null.
            }

            if(currentNode.getKey() == dest)
            {
                return length;
            }

            for(node_data neighbor: currentNode.getNi()) // getNi returns a collection of neighbors for currentNode
            {
                if(neighbor.getTag()==0)
                {
                    neighbor.setTag(1); // Set visited
                    Q.add(neighbor);
                }
            }
        }

        return length;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {




        Queue<node_data> Q = new LinkedList<>();
        HashMap<node_data, node_data> prev = new HashMap<node_data, node_data>();
        Q.add(gr.getNode(src));

        if(gr.getNode(src) == null || gr.getNode(dest) == null)
        {
            return null; // Source or Dest don't exist
        }

        for(node_data nd: gr.getV())
        {
            nd.setTag(0);//set all unvisited
            prev.put(nd, null);
        }

        while(!Q.isEmpty())
        {
            node_data temp = Q.remove();

            for(node_data ni: temp.getNi())
            {
                if(ni.getTag() == 0)
                {
                    Q.add(ni);
                    ni.setTag(1);
                    prev.put(ni, temp); // the previous of neighbor is temp
                }
            }
        }

        List<node_data> ret = new ArrayList<node_data>();
        node_data current = gr.getNode(dest);
        while(current != gr.getNode(src))
        {
            ret.add(current);
            current = prev.get(gr.getNode(current.getKey()));

        }
        ret.add(gr.getNode(src));
        Collections.reverse(ret);





        return ret;
    }




}
