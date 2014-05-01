package com.vividsolutions.geom.helper;

/*
 * Copyright (c) 2008, Keith Woodward
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of Keith Woodward nor the names
 *    of its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */

import java.util.*;

import com.jerrickhoang.pathfinding.geom.PFPoint;
import com.jerrickhoang.pathfinding.geom.PFPolygon;
import com.vividsolutions.jts.geom.*;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
	
/**
 *
 * @author Keith Woodward
 */
public class PolygonConverter{
	public GeometryFactory geometryFactory = new GeometryFactory();

	public PolygonConverter(){
	}
	
	public com.vividsolutions.jts.geom.Polygon makeJTSPolygonFrom(PFPolygon polygon){
		com.vividsolutions.jts.geom.Polygon jtsPolygon;
		Coordinate[] coordinateArray = new Coordinate[polygon.getPoints().size() + 1];
		for (int i = 0; i < polygon.getPoints().size(); i++){
			PFPoint p = polygon.getPoints().get(i);
			coordinateArray[i] = new Coordinate(p.x, p.y);
		}
		// link the first and last points
		coordinateArray[polygon.getPoints().size()] = new Coordinate(coordinateArray[0].x, coordinateArray[0].y);
		LinearRing linearRing = geometryFactory.createLinearRing(coordinateArray);
		jtsPolygon = new com.vividsolutions.jts.geom.Polygon(linearRing, null, geometryFactory);
		return jtsPolygon;
	}

	public PFPolygon makePFPolygonFromExterior(com.vividsolutions.jts.geom.Polygon jtsPolygon){
		LineString exteriorRingLineString = jtsPolygon.getExteriorRing();
		PFPolygon polygon = makePFPolygonFrom(exteriorRingLineString);
		return polygon;
	}
	
	public PFPolygon makePFPolygonFrom(com.vividsolutions.jts.geom.LineString lineString){
		CoordinateSequence coordinateSequence = lineString.getCoordinateSequence();
		ArrayList<PFPoint> points = new ArrayList<PFPoint>();
		PFPoint lastAddedPoint = null;
		for (int i = 0; i < coordinateSequence.size()-1; i++){
			Coordinate coord = coordinateSequence.getCoordinate(i);
			PFPoint p = new PFPoint(coord.x, coord.y);
			if (lastAddedPoint != null && p.x == lastAddedPoint.x && p.y == lastAddedPoint.y){
				continue;
			}else{
				points.add(p);
				lastAddedPoint = p;
			}
		}
		if (points.size() < 3){
			return null;
		}
		PFPolygon polygon = new PFPolygon(points);
		return polygon;
	}
}
