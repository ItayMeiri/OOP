package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class WGraph_DSTest {

    WGraph_DS graph;
   double EPS = 0.001;
    @BeforeEach
    void init()
    {
       graph = new WGraph_DS();

        for(int i = 0; i < 10; i++)
        {
            graph.addNode(i);
        }
    }

    @Test
    void hasEdge() {

        //Check for edge creation
        assertFalse(graph.hasEdge(0, 2));
        graph.connect(0, 1, 2);
        assertTrue(graph.hasEdge(0, 1));
        assertTrue(graph.hasEdge(1, 0));
        graph.removeEdge(0, 1);
        assertFalse(graph.hasEdge(0, 1));
        assertFalse(graph.hasEdge(1, 0));

        graph.connect(1, 0, 2);
        graph.removeEdge(1, 0);
        assertFalse(graph.hasEdge(0, 1));
        assertFalse(graph.hasEdge(1, 0));
        graph.connect(0, 1, 2);
        graph.removeNode(0);
        assertFalse(graph.hasEdge(0, 1));

    }

    @Test
    void getEdge() {

        graph.connect(0, 1, 3);
        //Check the weight
        assertEquals(3, graph.getEdge(0, 1), EPS);
        assertEquals(graph.getEdge(1, 0), 3, EPS);

        //Check the weight to self
        assertEquals(graph.getEdge(0, 0), 0, EPS);
        assertEquals(graph.getEdge(1, 1), 0, EPS);

        //Check the weight for non-existing edge
        assertEquals(graph.getEdge(1, 5), -1, EPS);


    }

    @Test
    void connect() {

        //Testing connect
        graph.connect(0, 1, 3);
        assertTrue(graph.hasEdge(0, 1));
        graph.connect(1, 5, 3);
        assertTrue(graph.hasEdge(0, 1));

        //Edge wasn't connected
        assertFalse(graph.hasEdge(1, 3));

        //has edge to self
        assertFalse(graph.hasEdge(0, 0));
        assertFalse(graph.hasEdge(4, 4));

        try {
            graph.connect(242, 4232, 2);
        }catch(Exception e)
        {
            fail("Caught exception at connect, where nodes don't exist");
            e.printStackTrace();
        }

    }

    @Test
    void removeNode() {

        //remove a node, see if it's still there
        graph.removeNode(5);
        assertNull(graph.getNode(5));

        try {
            graph.removeNode(15);
        }catch(Exception e)
        {
            fail("Trying to remove a node that doesn't exist shouldn't throw an exception, simply return null");
            e.printStackTrace();
        }

        //Test if edges are properly removed by the remove function

        graph.connect(1, 2, 1);
        graph.connect(1, 3, 1);

        for(node_info ni: graph.getV(1))
        {
            assertTrue(graph.hasEdge(1, ni.getKey()));
        }
        graph.removeNode(1);

        for(node_info ni: graph.getV(2))
        {
            fail("This shouldn't have any neighbors at all!");
        }
        assertFalse(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(2, 1));
        assertFalse(graph.hasEdge(3, 1));




    }

    @Test
    void removeEdge() {

        graph.connect(2, 3, 5);
        graph.removeEdge(2, 3);

        assertEquals(graph.getEdge(2, 3), -1, EPS);
        assertFalse(graph.hasEdge(2, 3));
        assertFalse(graph.hasEdge(3, 2));

        graph.connect(2, 3, 5);
        graph.removeEdge(3, 2);

        assertEquals(graph.getEdge(2, 3), -1, EPS);
        assertFalse(graph.hasEdge(2, 3));
        assertFalse(graph.hasEdge(3, 2));

        try{
            graph.removeEdge(2321, 342);
        }catch(Exception e)
        {
            fail("Removing an edge that doesn't exist shouldn't throw an exception");
        }


    }

    @Test
    void testEquals() {

        weighted_graph graphTwo = new WGraph_DS();

        for(int i = 0; i < 10; i++)
        {
            graphTwo.addNode(i);
        }
        assertEquals(graph, graphTwo);
        graphTwo.removeNode(5);
        assertNotEquals(graph, graphTwo);

    }


    @Test
    void getNode() {

        assertNotNull(graph.getNode(1));
        assertNull(graph.getNode(50));

    }

    @Test
    void addNode() {
        graph.addNode(13);
        assertNotNull(graph.getNode(13));

    }




    @Test
    void nodeSize() {

        assertEquals(graph.nodeSize(), 10, EPS);
        graph.removeNode(5);
        assertEquals(graph.nodeSize(), 9, EPS);
        graph.removeNode(2324);
        assertEquals(graph.nodeSize(), 9, EPS);

        graph.addNode(15);
        assertEquals(graph.nodeSize(), 10, EPS);
    }

    @Test
    void edgeSize() {

        assertEquals(graph.edgeSize(), 0, EPS);
        graph.connect(1, 0, 3);
        assertEquals(graph.edgeSize(), 1, EPS);
        graph.connect(1, 0, 3);
        assertEquals(graph.edgeSize(), 1, EPS);
        graph.connect(0, 1, 3);
        assertEquals(graph.edgeSize(), 1, EPS);

        graph.removeNode(52);
        assertEquals(graph.edgeSize(), 1, EPS);

        graph.removeNode(0);
        assertEquals(graph.edgeSize(), 0, EPS);

        graph.connect(2, 3, 3);
        assertEquals(graph.edgeSize(), 1, EPS);

        graph.removeEdge(2, 3);
        assertEquals(graph.edgeSize(), 0, EPS);

        graph.connect(2, 3, 3);
        graph.connect(3, 4, 3);
        graph.connect(4, 2, 3);
        assertEquals(graph.edgeSize(), 3, EPS);

        graph.removeNode(2);

        assertEquals(graph.edgeSize(), 1, EPS);
    }

    @Test
    void getMC() {

        assertEquals(graph.getMC(), 10, EPS);
    }

    @Test
    void getDist() {

        graph.setDist(1, 3);
        assertEquals(graph.getDist(1), 3, EPS);

    }

    @Test
    void getPrev() {
        graph.setPrev(1, 3);
        assertEquals(graph.getPrev(1), 3, EPS);
    }
}