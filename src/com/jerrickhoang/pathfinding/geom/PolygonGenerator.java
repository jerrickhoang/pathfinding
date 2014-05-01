package com.jerrickhoang.pathfinding.geom;

import java.util.ArrayList;
import java.util.Random;

import com.jerrickhoang.pathfinding.core.PFPoint;

public class PolygonGenerator {

	public static PFPolygon createRectOblique(double x, double y, double x2, double y2, double width){
		ArrayList<PFPoint> pointList = new ArrayList<PFPoint>();
		double r = width/2f;
		double xOffset = 0;
		double yOffset = 0;
		double xDiff = x2 - x;
		double yDiff = y2 - y;
		if (xDiff == 0){
			xOffset = r;
			yOffset = 0;
		}else if (yDiff == 0){
			xOffset = 0;
			yOffset = r;
		}else{
			double gradient = (yDiff) / (xDiff);
			xOffset = (r*gradient / (Math.sqrt(1 + gradient * gradient)));
			yOffset = -xOffset / gradient;
		}
		pointList.add(new PFPoint(x - xOffset, y - yOffset));
		pointList.add(new PFPoint(x + xOffset, y + yOffset));
		pointList.add(new PFPoint(x2 + xOffset, y2 + yOffset));
		pointList.add(new PFPoint(x2 - xOffset, y2 - yOffset));
		return new PFPolygon(pointList);
	}
	
	public static PFPolygon createRectOblique(PFPoint p1, PFPoint p2, double width){
		return createRectOblique(p1.getX(), p1.getY(), p2.getX(), p2.getY(), width);
	}
	
	public static PFPolygon creatCross(PFPoint top, double width, double height) {
		
		return null;
	}
	
	public static PFPolygon createStars(int numPoints, int topX, int topY) {
		if (numPoints < 5) {
			System.out.println("Cannot create stars of less than 5 vertices");
			System.exit(0);
		}
		
		Random rand = new Random();
		ArrayList<PFPoint> pointList = new ArrayList<PFPoint>();
		double angleIncrement = Math.PI * 2f/ (numPoints * 2);
		float rBig = 40 + rand.nextFloat() * 90;
		float rSmall = 20 + rand.nextFloat() * 70;
		double currentAngle = 0;
		for (int k = 0; k < numPoints; k++){
			double x = rBig * Math.cos(currentAngle);
			double y = rBig * Math.sin(currentAngle);
			pointList.add(new PFPoint((float) x, (float) y));
			currentAngle += angleIncrement;
			x = rSmall * Math.cos(currentAngle);
			y = rSmall * Math.sin(currentAngle);
			pointList.add(new PFPoint((float) x, (float) y));
			currentAngle += angleIncrement;
		}
		PFPolygon poly = new PFPolygon(pointList);
		//assert poly.isCounterClockWise();
		poly.translate((float) topX, (float)topY);
		return poly;
	}
	
}
