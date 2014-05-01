package com.jerrickhoang.pathfinding.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import com.jerrickhoang.pathfinding.geom.PFPoint;
import com.jerrickhoang.pathfinding.solver.Goal;
import com.jerrickhoang.pathfinding.solver.Obstacle;
import com.jerrickhoang.pathfinding.solver.PFNode;
import com.jerrickhoang.pathfinding.solver.PFSolution;
import com.jerrickhoang.pathfinding.solver.PathFinder;
import com.jerrickhoang.pathfinding.solver.Robot;
import com.jerrickhoang.pathfinding.solver.VisibilityGraph;



public class GameState {
	
	public Robot robot;
	public Goal goal;
	public GameMap map;
	public PFSolution solution;
	public VisibilityGraph vg;
	
	public GameState(int frameWidth, int frameHeight) {
		map = new GameMap(frameWidth, frameHeight);
		robot = initRandomRobot();
		goal = initRandomGoal();
	}
	
	public Robot initRandomRobot() {
		Random r = new Random();
		PFPoint p;
		do {
			p = new PFPoint(r.nextDouble() * map.getWidth(), r.nextDouble() * map.getHeight());
		} while (insideObstacle(p));
		return new Robot(p.x, p.y);
	}
	
	public Goal initRandomGoal() {
		Random r = new Random();
		PFPoint p;
		do {
			p = new PFPoint(r.nextDouble() * map.getWidth(), r.nextDouble() * map.getHeight());
		} while (insideObstacle(p));
		return new Goal(p.x, p.y);
	}
	
	public Robot robot() {
		return robot;
	}
	
	public PFSolution generateSolution() {
		if (solution == null) {
			VisibilityGraph v = generateVisibilityGraph();
			PFNode robotNode = new PFNode(robot.position);
			PFNode goalNode = new PFNode(goal.position);
			//System.out.println(fromNode + " " + toNode);
			solution = PathFinder.Astar(robotNode, goalNode, v);
		}
		return solution;
	}
	
	public VisibilityGraph generateVisibilityGraph() {
		PFNode robotNode = new PFNode(robot.position);
		PFNode goalNode = new PFNode(goal.position);
		vg = PathFinder.makeVisibilityGraph(robotNode, goalNode, map.obstacles);
		return vg;
	}
	
	public ArrayList<PFNode[]> iterableVisibilityGraph(){ 
		generateVisibilityGraph();
		
		ArrayList<PFNode[]> res = new ArrayList<PFNode[]>();
        Iterator it = vg.getIterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            PFNode v = (PFNode) pairs.getKey();
            ArrayList<PFNode> l = (ArrayList<PFNode>) pairs.getValue();
            PFNode[] newEdge = new PFNode[2];
            for (PFNode n : l) {
            	newEdge[0] = v; newEdge[1] = n;
            	res.add(newEdge);
            }
            it.remove();
        }
        return res;
		
	}

	public boolean insideObstacle(PFPoint p) {
		for (Obstacle ob : map.obstacles) {
			if (ob.getPolygon().contains(p)) return true;
		}
		return false;
	}
}
