package com.jerrickhoang.pathfinding.core;


public class PFPoint {

	public double x;
	public double y;
	
	public PFPoint(double x, double y) {
		setLocation(x, y);
	}
	
	public PFPoint() {
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setLocation(double x, double y) {
		setX(x); setY(y);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}

}
