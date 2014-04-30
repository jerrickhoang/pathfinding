package com.jerrickhoang.pathfinding.core;
import java.awt.AWTEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;



public class Engine {
	public static final int GAME_SPEED = 1;
	
	public JFrame frame;
	public GameScreen view;
	public volatile boolean keepRunning = true;

	final Object mutex = new Object();
	ArrayList<AWTEvent> events = new ArrayList<AWTEvent>();
	ArrayList<AWTEvent> eventsCopy = new ArrayList<AWTEvent>();


	Robot robot;
	Random rand = new Random(11);

	public Engine(){
		frame = new JFrame(this.getClass().getSimpleName());
		frame.setSize(1000, 800);
		frame.setLocationRelativeTo(null);
		view = new GameScreen(frame.getWidth(), frame.getHeight());
		frame.add(view);


		frame.setVisible(true);

		Thread gameLoopThread = new Thread("GameLoop"){
			public void run(){
				long lastUpdateNanos = System.nanoTime();
				while(keepRunning){
					long currentNanos = System.nanoTime();
					float seconds = (currentNanos - lastUpdateNanos)/1000000000f;
					view.render();
					try{ Thread.sleep(1);}catch(Exception e){}
					lastUpdateNanos = currentNanos;
				}
			}
		};
		gameLoopThread.setDaemon(false);
		gameLoopThread.start();
	}



	public static void main(String[] args){
		new Engine();
	}
}
