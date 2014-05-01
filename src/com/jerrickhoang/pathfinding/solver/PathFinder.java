package com.jerrickhoang.pathfinding.solver;

import java.util.ArrayList;
import java.util.Comparator;

import com.jerrickhoang.pathfinding.geom.PFPoint;
import com.jerrickhoang.pathfinding.geom.PFPolygon;
import com.jerrickhoang.pathfinding.geom.Utility;
import com.vividsolutions.jts.util.PriorityQueue;

public class PathFinder {

	
	public static ArrayList<PFNode> visibleNodes(PFNode from, ArrayList<Obstacle> obstacles) {
		ArrayList<PFNode> res = new ArrayList<PFNode>();
		PFPoint fromPoint = from.p;
		for (Obstacle ob : obstacles) {
			ArrayList<PFPoint> vertices = ob.getPolygon().pointList;
			for (PFPoint v : vertices) {
				if (isVisible(fromPoint, v, obstacles)) {
					res.add(new PFNode(v));
				}
			}
		}
		
		return res;
	}
	
	private static boolean isVisible(PFPoint fromPoint, PFPoint toPoint, ArrayList<Obstacle> obstacles) {
		PFPolygon current;
		for (Obstacle ob : obstacles) {
			current = ob.getInnerPolygon();
			if (current.intersectsLine(fromPoint, toPoint)) return false;
		}
		return true;
	}

	public static PFSolution Astar(PFNode from, PFNode to, VisibilityGraph g) {
		if (!g.contains(from) || !g.contains(to)) {
			System.out.println("graph doesn't have the robot and the goal!");
			System.exit(0);
		}
		
		java.util.PriorityQueue<PFNode> openList = new java.util.PriorityQueue<PFNode>(11, 
				new Comparator<PFNode>() {

					@Override
					public int compare(PFNode n1, PFNode n2) {
						return (int) (n1.f() - n2.f());
					}
					
				});
		ArrayList<PFNode> closeList = new ArrayList<PFNode>();
		openList.clear(); closeList.clear();
		from.g = 0;
		openList.add(from);
		
		while(openList.size() != 0) {
			PFNode q = openList.remove();
			if (q.equals(to)) reconstructPath(q);
			closeList.add(q);
			
			for (PFNode n : g.adj(q)) {
				boolean neighborIsBetter = false;
				if (closeList.contains(n)) continue;
				double neighborDistanceFromStart = q.g + Utility.distance(n.p.x, n.p.y, q.p.x, q.p.y);
				
				if (!openList.contains(n)) {
					openList.add(n);
					neighborIsBetter = true;
				} else if (neighborDistanceFromStart < q.g) {
					neighborIsBetter = true;
				} else neighborIsBetter = false;
				
				if (neighborIsBetter) {
					n.parent = q;
					n.g = neighborDistanceFromStart;
					n.h = Utility.distance(n.p.x, n.p.y, to.p.x, to.p.y);
				}
			}
		}
		
		return null;
	}
	
	public static PFSolution reconstructPath(PFNode p) {
		PFSolution sol = new PFSolution();
		while (!(p.parent == null)) {
			sol.path.add(p.parent);
			p = p.parent;
		}
		return sol;
	}
		
	public static VisibilityGraph makeVisibilityGraph(PFNode from, PFNode to, ArrayList<Obstacle> obstacles) {
		VisibilityGraph res = new VisibilityGraph();
		
		ArrayList<PFPoint> vertices = new ArrayList<PFPoint>();
		vertices.add(from.p);
		vertices.add(to.p);
		ArrayList<PFPoint> cur;
		for (Obstacle obs: obstacles) {
			cur = obs.getPolygon().pointList;
			vertices.addAll(cur);
		}
		
		ArrayList<PFNode> currentVisibleNodes;
		PFNode currentNode;
		for (PFPoint v : vertices) {
			currentNode = new PFNode(v);
			currentVisibleNodes = visibleNodes(currentNode, obstacles);
			for (PFNode n : currentVisibleNodes) {
				res.addEdge(currentNode, n);
			}
		}
		
		return res;
	}
	
}
