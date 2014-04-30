package com.jerrickhoang.pathfinding.core;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;



	public class GameScreen extends JComponent {
		
		private static final int ROBOT_RADIUS = 5;
		
		private Image backImage;
		private Graphics2D backImageGraphics2D;
		private GameState gameState;
		
		public GameScreen(int frameWidth, int frameHeight) {
			super();
			gameState = new GameState(frameWidth, frameHeight);
		}
		
		public void render() {
			if (getWidth() <= 0 || getHeight() <= 0) {
				System.out.println(this.getClass().getSimpleName() + ": width &/or height <= 0!!!");
				return;
			}
			if (backImage == null || getWidth() != backImage.getWidth(null) || getHeight() != backImage.getHeight(null)) {
				backImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TRANSLUCENT);
			}
			backImageGraphics2D = (Graphics2D)backImage.getGraphics();
			renderWorld();
			backImageGraphics2D.dispose();
			if (getGraphics() != null) {
				getGraphics().drawImage(backImage, 0, 0, null);
				Toolkit.getDefaultToolkit().sync();
			}
		}

		protected void renderWorld() {
			Graphics2D g = backImageGraphics2D;

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			float backGroundGrey = 77f / 255f;
			g.setColor(new Color(backGroundGrey, backGroundGrey, backGroundGrey));
			g.fillRect(0, 0, getWidth(), getHeight());

			renderObstacles(g);
			renderRobot(g);

		}
		
		private void renderObstacles(Graphics2D g) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			float g4 = 0.1f;
			g.setColor(new Color(g4, g4, g4));
			ArrayList<Obstacle> stationaryObstacles = gameState.map.obstacles;
			for (int i = 0; i < stationaryObstacles.size(); i++) {
				g.fill(stationaryObstacles.get(i).getPolygon());
			}

		}
		
		private void renderRobot(Graphics2D g) {
			Robot r = gameState.robot();
			//render robot's path
			g.setColor(Color.LIGHT_GRAY);
			if (r.path.points.size() > 0) {
				PFPoint currentPoint = r.position;
				for (int j = 0; j < r.path.points.size(); j++) {
					PFPoint nextPoint = r.path.points.get(j);
					g.draw(new Line2D.Double(currentPoint.getX(), currentPoint.getY(), nextPoint.getX(), nextPoint.getY()));
					float d = 5f;
					g.fill(new Ellipse2D.Double(nextPoint.getX() - d / 2f, nextPoint.getY() - d / 2f, d, d));
					currentPoint = nextPoint;
				}
			}

			g.setColor(Color.RED);
			
			//draw robot
			g.fill(new Ellipse2D.Double(r.position.getX() - ROBOT_RADIUS, r.position.getY() - ROBOT_RADIUS, 
										2 * ROBOT_RADIUS, 2 * ROBOT_RADIUS));
		}
	}
