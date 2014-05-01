package com.jerrickhoang.pathfinding.solver;

import com.jerrickhoang.pathfinding.geom.PFPolygon;
import com.vividsolutions.geom.helper.PolygonBufferer;

public class Obstacle {
	
	//to shrink polygons
	public static float BUFFER_AMOUNT = 0.01f;
	public static int NUM_POINTS_IN_A_QUADRANT = 1;

	
	private PFPolygon outerPolygon;
	private PFPolygon innerPolygon;
	
	public Obstacle() {
		outerPolygon = new PFPolygon();
		PolygonBufferer polygonBufferer = new PolygonBufferer();
		innerPolygon = polygonBufferer.buffer(outerPolygon, -1 * BUFFER_AMOUNT, NUM_POINTS_IN_A_QUADRANT);
	}
	
	public Obstacle(PFPolygon p) {
		this.outerPolygon = p;
		PolygonBufferer polygonBufferer = new PolygonBufferer();
		innerPolygon = polygonBufferer.buffer(outerPolygon, -1 * BUFFER_AMOUNT, NUM_POINTS_IN_A_QUADRANT);
	}

	public PFPolygon getPolygon() {
		return outerPolygon;
	}
	
	public PFPolygon getInnerPolygon() {
		return innerPolygon;
	}
}
