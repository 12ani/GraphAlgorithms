/**
 * @authors
 * sxm180018 - Shivani Mankotia
 * axk175430 - Akshay Kanduri
 */
package sxm180018;

import rbk.Graph.Vertex;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;
import rbk.Graph.Timer;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import rbk.Graph;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {

    private LinkedList<Graph.Vertex> finalList; //list to stote vertices. 
    private int top;
    private boolean cyclic;		//boolean variable check for cycles.
    private int scc;			//variable for storing the number of connected components 

    public static class DFSVertex implements Factory {

        int cno;
        boolean seen;			//boolean variable check whether vertex is visited or not.	
        boolean recStack;		// boolean variable check for cycles.	
        Vertex parent;                  // initializing a vertex variable  
        
        public DFSVertex(Vertex u) {
            this.cno = 0;
        }
        public DFSVertex make(Vertex u) {
            return new DFSVertex(u);
        }
    }

    public DFS(Graph g) {
        super(g, new DFSVertex(null));
    }
    
    public static DFS depthFirstSearch(Graph g) {
        DFS d = new DFS(g);
        d.dfs(g);
        return d;
    }

    public void dfs(Iterable<Vertex> iterable) {

        // intialize the values of variables
        finalList = new LinkedList<Vertex>();
        top = g.size();
        cyclic = false;

        for (Vertex u : iterable) {
            get(u).seen = false;
            get(u).parent = null;
            get(u).recStack = false;
        }
        //initializing the number of connected components to 0 every time dfs is run on a graph
        scc = 0;
        //visiting each vertex of the graph if not seen
        for (Vertex u : iterable) {
            if (!get(u).seen) {
                ++scc;
                dfsVisit(u);
            }
        }
    }

    private void dfsVisit(Vertex u) {

        get(u).seen = true;
        get(u).recStack = true;
        get(u).cno = scc;

        //checking if the adjacent vertices of u are visited
        for (Edge e : g.incident(u)) {
            Vertex v = e.otherEnd(u);

            if (!cyclic) {
                // if recStack is true the graph has cycles		          
                if (get(v).recStack) {
                    cyclic = true;
                }
            }

            if (!get(v).seen) {
                get(v).parent = u;
                dfsVisit(v);
            }
        }

        finalList.addFirst(u);              // adding each of visited vertex in starting of the linkedList. 
        //get topological ordering of the list
        get(u).recStack = false;  //if there is no cycle the recStack variable will become false.
    }

    public List<Vertex> topologicalOrder1() {
        //check if graph is directed, if not return null
        if (!g.isDirected()) {
            return null;
        }
        this.dfs(g);

        //checks if there is a cycle in the graph and returns null, else returns the list
        return cyclic ? null : finalList;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG
    public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }
    
    public int connectedComponents() {
        dfs(g);
        return scc;
    }

    public int cno(Vertex u) {
        return get(u).cno;
    }
    //function to find strongly connected components
    public static DFS stronglyConnectedComponents(Graph g) {
        DFS d = new DFS(g);
        d.dfs(g);
        List<Vertex> list = d.finalList;
        g.reverseGraph();
        d.dfs(list);
        g.reverseGraph();
        return d;
    }

    public static void main(String[] args) throws Exception {
        String string = "8 9  1 2 1  2 3 1  3 1 1  2 4 1  4 5 1  5 6 1  6 7 1  7 4 1  7 8 1";

        Scanner in;
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        Graph g = Graph.readGraph(in, true);
        g.printGraph(false);

        DFS d = stronglyConnectedComponents(g);
        System.out.println("Number of strongly connected components: " + d.scc + "\nu\tStronglyconnectedComponents");
        for (Vertex u : g) {
            System.out.println(u + "\t" + d.cno(u));
        }

    }
}
