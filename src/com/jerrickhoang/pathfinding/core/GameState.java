package com.jerrickhoang.pathfinding.core;
import java.util.ArrayList;



public class GameState {
	
	public Robot robot;
	public ArrayList<Obstacle> obstacles;
	
	public GameState() {
		robot = new Robot();
		obstacles = new ArrayList<Obstacle>();
	}
	
	public Robot robot() {
		// TODO Auto-generated method stub
		return robot;
	}

}
