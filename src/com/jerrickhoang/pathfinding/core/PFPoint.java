package com.jerrickhoang.pathfinding.core;
import java.awt.geom.Point2D;


public class PFPoint extends Point2D{

	private double x;
	private double y;
	
	public PFPoint(double x, double y) {
		setLocation(x, y);
	}
	
	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLocation(double x, double y) {
		// TODO Auto-generated method stub
		setX(x); setY(y);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}

}
