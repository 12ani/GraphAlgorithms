
/**
*
* @authors
* sxm180018 - Shivan Mankotia
* axk175430 - Akshay Kanduri
*/
package sxm180018;

import rbk.Graph.Vertex;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;


import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import rbk.Graph;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {

    private static List<Graph.Vertex> finishList; //list to stote vertices. 		
    public boolean cyclic;
    //boolean variable check for cycles.						

    public static class DFSVertex implements Factory {

        int cno;
        public boolean seen;		// boolean variable check whether vertex is visited or not.								
        public boolean recStack;      // boolean variable check for cycles.									

        public DFSVertex(Vertex u) {
            seen = false;
            recStack = false;
        }

        public DFSVertex make(Vertex u) {
            return new DFSVertex(u);
        }
    }

    public DFS(Graph g) {
        super(g, new DFSVertex(null));
        finishList = new LinkedList<Vertex>();
        //boolean[] recStack = new boolean[]; 
        //recStack = false;
        cyclic = false; 
    }

    public static DFS depthFirstSearch(Graph g) {
        DFS d = new DFS(g);

        for (Graph.Vertex u : g) {
            DFS_Visit(u, d);
        }

        return d;
    }

    public static void DFS_Visit(Vertex u, DFS d) {   //recursive method to check if vertex is visited and mark if not.						
        
        if (d.get(u).recStack) {
            d.cyclic = true;
            return;
        }

        if (!d.get(u).seen) {
            d.get(u).seen = true;
            d.get(u).recStack = true;

            for (Edge e : d.g.incident(u)) {
                Vertex v = e.otherEnd(u);
                DFS.DFS_Visit(v, d);
            }

            if (!d.cyclic) {
                ((LinkedList<Vertex>) finishList).addFirst(u); // adding each of visited vertex in starting of the linkedList. 
            }                                                   //the linkedList is operating like a Stack. 

            d.get(u).recStack = false;     //if there is no cycle the parentseen variable will become false. 
        }
    }

    // Member function to find topological order
    public List<Vertex> topologicalOrder1() {
        if (!g.isDirected() || cyclic) {
            return null;
        }
        depthFirstSearch(g);
        return finishList;

    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
        return 0;
    }

    // After running the connected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
        return get(u).cno;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        //This input will output the topological order of the graph. It has no cycles. 
        String string = "3 2    1 2 1    1 3 1";
        
        //This input will output '[]' (null) as it is cyclic in 6-7 nodes.
        //String string = "7 10  1 1 4  1 4 2   1 2 3   2 3 5   3 7 4   4 2 1   4 5 7   6 5 1   6 7 1  6 3 2";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readDirectedGraph(in);
        g.printGraph(false);

        DFS d = new DFS(g);
        int numcc = d.connectedComponents();
        System.out.println("Number of components: " + numcc + "\nu\tcno");
        for (Vertex u : g) {
            System.out.println(u + "\t" + d.cno(u));
        }
        
        System.out.println(d.topologicalOrder1(g));
    }
}
