package com.jerrickhoang.pathfinding.geom;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;


public class PFPolygonIterator implements PathIterator {
	public int type = PathIterator.SEG_MOVETO;
	public int index = 0;
	public PFPolygon polygon;
	public PFPoint currentPoint;
	AffineTransform affine;

	double[] singlePointSetDouble = new double[2];

	PFPolygonIterator(PFPolygon pfPolygon) {
		this(pfPolygon, null);
	}

	PFPolygonIterator(PFPolygon pfPolygon, AffineTransform at) {
		this.polygon = pfPolygon;
		this.affine = at;
		currentPoint = polygon.getPoint(0);
	}

	public int getWindingRule() {
		return PathIterator.WIND_EVEN_ODD;
	}

	public boolean isDone() {
		if (index == polygon.pointList.size() + 1){
			return true;
		}
		return false;
	}

	public void next() {
		index++;
	}

	public void assignPointAndType(){
		if (index == 0){
			currentPoint = polygon.getPoint(0);
			type = PathIterator.SEG_MOVETO;
		} else if (index == polygon.pointList.size()){
			type = PathIterator.SEG_CLOSE;
		} else{
			currentPoint = polygon.getPoint(index);
			type = PathIterator.SEG_LINETO;
		}
	}

	public int currentSegment(float[] coords){
		assignPointAndType();
		if (type != PathIterator.SEG_CLOSE){
			if (affine != null){
				float[] singlePointSetFloat = new float[2];
				singlePointSetFloat[0] = (float)currentPoint.x;
				singlePointSetFloat[1] = (float)currentPoint.y;
				affine.transform(singlePointSetFloat, 0, coords, 0, 1);
			} else{
				coords[0] = (float)currentPoint.x;
				coords[1] = (float)currentPoint.y;
			}
		}
		return type;
	}

	public int currentSegment(double[] coords){
		assignPointAndType();
		if (type != PathIterator.SEG_CLOSE){
			if (affine != null){
				singlePointSetDouble[0] = currentPoint.x;
				singlePointSetDouble[1] = currentPoint.y;
				affine.transform(singlePointSetDouble, 0, coords, 0, 1);
			} else{
				coords[0] = currentPoint.x;
				coords[1] = currentPoint.y;
			}
		}
		return type;
	}
}


