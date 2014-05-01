package com.jerrickhoang.pathfinding.solver;

import com.jerrickhoang.pathfinding.geom.PFPoint;

public class Goal {
	public PFPoint position;
	
	public Goal(double x, double y) {
		this.position = new PFPoint(x, y);
	}
}
