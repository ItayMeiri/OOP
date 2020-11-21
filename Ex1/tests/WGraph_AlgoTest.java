package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Random;

class WGraph_AlgoTest {

    weighted_graph graph;
    weighted_graph_algorithms wga;
    double EPS = 0.001;

    @BeforeEach
     void init()
    {
        graph = new WGraph_DS();

        wga = new WGraph_Algo();
        wga.init(graph);
        for(int i = 0; i < 10; i++)
        {
            graph.addNode(i);
        }


    }

    public weighted_graph graph_creator(int v, int e, long seed)
    {
        graph = new WGraph_DS();

        for(int i = 0; i < v; i++)
        {
            graph.addNode(i);
        }

        Random random = new Random(seed);
        for(int i = 0; i < e; i++)
        {
            int max = random.nextInt(e);
            int min = random.nextInt(e);
            graph.connect(max, min, random.nextDouble());
        }

        return graph;

    }


    @Test
    void copy() {

        //small test
        double EPS = 0.001;
        weighted_graph graph = graph_creator(1000, 10000, 100L);

        weighted_graph g1 = new WGraph_DS();
        g1.addNode(0);
        g1.addNode(1);
        g1.connect(0, 1, 0.5);
        g1.connect(1, 0, 0.5);

        weighted_graph_algorithms wga1 = new WGraph_Algo();

        wga1.init(g1);
        weighted_graph g2 = wga1.copy();

        Assertions.assertTrue(g2.hasEdge(0, 1));
        Assertions.assertTrue(g2.hasEdge(1, 0));


        Assertions.assertEquals(g1.edgeSize(), g2.edgeSize());
        Assertions.assertEquals(g1.nodeSize(), g2.nodeSize());
        Assertions.assertEquals(g1.getMC(), g2.getMC());
        for(node_info ni: g1.getV())
        {
            for(node_info nd : g1.getV(ni.getKey()))
            {
                Assertions.assertTrue(g2.hasEdge(nd.getKey(), ni.getKey()));
                Assertions.assertEquals(g1.getEdge(nd.getKey(), ni.getKey()), g2.getEdge(nd.getKey(), ni.getKey()), EPS);

            }
        }



        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(graph);
        weighted_graph copyGraph = wga.copy();


        Assertions.assertEquals(graph.edgeSize(), copyGraph.edgeSize());
        Assertions.assertEquals(graph.nodeSize(), copyGraph.nodeSize());


        //check if all neighbors exist with the same values
        for(node_info ni: graph.getV())
        {
            Assertions.assertEquals(graph.getV(ni.getKey()).size(), copyGraph.getV(ni.getKey()).size(), EPS);
            for(node_info nd : graph.getV(ni.getKey()))
            {
                Assertions.assertTrue(copyGraph.hasEdge(nd.getKey(), ni.getKey()));
                Assertions.assertTrue(copyGraph.hasEdge(ni.getKey(), nd.getKey()));
                Assertions.assertEquals(graph.getEdge(nd.getKey(), ni.getKey()), copyGraph.getEdge(nd.getKey(), ni.getKey()), EPS);
                Assertions.assertEquals(graph.getNode(nd.getKey()).getInfo(), copyGraph.getNode(nd.getKey()).getInfo());
                Assertions.assertEquals(graph.getNode(nd.getKey()).getTag(), copyGraph.getNode(nd.getKey()).getTag(), EPS);

            }

        }

    }

    @Test
    void isConnected() {

        weighted_graph graph = new WGraph_DS();

        WGraph_Algo wga = new WGraph_Algo();
        wga.init(graph);
        for(int i = 0; i < 10; i++)
        {
            graph.addNode(i);
        }
        graph.connect(0, 1, 0.5);
        graph.connect(1, 2, 0.5);
        graph.connect(2, 3, 0.5);
        graph.connect(3, 5, 0.5);
        graph.connect(4, 5, 0.5);
        graph.connect(3, 6, 0.5);
        graph.connect(6, 7, 0.5);
        graph.connect(8, 7, 0.5);
        graph.connect(8, 9, 0.5);

        Assertions.assertTrue(wga.isConnected());
        graph.removeEdge(8, 9);
        Assertions.assertFalse(wga.isConnected());
        graph.connect(9, 2, 0.5);
        Assertions.assertTrue(wga.isConnected());
        graph.removeEdge(2, 9);
        Assertions.assertFalse(wga.isConnected());
        graph.removeNode(9);
        Assertions.assertTrue(wga.isConnected());

    }

    @Test
    void shortestPathDist() {

        graph.connect(1, 2, 1);
        graph.connect(2, 3, 1);
        graph.connect(3, 4, 1);
        graph.connect(4, 1, 2);

        assertEquals(wga.shortestPathDist(1, 4), 2, EPS);

        graph.connect(4, 1, 5);

        assertEquals(wga.shortestPathDist(1, 4), 3, EPS);

        assertEquals(-1, wga.shortestPathDist(1, 8));




    }

    @Test
    void shortestPath() {

        graph.connect(1, 2, 3);
        graph.connect(2, 3, 2);
        graph.connect(3, 4, 1);
        graph.connect(3, 5, 2);
        graph.connect(4, 1, 2);
        graph.connect(4, 8, 1);
        graph.connect(3, 8, 2);

        List<node_info> shortest = wga.shortestPath(1, 8);
        int a = shortest.get(0).getKey();
        int b = shortest.get(1).getKey();
        int c = shortest.get(2).getKey();

        assertEquals(1, a);
        assertEquals(4, b);
        assertEquals(8, c);

        shortest = wga.shortestPath(1, 7);
        assertNull(shortest);
    }

    @Test
    void save() {

        weighted_graph g1 = graph_creator(10, 20, 100L);
        weighted_graph g2 = graph_creator(10, 20, 100L);

        assertEquals(g1, g2);

        weighted_graph_algorithms wga1 = new WGraph_Algo();
        weighted_graph_algorithms wga2 = new WGraph_Algo();

        wga1.init(g1);
        wga2.init(g2);


        wga1.save("myFile");
        wga2.load("myFile");
        g2 = wga2.getGraph();


        assertEquals(g1, g2);


    }


    @Test
    void testInit() {

        weighted_graph g1 = graph_creator(10, 20, 100L);
        weighted_graph g2 = graph_creator(10, 20, 100L);

        wga.init(g1);
        assertEquals(g1, wga.getGraph());
        wga.init(g2);
        assertEquals(g2, wga.getGraph());



    }

    @Test
    void getGraph() {

        assertEquals(wga.getGraph(), graph);
    }
}