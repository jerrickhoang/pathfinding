package com.jerrickhoang.pathfinding.core;

import com.jerrickhoang.pathfinding.geom.PFPolygon;

public class Obstacle {
	
	private PFPolygon polygon;
	
	public Obstacle() {
		polygon = new PFPolygon();
	}
	
	public PFPolygon getPolygon() {
		return polygon;
	}
}
