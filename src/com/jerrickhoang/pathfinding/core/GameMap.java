package com.jerrickhoang.pathfinding.core;

import java.util.ArrayList;
import java.util.Random;

import com.jerrickhoang.pathfinding.geom.PFPolygon;
import com.jerrickhoang.pathfinding.geom.PolygonGenerator;


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
		int numRects = 5;
		ArrayList<PFPolygon> polygons = new ArrayList<PFPolygon>();

		// make some rectangles
		for (int i = 0; i < numRects; i++){
			PFPoint p = new PFPoint((float) rand.nextFloat() * frameWidth, (float) rand.nextFloat() * frameHeight);
			PFPoint p2 = new PFPoint((float) rand.nextFloat() * frameWidth, (float) rand.nextFloat() * frameHeight);
			float width = 10 + 30 * rand.nextFloat();
			PFPolygon rect = PolygonGenerator.createRectOblique(p, p2, width);
			polygons.add(rect);
		}
		// make a cross
		polygons.add(PolygonGenerator.createRectOblique(40, 70, 100, 70, 20));
		polygons.add(PolygonGenerator.createRectOblique(70, 40, 70, 100, 20));
		// make some stars
		for (int i = 0; i < 4; i++){
			int topX = (int) (rand.nextFloat() * frameWidth);
			int topY = (int) (rand.nextFloat() * frameHeight);
			System.out.println("topx = " + topX + " topy " + topY);
			polygons.add(PolygonGenerator.createStars(5 + rand.nextInt(4) * 2, 20 + topX, 20 + topY));

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
