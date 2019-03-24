package sxm180018;

import java.io.File;
import java.util.Scanner;
import rbk.Graph;
import rbk.Graph.Vertex;

public class Sp12 {

	public int diameter(Graph g) {
		
		BFSOO bfs = new BFSOO(g);
		
		//BFS starting from source node 1 
		bfs = bfs.breadthFirstSearch(g, 1);
		
		Vertex max = maxDistance(bfs, g);
		
		//reverse BFS: starting from max node
		bfs = bfs.breadthFirstSearch(g, max);
		max = maxDistance(bfs, g);
		return bfs.getDistance(max);
	}

	private Vertex maxDistance(BFSOO bfs, Graph g) {
		//maxV is the farthest vertex 
		Vertex maxV = null;
		int maxDistance = 0;
		
		//Traversing all nodes and computing distance  
		for (Vertex u : g) {
			if (bfs.getDistance(u) > maxDistance) {
				maxV = u;
				maxDistance = bfs.getDistance(u);
			}
		}
		return maxV;
	}

	public static void main(String[] args) throws Exception {
		String string = "5 4  1 2 1  1 3 1  2 4 1  4 5 1   1 ";
		Scanner in;
		// If there is a command line argument, use it as file from which
		// input is read, otherwise use input from string.
		in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
		// Read graph from input
		Graph g = Graph.readGraph(in);
		int s = in.nextInt();
		g.printGraph(false);

		Sp12 sp = new Sp12();
		System.out.println("Diameter:" + sp.diameter(g));
	}

}
