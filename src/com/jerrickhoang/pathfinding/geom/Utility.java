package com.jerrickhoang.pathfinding.geom;



public class Utility {

	public static boolean collinear(double x1, double y1, double x2, double y2, double x3, double y3){
		double area = x1 * (y2-y3) + x2 * (y3-y1) + x3 * (y1-y2);	
		return area == 0;
	}
	public static boolean collinear(PFPoint p1, PFPoint p2, PFPoint p3){
		return collinear(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
	}
	
	public static boolean linesIntersect(PFPoint p1, PFPoint p2, PFPoint p3, PFPoint p4){
		return linesIntersect(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y);
	}
	
	public static boolean linesIntersect(double x1, double y1, double x2, double y2,
										double x3, double y3, double x4, double y4){
		if (x1 == x2 && y1 == y2 ||
				x3 == x4 && y3 == y4){
			return false;
		}
		double ax = x2-x1;
		double ay = y2-y1;
		double bx = x3-x4;
		double by = y3-y4;
		double cx = x1-x3;
		double cy = y1-y3;

		double alphaNumerator = by*cx - bx*cy;
		double commonDenominator = ay*bx - ax*by;
		if (commonDenominator > 0){
			if (alphaNumerator < 0 || alphaNumerator > commonDenominator){
				return false;
			}
		}else if (commonDenominator < 0){
			if (alphaNumerator > 0 || alphaNumerator < commonDenominator){
				return false;
			}
		}
		double betaNumerator = ax*cy - ay*cx;
		if (commonDenominator > 0){
			if (betaNumerator < 0 || betaNumerator > commonDenominator){
				return false;
			}
		}else if (commonDenominator < 0){
			if (betaNumerator > 0 || betaNumerator < commonDenominator){
				return false;
			}
		}
		// if commonDenominator == 0 then the lines are parallel.
		if (commonDenominator == 0){
			double collinearityTestForP3 = x1*(y2-y3) + x2*(y3-y1) + x3*(y1-y2);
			// If p3 is collinear with p1 and p2 then p4 will also be collinear, since p1-p2 is parallel with p3-p4
			if (collinearityTestForP3 == 0){
				// The lines are collinear. Now check if they overlap.
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
			return false;
		}
		return true;

	}

	public static double distanceSq(double x1, double y1, double x2, double y2){
		x1 -= x2;
		y1 -= y2;
		return (x1 * x1 + y1 * y1);
	}
	
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(distanceSq(x1, y1, x2, y2));
	}
}
