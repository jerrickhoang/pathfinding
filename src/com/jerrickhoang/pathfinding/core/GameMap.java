package com.jerrickhoang.pathfinding.core;

import java.util.ArrayList;
import java.util.Random;

import com.jerrickhoang.pathfinding.geom.PFPolygon;


public class GameMap {
	
	public ArrayList<Obstacle> obstacles;
	private int width;
	private int height;
	
	public GameMap(int frameWidth, int frameHeight) {
		this.width = frameWidth;
		this.height = frameHeight;
		obstacles = new ArrayList<Obstacle>();
		ArrayList<PFPolygon> polygons = createRandomMap(frameWidth, frameHeight);
		for (PFPolygon p : polygons) {
			Obstacle ob = new Obstacle(p);
			obstacles.add(ob);
		}
	}

	public ArrayList<PFPolygon> createRandomMap(int frameWidth, int frameHeight){
		Random rand = new Random();
		ArrayList<PFPolygon> polygons = new ArrayList<PFPolygon>();

		// make some rectangles
		for (int i = 0; i < 4; i++){
			PFPoint p = new PFPoint((float) rand.nextFloat() * frameWidth, (float) rand.nextFloat() * frameHeight);
			PFPoint p2 = new PFPoint((float) rand.nextFloat() * frameWidth, (float) rand.nextFloat() * frameHeight);
			float width = 10 + 30 * rand.nextFloat();
			PFPolygon rect = PFPolygon.createRectOblique(p, p2, width);
			polygons.add(rect);
		}
		// make a cross
		polygons.add(PFPolygon.createRectOblique(40, 70, 100, 70, 20));
		polygons.add(PFPolygon.createRectOblique(70, 40, 70, 100, 20));
		// make some stars
		for (int i = 0; i < 4; i++){
			ArrayList<PFPoint> pointList = new ArrayList<PFPoint>();
			int numPoints = 4 + rand.nextInt(4) * 2;
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
			assert poly.isCounterClockWise();
			poly.translate(20 + (float) rand.nextFloat() * frameWidth, 20 + (float)rand.nextFloat() * frameHeight);
			polygons.add(poly);
		}
		return polygons;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
