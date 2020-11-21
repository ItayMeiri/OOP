package ex1.src;

import java.io.*;
import java.util.*;

/**
 * An implementation of the weighted_graph_algorithms interface.
 */
public class WGraph_Algo implements weighted_graph_algorithms, Serializable{


    private WGraph_DS graph;

    public WGraph_Algo()
    {
        this.init(null);

    }

    @Override
    public void init(weighted_graph g) {

        if(g == null)
        {
            graph = new WGraph_DS();
            return;
        }
        graph = (WGraph_DS)g;

    }

    @Override
    public weighted_graph getGraph() {
        return graph;
    }

    @Override
    public weighted_graph copy() {

        weighted_graph g = new WGraph_DS(); // The new graph we'll return

        for(node_info nd : graph.getV()) { // Going over all nodes in graph

            //Making sure it wasn't added in the inner loop
            if(g.getNode(nd.getKey()) == null) {
                g.addNode(nd.getKey());
                g.getNode(nd.getKey()).setTag(nd.getTag());
                g.getNode(nd.getKey()).setInfo(nd.getInfo());
            }

            for(node_info ni : graph.getV(nd.getKey())) {
                if(g.getNode(ni.getKey()) == null) {
                    g.addNode(ni.getKey());
                    g.getNode(ni.getKey()).setTag(ni.getTag());
                    g.getNode(ni.getKey()).setInfo(ni.getInfo());
                }
                g.connect(nd.getKey(), ni.getKey(), graph.getEdge(nd.getKey(), ni.getKey()));
            }
        }
        return g;

    }


    @Override
    public boolean isConnected() {

        // Adapted from http://math.hws.edu/eck/cs327_s04/chapter9.pdf



        //Check for the trivial graph of size 1 or an empty graph.
        if(this.graph.getV().size() == 1 || graph.getV().isEmpty())
        {
            return true;
        }

        Queue<node_info> que = new LinkedList<node_info>();

        for(node_info nd: graph.getV())
        {
            nd.setTag(0); // Set everything to unvisited
        }

        int connected = 0;

        /*
        In this loop, we pick an unvisited node and traverse the graph. Then, we go over all the nodes to ensure they have been visited.
        The 'connected' variable acts as a counter to the maximal connected sub graphs - if connected == 1, that means we only have one such subgraph, that is the full graph itself.

         */
        for(node_info nd: graph.getV())
        {
            if(nd.getTag() == 0) // We have an unvisited node
            {
                connected++; // In a connected graph, this incrementation will only happen once since all nodes will be visited afterwards.
                que.add(nd);
                nd.setTag(1);

                while( !que.isEmpty()) // breadth-first search traversal
                {
                    node_info temp = que.remove();
                    for(node_info ni : graph.getV(temp.getKey()))
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
    public double shortestPathDist(int src, int dest) {

        //TODO: Make this more efficient
        if(shortestPath(src, dest) == null)
        {
            return -1;
        }
        if(graph.getDist(dest) == Double.MAX_VALUE)
        {
            return -1;
        }
        return graph.getDist(dest);
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {


        if(graph.getNode(src) == null || graph.getNode(dest) == null)
        {
            return null;
        }
        //TODO: RETURN NULL WHEN PATH ISN'T FOUND

        //Adapted Dijkstra's algorithm: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

        Queue<node_info> que = new LinkedList<node_info>();
        double alt = 0;

        HashMap<Integer, node_info> prevHash = new HashMap<Integer, node_info>();
        for(node_info ni: graph.getV())
        {
            ni.setTag(0); // set unvisited
            prevHash.put(ni.getKey(), null);
            this.graph.setDist(ni.getKey(), Double.MAX_VALUE); // Sets distance from source to infinity
            que.add(ni);
        }
        this.graph.setDist(src, 0);
        while(!que.isEmpty())
        {
            node_info min = this.minimum(que);

            que.remove(min);

            for(node_info neighbor : graph.getV(min.getKey()))
            {
                if(!que.contains(neighbor))
                {
                    continue;
                }
                alt = graph.getDist(min.getKey()) + graph.getEdge(min.getKey(), neighbor.getKey());

                if(alt < graph.getDist(neighbor.getKey()))
                {
                    graph.setDist(neighbor.getKey(), alt);
                    prevHash.put(neighbor.getKey(), min);
                }
            }
        }

        List<node_info> toRet = new ArrayList<>();
        node_info currentNode = graph.getNode(dest);

        while(currentNode.getKey() != src)
        {
            toRet.add(currentNode);
            currentNode = prevHash.get(currentNode.getKey());
            // testing
            if(currentNode == null)
            {
                return null;
            }
        }

        toRet.add(graph.getNode(src));
        Collections.reverse(toRet);

        return toRet;
    }

    private node_info minimum(Queue<node_info> que) {
        int minNodeKey = que.peek().getKey();
        double minimum = 0;

        for(node_info ni: graph.getV())
        {
               if( minimum > this.graph.getDist(ni.getKey()) )
               {
                   minimum = this.graph.getDist(ni.getKey());
                   minNodeKey = ni.getKey();
               }
        }

        return graph.getNode(minNodeKey);

    }

    //Save and load have been adapted from: https://mkyong.com/java/how-to-read-and-write-java-object-to-a-file/ and https://github.com/simon-pikalov/Ariel_OOP_2020/blob/master/Classes/week_03/class3/src/class3/Points3D.java

    @Override
    public boolean save(String file) {

        boolean result = false;

        try{
            FileOutputStream f = new FileOutputStream(new File(file));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(graph);
            o.close();
            f.close();
            result = true;

        }catch(FileNotFoundException e) {
        System.out.println("File not found");
    } catch (IOException e) {
        System.out.println("Error initializing stream");
    } catch (Exception e) {
        e.printStackTrace();
    }

        return result;
    }

    @Override
    public boolean load(String file) {

        boolean result = false;

        try{
            FileInputStream f = new FileInputStream(new File(file));
            ObjectInputStream o = new ObjectInputStream(f);
            WGraph_DS readGraphDS = (WGraph_DS)o.readObject();
            this.graph = readGraphDS;

            f.close();
            o.close();
            result = true;

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
