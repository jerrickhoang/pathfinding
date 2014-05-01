package com.jerrickhoang.pathfinding.geom;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class PFPolygon implements Shape {

	public PFPoint center;
	public double area;
	public double radius;
	public double radiusSq;
	public boolean counterClockWise;
	public ArrayList<PFPoint> pointList;
	
	public PFPolygon(ArrayList<PFPoint> pointList) {
		this.pointList = pointList;
		calcAll();
	}

	public PFPolygon() {
		pointList = new ArrayList<PFPoint>();
	}
	
	public PFPolygon(PFPolygon polygon) {
		pointList = new ArrayList<PFPoint>(polygon.getPoints().size());
		for (int i = 0; i < polygon.getPoints().size() ;i++){
			PFPoint existingPoint = polygon.getPoints().get(i);
			pointList.add(new PFPoint(existingPoint));
		}
		area = polygon.getArea();
		counterClockWise = polygon.isCounterClockWise();
		radius = polygon.radius;
		radiusSq = polygon.radiusSq;
		center = new PFPoint(polygon.center);
	}

	public void calcArea(){
		double signedArea = 0;
		PFPoint cur, next;
		for (int i = 0; i < pointList.size() - 1; i++) {
			cur = pointList.get(i);
			next = pointList.get(i + 1);
			signedArea += ( (cur.x - next.x) * (next.y + (cur.y - next.y) / 2) );
		}
		
		if (signedArea < 0){
			counterClockWise = false;
		}else{
			counterClockWise = true;
		}
		area = Math.abs(signedArea);
	}
	
	// http://en.wikipedia.org/wiki/Centroid
	public void calcCenter() {
		if (center == null){
			center = new PFPoint();
		}
		if (getArea() == 0){
			center.x = pointList.get(0).x;
			center.y = pointList.get(0).y;
			return;
		}
        double cx = 0.0f;
		double cy = 0.0f;
		PFPoint pointIBefore = (pointList.size() != 0 ? pointList.get(pointList.size()-1) : null);
        for (int i = 0; i < pointList.size(); i++) {
			PFPoint pointI = pointList.get(i);
			double multiplier = (pointIBefore.y * pointI.x - pointIBefore.x * pointI.y);
			cx += (pointIBefore.x + pointI.x) * multiplier;
			cy += (pointIBefore.y + pointI.y) * multiplier;
			pointIBefore = pointI;
        }
		cx /= (6 * getArea());
        cy /= (6 * getArea());
		if (counterClockWise == true){
			cx *= -1;
			cy *= -1;
		}
        center.x = cx;
		center.y = cy;
    }
	
	// radius of the smallest circle contains the polygon.
	public void calcRadius() {
		if (center == null){
			calcCenter();
		}
		double maxRadiusSq = -1;
		int furthestPointIndex = 0;
		PFPoint current;
		for (int i = 0; i < pointList.size(); i++) {
			current = pointList.get(i);
			double currentRadiusSq = Utility.distanceSq(center.x, center.y, current.x, current.y);
			if (currentRadiusSq > maxRadiusSq) {
				maxRadiusSq = currentRadiusSq;
				furthestPointIndex = i;
			}
		}
		PFPoint furthestPoint = pointList.get(furthestPointIndex);
		radius = Utility.distance(center.x, center.y, furthestPoint.x, furthestPoint.y);
		radiusSq = radius * radius;
	}

	public void calcAll(){
		this.calcArea();
		this.calcCenter();
		this.calcRadius();
	}
	
	public boolean isCounterClockWise() {
		return counterClockWise;
	}

	public void translate(double x, double y) {
		for (int i = 0; i < pointList.size(); i++) {
			pointList.get(i).x += x;
			pointList.get(i).y += y;
		}
		center.x += x;
		center.y += y;
	}

	@Override
	public boolean contains(Point2D p) {
		return contains(p.getX(), p.getY());
	}
	
	@Override
	public boolean contains(double x, double y) {
		PFPoint pointIBefore = (pointList.size() != 0 ? pointList.get(pointList.size() - 1) : null);
		int crossings = 0;
		for (int i = 0; i < pointList.size(); i++) {
			PFPoint pointI = pointList.get(i);
			if (((pointIBefore.y <= y && y < pointI.y)
					|| (pointI.y <= y && y < pointIBefore.y))
					&& x < ((pointI.x - pointIBefore.x)/(pointI.y - pointIBefore.y)*(y - pointIBefore.y) + pointIBefore.x)) {
				crossings++;
			}
			pointIBefore = pointI;
		}
		return (crossings % 2 != 0);
	}

	@Override
	public boolean contains(Rectangle2D r) {
		return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		if (x + w < center.x - radius ||
				x > center.x + radius ||
				y + h < center.y - radius ||
				y > center.y + radius){
			return false;
		}
		for (int i = 0; i < pointList.size(); i++){
			int nextI = (i+1 >= pointList.size() ? 0 : i+1);
			if (Utility.linesIntersect(x, y, x + w, y, pointList.get(i).x, pointList.get(i).y, pointList.get(nextI).x, pointList.get(nextI).y) ||
					Utility.linesIntersect(x, y, x, y+h, pointList.get(i).x, pointList.get(i).y, pointList.get(nextI).x, pointList.get(nextI).y) ||
					Utility.linesIntersect(x, y+h, x+w, y+h, pointList.get(i).x, pointList.get(i).y, pointList.get(nextI).x, pointList.get(nextI).y) ||
					Utility.linesIntersect(x+w, y, x+w, y+h, pointList.get(i).x, pointList.get(i).y, pointList.get(nextI).x, pointList.get(nextI).y)){
				return false;
			}
		}
		double px = pointList.get(0).x;
		double py = pointList.get(0).y;
		if (px > x && px < x + w && py > y && py < y + h){
			return false;
		}
		if (contains(x, y) == true){
			return true;
		}
		return false;
	}

	public boolean intersectsLine(PFPoint p1, PFPoint p2){
		return intersectsLine(p1.x, p1.y, p2.x, p2.y);
	}
	
	public boolean intersectsLine(double x1, double y1, double x2, double y2){

		if (x1 == x2 && y1 == y2){
			return false;
		}
		double ax = x2-x1;
		double ay = y2-y1;
		PFPoint pointIBefore = pointList.get(pointList.size()-1);
		for (int i = 0; i < pointList.size(); i++){
			PFPoint pointI = pointList.get(i);
			double x3 = pointIBefore.x;
			double y3 = pointIBefore.y;
			double x4 = pointI.x;
			double y4 = pointI.y;

			double bx = x3-x4;
			double by = y3-y4;
			double cx = x1-x3;
			double cy = y1-y3;

			double alphaNumerator = by*cx - bx*cy;
			double commonDenominator = ay*bx - ax*by;
			if (commonDenominator > 0){
				if (alphaNumerator < 0 || alphaNumerator > commonDenominator){
					pointIBefore = pointI;
					continue;
				}
			}else if (commonDenominator < 0){
				if (alphaNumerator > 0 || alphaNumerator < commonDenominator){
					pointIBefore = pointI;
					continue;
				}
			}
			double betaNumerator = ax*cy - ay*cx;
			if (commonDenominator > 0){
				if (betaNumerator < 0 || betaNumerator > commonDenominator){
					pointIBefore = pointI;
					continue;
				}
			}else if (commonDenominator < 0){
				if (betaNumerator > 0 || betaNumerator < commonDenominator){
					pointIBefore = pointI;
					continue;
				}
			}
			if (commonDenominator == 0){
				double collinearityTestForP3 = x1*(y2-y3) + x2*(y3-y1) + x3*(y1-y2);
				if (collinearityTestForP3 == 0){
					if (x1 >= x3 && x1 <= x4 || x1 <= x3 && x1 >= x4 ||
							x2 >= x3 && x2 <= x4 || x2 <= x3 && x2 >= x4 ||
							x3 >= x1 && x3 <= x2 || x3 <= x1 && x3 >= x2){
						if (y1 >= y3 && y1 <= y4 || y1 <= y3 && y1 >= y4 ||
								y2 >= y3 && y2 <= y4 || y2 <= y3 && y2 >= y4 ||
								y3 >= y1 && y3 <= y2 || y3 <= y1 && y3 >= y2){
							return true;
						}
					}
				}
				pointIBefore = pointI;
				continue;
			}
			return true;
		}
		return false;
	}
	
	public double[] getBoundsArray() {
		double[] bounds = new double[4];
		double leftX = Double.MAX_VALUE;
		double botY = Double.MAX_VALUE;
		double rightX = -Double.MAX_VALUE;
		double topY = -Double.MAX_VALUE;

		for (int i = 0; i < pointList.size(); i++) {
			if (pointList.get(i).x < leftX) {
				leftX = pointList.get(i).x;
			}
			if (pointList.get(i).x > rightX) {
				rightX = pointList.get(i).x;
			}
			if (pointList.get(i).y < botY) {
				botY = pointList.get(i).y;
			}
			if (pointList.get(i).y > topY) {
				topY = pointList.get(i).y;
			}
		}
		bounds[0] = leftX;
		bounds[1] = botY;
		bounds[2] = rightX;
		bounds[3] = topY;
		return bounds;
	}
	
	@Override
	public Rectangle getBounds() {
		double[] bounds = getBoundsArray();
		return new Rectangle((int)(bounds[0]), (int)(bounds[1]), (int)Math.ceil(bounds[2]), (int)Math.ceil(bounds[3]));
	}

	@Override
	public Rectangle2D.Double getBounds2D() {
		double[] bounds = getBoundsArray();
		return new Rectangle2D.Double(bounds[0], bounds[1], bounds[2], bounds[3]);
	}

	public PathIterator getPathIterator(AffineTransform at){
		return new PFPolygonIterator(this, at);
	}
	
	public PathIterator getPathIterator(AffineTransform at, double flatness){
		return new PFPolygonIterator(this, at);
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		if (x + w < center.x - radius ||
				x > center.x + radius ||
				y + h < center.y - radius ||
				y > center.y + radius){
			return false;
		}
		for (int i = 0; i < pointList.size(); i++){
			int nextI = (i+1 >= pointList.size() ? 0 : i+1);
			if (Utility.linesIntersect(x, y, x + w, y, pointList.get(i).x, pointList.get(i).y, pointList.get(nextI).x, pointList.get(nextI).y) ||
				Utility.linesIntersect(x, y, x, y + h, pointList.get(i).x, pointList.get(i).y, pointList.get(nextI).x, pointList.get(nextI).y) ||
				Utility.linesIntersect(x, y + h, x + w, y + h, pointList.get(i).x, pointList.get(i).y, pointList.get(nextI).x, pointList.get(nextI).y) ||
				Utility.linesIntersect(x + w, y, x + w, y + h, pointList.get(i).x, pointList.get(i).y, pointList.get(nextI).x, pointList.get(nextI).y)){
				return true;
			}
		}
		double px = pointList.get(0).x;
		double py = pointList.get(0).y;
		if (px > x && px < x + w && py > y && py < y + h){
			return true;
		}
		if (contains(x, y) == true){
			return true;
		}
		return false;
	}

	public PFPoint getPoint(int i) {
		return pointList.get(i);
	}
	
	public double getArea()	{
		return area;
	}

	
	public ArrayList<PFPoint> getPoints() {
		return pointList;
	}

	public PFPolygon copy(){
		PFPolygon polygon = new PFPolygon(this);
		return polygon;
	}
	
	public PFPoint getCenter() {
		return center;
	}

	public boolean contains(PFPoint point) {
		return contains(point.x, point.y);
	}

	public void reversePointOrder() {
		counterClockWise = !counterClockWise;
		ArrayList<PFPoint> tempPoints = new ArrayList<PFPoint>(pointList.size());
		for (int i = pointList.size()-1; i >= 0; i--){
			tempPoints.add(pointList.get(i));
		}
		pointList.clear();
		pointList.addAll(tempPoints);
	}
}
