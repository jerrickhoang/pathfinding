package com.jerrickhoang.pathfinding.core;

import java.util.Random;



public class GameState {
	
	public Robot robot;
	public GameMap map;
	
	public GameState(int frameWidth, int frameHeight) {
		map = new GameMap(frameWidth, frameHeight);
		robot = initRandomRobot();
	}
	
	public Robot initRandomRobot() {
		Random r = new Random();
		return new Robot(r.nextDouble() * map.getWidth(), r.nextDouble() * map.getHeight());
	}
	
	public Robot robot() {
		return robot;
	}

}
