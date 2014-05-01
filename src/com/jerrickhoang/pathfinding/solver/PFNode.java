package com.jerrickhoang.pathfinding.solver;

import com.jerrickhoang.pathfinding.geom.PFPoint;

public class PFNode {

	public PFPoint p;
	
	public PFNode(PFPoint newPoint) {
		this.p = newPoint;
	}
	
	public PFNode(double x, double y) {
		this.p = new PFPoint(x, y);
	}
	
	@Override
	public String toString() {
		return "PFPoint(" + p.x + ", " + p.y + ")";
	}
	
}
