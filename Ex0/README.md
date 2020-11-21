This projects implements an unweighted, undirectional graph and allows users to find the shortest paths and store information in nodes.



Interfaces used:

-node_data
-graph
-graph_algorithms

node_data represents the set of operations applicable on a node in the graph.
graph represents the aforementioned graph
graph_algorithm represents operations on the graph


node_data functions:
getKey(int key) - returns an integer for the Node key
getNi() - returns a collection for all the neighbors
hasNi(int key) - returns a boolean whether a neighbor exists for the node
addNi(node_data t) - adds a neighbor to the node
removeNode(node_data node) - removes the neighbor as a neighbor
getInfo() - returns a string of metadata
setInfo(String s) - sets the metadata 
getTag() - returns an integer tag(used for algorithm marking)
setTag(int tag) - sets an integer tag


NodeData implementation of node_data

Beyond the above, NodeData stores a static int that starts with 0 which is incremented at every new objection creation - this acts as the key, and is given automatically.
NodeData() the constructor creates a new HashMap, and assigns the key to the Node. 
NodeData(int key) creates a new NodeMap with a specific key


graph functions:

getNode(int key) - returns the node_data with requested key
hasEdge(int node1, node2) - returns whether node1 is a neighbor of node2
connect(node1, node2) - adds node2 as a neighbor to node1
getV() - returns a collection of the Nodes in the graph
getV(int node_id) - returns a collection of the neighbors for a Node in the graph
removeNode(int key) - removes a Node from the graph
removeEdge(int node1, node2) - Removes node1 as a neighbor for node2
nodeSize() - returns the amount of nodes in the graph
edgeSize() - returns the amount of connected nodes in the graph
getMC() - Used for testing changes in the graph

Graph_DS implementation:
The graph is implemented as an unweighted, undirectional graph, which means that the operation connect(node1, node2), for example - adds node1 as a neighbor to node2
as well as adding node2 as a neighbor for node1. Similarly, hasEdge(node1, node2) does exactly the same thing as hasEdge(node2, node1) and so on.

Nodes are stored in a HashMap, similar to Nodes. 

the ModeCounter is incremented every time a new node/edge is added/removed. MC won't change if there hasn't been an actual change(such as trying to remove an edge that doesn't exist)

toString() - toString currently prints the keys, it is subject to change in the future to something else. 


graph_algo functions:
init(graph g) - initializes graph g
copy() - performs a deep copy of the graph, and returns it.
isConnected() - returns true if the graph is connected
shortestPathDist(int src, int dest) - returns an integer for the shortest distance from source to dest. 
shortestPath(int src, int dest) - returns a list of Nodes from source to destination. 

Graph_Algo implementation:
init(graph g) initializes a graph, and allows the operations of graph_algo on graph g. If g is null, it creates a new Graph_DS. 

When creating a new instance of Graph_Algo() - it calls the init function with a null value and is thus a substitution for init(null).
Graph_Algo(graph g) calls init(g) which can be used to initialize a specific graph without explicitly calling init. 

Copy() runs a deep copy - it copies everything including the key, but in new instances.

shortestPathDistance implements a simple BFS, and returns once a Node is found.

shortestPath implements a simple BFS, and returns the first path to arrive at the Node. There may be other paths of identical lengths, this isn't guarenteed to return the same path each time as the graphs change and
the HashMap collection changes. 




Example usage:

        graph gr = new Graph_DS();

        for(int i = 0; i < 10; i++){ gr.addNode(new NodeData()); } ; // creating some nodes

//connect some of them
        gr.connect(0, 1);
        gr.connect(1, 5);


//creating the graph algo
        graph_algorithms algo = new Graph_Algo(gr);

        System.out.println("Is this graph connected?" + algo.isConnected()); // This would be false, since only 2 nodes are connected right now
        gr.connect(5, 6);
        System.out.println("Shortest path from 0 to 6 is: " + algo.shortestPathDist(0, 6)); // This will return 3





