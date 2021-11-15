package dev.cmc.cmcGFX;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public abstract class Game implements Runnable {
	
	private final int SECOND_IN_NS = 1000000000;
	private int FPS;

	public String title;
	public int width, height;
	
	public Display display;
	public KeyManager keyManager;
	
	private BufferStrategy bs;
	private Graphics g;
	
	public Game(String title, int width, int height, int FPS) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.FPS = FPS;
		display = new Display(title, width, height);
		keyManager = new KeyManager();
		display.getFrame().addKeyListener(keyManager);
	}
	
	@Override
	public void run() {
		
		double nsPerTick = SECOND_IN_NS / FPS;
		long lastTime = System.nanoTime(), now;
		double delta = 0;
		long timer = 0;
		int frames = 0;
		
		while (true) {
			now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			timer += now - lastTime;
			lastTime = now;
			
			mTick();
			if (delta >= 1) {
				mRender();
				frames++;
				delta--;
			}
			
			if (timer >= SECOND_IN_NS) {
				display.updateFPS(frames);
				frames = 0;
				timer = 0;
			}
		}
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	private void mRender() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			bs = display.getCanvas().getBufferStrategy();
		}
		
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		
		render(g);

		bs.show();
		g.dispose();
	}

	private void mTick()
	{
		keyManager.tick();
		tick();
	}
	
}
