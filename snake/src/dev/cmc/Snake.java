package dev.cmc;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import dev.cmc.cmcGFX.Game;

public class Snake extends Game {

	int gridLen, gridSize = 50;

	ArrayList<Vec2> snake;
	Vec2 apple;
	Vec2 vel;

	public Snake(String title, int size, int FPS) {
		super(title, size, size, FPS);
		this.gridLen = size / gridSize;
		reset();
	}

	public void reset()
	{
		int x = (int)(Math.random() * gridLen) * gridSize;
		int y = (int)(Math.random() * gridLen) * gridSize;
		apple = new Vec2(x, y);

		x = (int)(Math.random() * gridLen) * gridSize;
		y = (int)(Math.random() * gridLen) * gridSize;
		snake = new ArrayList<Vec2>();
		snake.add(new Vec2(x, y));
		vel = new Vec2(0, 0);
	}

	@Override
	public void tick() {
		if (vel.y == 0)
		{
			if (keyManager.up)
			{
				vel.y = -gridSize;
				vel.x = 0;
			}
			if (keyManager.down)
			{
				vel.y = gridSize;
				vel.x = 0;
			}
		}
		if (vel.x == 0)
		{
			if (keyManager.left)
			{
				vel.x = -gridSize;
				vel.y = 0;
			}
			if (keyManager.right)
			{
				vel.x = gridSize;
				vel.y = 0;
			}
		}
	}

	void move()
	{
		if (vel.x == 0 && vel.y == 0) return;
		Vec2 head = new Vec2(snake.get(0).x, snake.get(0).y);
		head.x += vel.x;
		head.y += vel.y;

		if (head.x < 0 || head.y < 0 || head.x >= width || head.y >= height)
		{
			reset();
			return;
		}

		for (Vec2 pos : snake)
		{
			if (head.x == pos.x && head.y == pos.y)
			{
				reset();
				return;
			}
		}

		if (apple.x != head.x || apple.y != head.y)
		{
			snake.remove(snake.size() - 1);
		}
		else
		{
			int x = (int)(Math.random() * gridLen) * gridSize;
			int y = (int)(Math.random() * gridLen) * gridSize;
			apple = new Vec2(x, y);
		}
		snake.add(0, head);
	}

	@Override
	public void render(Graphics g) {
		move();
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);	
		g.setColor(Color.black);
		for (int i = 0; i < gridLen; i++)
		{
			for (int j = 0; j < gridLen; j++)
			{
				g.drawRect(i * gridSize, j * gridSize, gridSize, gridSize);
			}
		}

		g.setColor(Color.green);
		for (Vec2 pos : snake)
		{
			g.fillRect(pos.x, pos.y, gridSize, gridSize);
		}

		g.setColor(Color.red);
		g.fillRect(apple.x, apple.y, gridSize, gridSize);
	}

	class Vec2
	{
		int x, y;
		Vec2(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
}
