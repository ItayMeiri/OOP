This projects implements a weight, undirectional graph and allows users to find the shortest paths and store information in nodes.



Interfaces used:

-node_info
-weighted_graph
-weighted_graph_algorithms

node_info represents the set of operations applicable on a node in the graph.
weighted_graph represents the aforementioned graph
weighted_graph_algorithm represents operations on the graph


node_info functions:
getKey(int key) - returns an integer for the Node key
getInfo() - returns a string of metadata
setInfo(String s) - sets the metadata 
getTag() - returns an integer tag(used for algorithm marking)
setTag(int tag) - sets an integer tag


NodeInfo implementation of node_info

Beyond the above, NodeInfo has a dist and prev field, which are used for algorithms, the constructor takes an key to assign a key to Node_Info


weighted_graph functions:

getNode(int key) - returns the node_data with requested key
hasEdge(int node1, node2) - returns whether node1 is a neighbor of node2
getEdge(int node1, int node2) - returns the weight of the edge
addNode(int key) - creates a new node with the key as its key
connect(node1, node2) - adds node2 as a neighbor to node1
getV() - returns a collection of the Nodes in the graph
getV(int node_id) - returns a collection of the neighbors for a Node in the graph
removeNode(int key) - removes a Node from the graph
removeEdge(int node1, node2) - Removes node1 as a neighbor for node2
nodeSize() - returns the amount of nodes in the graph
edgeSize() - returns the amount of connected nodes in the graph
getMC() - Used for testing changes in the graph

WGraph_DS implementation:
The graph is implemented as a weighted, undirectional graph, which means that the operation connect(node1, node2), for example - adds node1 as a neighbor to node2
as well as adding node2 as a neighbor for node1. Similarly, hasEdge(node1, node2) does exactly the same thing as hasEdge(node2, node1) and so on.

Nodes are stored in a HashMap, neighbors are also stored in a HashMap inside a HashMap, with the inner one storing the weight between them. When creating a new edge, both nodes are stored as each others neighbors.
Similarly, when removed - both of them are removed. 

the ModeCounter is incremented every time a new node/edge is added/removed. MC won't change if there hasn't been an actual change(such as trying to remove an edge that doesn't exist)


weighted_graph_algorithms functions:
init(weighted_graph g) - initializes weighted_graph g
getGraph() - returns the weighted_graph initialized
copy() - performs a deep copy of the graph, and returns it.
isConnected() - returns true if the graph is connected
shortestPathDist(int src, int dest) - returns an integer for the shortest distance from source to dest. 
shortestPath(int src, int dest) - returns a list of Nodes from source to destination. 

WGraph_Algo implementation:
init(graph g) initializes a graph, and allows the operations of weighted_graph_algorithms on graph g. If g is null, it creates a new WGraph_DS. 

When creating a new instance of Graph_Algo() - it calls the init function with a null value and is thus a substitution for init(null).
WGraph_Algo(weighted_graph g) calls init(g) which can be used to initialize a specific graph without explicitly calling init. 

Copy() runs a deep copy - it copies everything including the keys - the MC is not copied.

shortestPathDist uses Dijkstra's algorithm to return the distance from src to dest

shortestPath implements Dijkstra, and returns the shortest path. There may be other paths of identical lengths, this isn't guarenteed to return the same path each time as the graphs change and
the HashMap collection changes. 




Example usage:

    weighted_graph graph;
    weighted_graph_algorithms wga;


        graph = new WGraph_DS();

        wga = new WGraph_Algo();
        wga.init(graph);
        for(int i = 0; i < 10; i++)
        {
            graph.addNode(i); // Creating some nodes
        }
        graph.connect(0, 1);
        graph.connect(1, 5);
        wga = new WGraph_Algo();
        wga1.init(g1);

        System.out.println("Is this graph connected?" + wga.isConnected()); // This would be false, since only 2 nodes are connected right now







