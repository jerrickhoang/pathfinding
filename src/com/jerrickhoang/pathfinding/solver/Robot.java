package com.jerrickhoang.pathfinding.solver;

import com.jerrickhoang.pathfinding.geom.PFPoint;



	public class Robot{
		public PFPoint position;
		public PFPoint target;
		public PFPath path;
		public Goal goal;
		

		float speed;
		float speedX;
		float speedY;
		float moveAngle;

		public Robot(){
			path = new PFPath();
		}
		
		public Robot(double x, double y) {
			this.position = new PFPoint(x, y);
			path = new PFPath();
		}
		
		public void update(double seconds){

		}


	}
