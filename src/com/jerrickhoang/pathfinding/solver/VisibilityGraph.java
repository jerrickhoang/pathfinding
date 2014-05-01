package com.jerrickhoang.pathfinding.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VisibilityGraph {
	private int V;
	private int E;
	private HashMap<PFNode, ArrayList<PFNode>> adj;
	
	public VisibilityGraph() {
		this.V = 0;
		this.E = 0;
		adj = new HashMap<PFNode, ArrayList<PFNode>>();
	}

    public void addEdge(PFNode v, PFNode w) {
    	E++;
        if (adj.containsKey(v)) {
        	ArrayList<PFNode> oldList = adj.get(v);
        	oldList.add(w);
        	adj.put(v, oldList);
        } else {
        	ArrayList<PFNode> newList = new ArrayList<PFNode>();
        	newList.add(w);
        	adj.put(v, newList);
        }
        
        if (adj.containsKey(w)) {
        	ArrayList<PFNode> oldList = adj.get(w);
        	oldList.add(v);
        	adj.put(w, oldList);
        } else {
        	ArrayList<PFNode> newList = new ArrayList<PFNode>();
        	newList.add(v);
        	adj.put(w, newList);
        }

    }

    public Iterable<PFNode> adj(PFNode v) {
        if (!adj.containsKey(v)) {
        	System.out.println("The graph does not contain the vertex");
        	System.exit(0);
        }
        return adj.get(v);
    }

    public void printMap() {
    	if (adj.size() == 0) System.out.println("Empty graph!");
        Iterator it = adj.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            PFNode v = (PFNode) pairs.getKey();
            ArrayList<PFNode> l = (ArrayList<PFNode>) pairs.getValue();
            System.out.println(v + ": " + l);
            it.remove();
        }
    }
    
    public Iterator getIterator() {
    	return adj.entrySet().iterator();
    }
	
	private int E() {
		return E;
	}

	private int V() {
		return V;
	}

	// unit test for graph
	public static void main(String[] args) {
		VisibilityGraph g = new VisibilityGraph();
		PFNode[] list = {new PFNode(1, 1), new PFNode(1, 2), new PFNode(1, 3), new PFNode(1, 4), new PFNode(1, 5)};
		g.addEdge(list[0], list[1]);
		g.addEdge(list[2], list[3]);
		g.addEdge(list[4], list[1]);
		g.addEdge(list[3], list[1]);
		g.addEdge(list[0], list[4]);
		
		g.printMap();
	}

	public boolean contains(PFNode v) {
//    	if (adj.size() == 0) System.out.println("Empty graph!");
//        Iterator it = adj.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pairs = (Map.Entry)it.next();
//            PFNode n = (PFNode) pairs.getKey();
//            if (n.equals(v)) return true;
//            it.remove();
//        }
//        return false;
		return adj.containsKey(v);
	}
	

}
