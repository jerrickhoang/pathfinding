package com.jerrickhoang.pathfinding.solver;

import com.jerrickhoang.pathfinding.geom.PFPoint;

public class PFNode {

	public static final double EPSILON = 10e-2;
	
	public PFPoint p;
	public double g;
	public double h;
	public double f;
	public PFNode parent;
	
	public PFNode(PFPoint newPoint) {
		this(newPoint.x, newPoint.y);
	}
	
	public PFNode(double x, double y) {
		this.p = new PFPoint(x, y);
		this.g = 0;
		this.h = 0;
		this.f = 0;
		this.parent = null;
	}
	
	@Override
	public String toString() {
		return "PFPoint(" + p.x + ", " + p.y + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		PFNode point = (PFNode) obj;
		return (this.p.x - point.p.x) < EPSILON && (this.p.y - point.p.y) < EPSILON;
	}
	
//	@Override
//	public int hashCode() {
//		return p.hashCode();
//	}

	public double f() {
		return g + h;
	}
}
