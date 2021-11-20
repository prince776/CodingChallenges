package dev.cmc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import dev.cmc.cmcGFX.Game;

public class SegTreeViz extends Game {

	int a[];
	int n = 6;
	int gridSize = 80, gridLen = 0;

	Node t[];
	int animSpeed = 500;

	public SegTreeViz(String title, int width, int height, int FPS) {
		super(title, width, height, FPS);
		this.gridLen = width / gridSize;
		a = new int[]{1, 3, -2, 8, -7, 10, 2, 22};
		t = new Node[4 * n];
		new Thread()
		{
			public void run()
			{
				try {
					Thread.sleep(animSpeed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				build(a, 1, 0, n - 1, 1, 1);
			}
		}.start();
	}

	void build(int a[], int v, int tl, int tr, int x, int y) {
		try {
			Thread.sleep(animSpeed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t[v] = new Node(0, tl + x, tr + x + 1, y);
		if (tl == tr) {
			try {
				Thread.sleep(animSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t[v].val = a[tl];
			t[v].set = true;
		} else {
			int tm = (tl + tr) / 2;
			build(a, v*2, tl, tm, x, y + 2);
			build(a, v*2+1, tm+1, tr, x, y + 2);
			try {
				Thread.sleep(animSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t[v].val = t[v*2].val + t[v*2+1].val;
			t[v].c1 = t[2*v].mid;
			t[v].c2 = t[2*v + 1].mid;
			t[v].set = true;
		}
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g, Graphics2D g2) {
		g.setColor(new Color(49, 49, 49));
		g.fillRect(0, 0, width, height);
		for (int i = 0; i < 4 * n; i++)
		{
			if (t[i] != null)
				t[i].render(g2);
		}
	}

	class Node {
		int val;
		int l, r, y;
		float mid;
		float c1, c2;
		boolean set = false;
		Font f;
		Node(int val, int l, int r, int y) {
			this.val = val;
			this.l = l;
			this.r = r;
			this.y = y;
			mid = ((float)l + r) / 2f;
			c1 = c2 = mid;
			f = new Font("Serif", Font.PLAIN, 34);
		}

		void render(Graphics2D g)
		{
			g.setColor(new Color(81, 172, 51));
			g.setStroke(new BasicStroke(4));
			g.drawOval(l * gridSize - 5, y * gridSize, (r - l) * gridSize - 10, gridSize);

			if (set)
			{

				String toDraw = Integer.toString(val);
				FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
				int w = (int)f.getStringBounds(toDraw, frc).getWidth();
				int h = (int)f.getStringBounds(toDraw, frc).getHeight();
				g.setFont(f);
				g.drawString(toDraw, (int)(mid * gridSize) - w, y * gridSize + gridSize / 2 + h / 4);
			}
			

			if (c1 != mid) {
				g.drawLine((int)(mid * gridSize), (y + 1) * gridSize, (int)(c1 * gridSize), (y + 2) * gridSize);
			}
			if (c2 != mid) {
				g.drawLine((int)(mid * gridSize), (y + 1) * gridSize, (int)(c2 * gridSize), (y + 2) * gridSize);
			}
			
		}

	}

}
