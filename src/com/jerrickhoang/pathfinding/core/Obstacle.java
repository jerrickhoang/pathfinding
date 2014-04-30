package com.jerrickhoang.pathfinding.core;

import com.jerrickhoang.pathfinding.geom.PFPolygon;

public class Obstacle {
	
	private PFPolygon polygon;
	
	public Obstacle() {
		polygon = new PFPolygon();
	}
	
	public Obstacle(PFPolygon p) {
		// TODO Auto-generated constructor stub
		this.polygon = p;
	}

	public PFPolygon getPolygon() {
		return polygon;
	}
}
