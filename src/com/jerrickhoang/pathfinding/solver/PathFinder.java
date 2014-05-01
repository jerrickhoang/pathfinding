package com.jerrickhoang.pathfinding.solver;

import java.util.ArrayList;

import com.jerrickhoang.pathfinding.geom.PFPoint;
import com.jerrickhoang.pathfinding.geom.PFPolygon;

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

	public PFSolution Astar(PFNode from, PFNode to, VisibilityGraph g) {
		return null;
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
