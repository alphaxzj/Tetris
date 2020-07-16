package cn.alpha.entities;

import cn.alpha.listener.ShapeListener;
import cn.alpha.utils.Global;

import java.awt.*;


/**
 * @author alpha
 */
public class Shape
{
	private int y = 0;
	private int x = 6;
	int[][] body = new int[4][4];
	int status = 0;
	protected int Left = 0;
	protected int Right = 0;
	protected int Upper = 0;
	protected int Lower = 0;
	public static final int ROTATE = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	private Thread thread;
	private ShapeListener shapelistener;
	private Color color;

	public Shape(int[][] body, int status)
	{
		this.body = body;
		this.status = status;
		this.y = (-getLowerBoundary() - 1);
		this.thread = new Thread(new ShapeDriver());
		this.thread.start();
	}

	private class ShapeDriver
			implements Runnable
	{
		private ShapeDriver() {}

		@Override
		public void run()
		{
			while (Shape.this.shapelistener.isMoveDownable(Shape.this))
			{
				if(Global.GAME_STATUS == 1){
					Shape.this.moveDown();
					Shape.this.shapelistener.shapeMoveDown(Shape.this);
					try
					{
						Thread.sleep(Global.SPEED);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void stop()
	{
		try
		{
			this.thread.interrupt();
			this.thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public int[][] getBody()
	{
		int[][] temp = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				temp[i][j] = this.body[i][j];
			}
		}
		return temp;
	}

	public void moveDown()
	{
		this.y += 1;
	}

	public void moveLeft()
	{
		this.x -= 1;
	}

	public void moveRight()
	{
		this.x += 1;
	}

	public void rotate()
	{
		int[][] temp = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				temp[i][j] = this.body[(3 - j)][i];
			}
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				this.body[i][j] = temp[i][j];
			}
		}
	}

	public void drawMe(Graphics g)
	{
		g.setColor(this.color);
		for (int row = 0; row < 4; row++)
		{
			for (int col = 0; col < 4; col++) {
				if ((col + this.y >= 0) &&
						(this.body[row][col] == 1)) {
					g.fill3DRect((row + this.x) * 25 + 1, (col + this.y) * 25 + 1, 24, 24, true);
				}
			}
			if (row + this.x >= 0) {}
		}
	}

	public void setStatus(int r)
	{
		for (int i = 0; i <= r; i++) {
			rotate();
		}
	}

	public void addShapeListener(ShapeListener shapelistener)
	{
		if (shapelistener != null) {
			this.shapelistener = shapelistener;
		}
	}

	public int getx()
	{
		return this.x;
	}

	public int gety()
	{
		return this.y;
	}

	public int getUpperBoundary()
	{
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				if (this.body[j][i] == 1) {
					return i;
				}
			}
		}
		return 0;
	}

	public int getLowerBoundary()
	{
		for (int i = 3; i >= 0; i--) {
			for (int j = 0; j <= 3; j++) {
				if (this.body[j][i] == 1) {
					return i;
				}
			}
		}
		return 0;
	}

	public int getLeftBoundary()
	{
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				if (this.body[i][j] == 1) {
					return i;
				}
			}
		}
		return 0;
	}

	public int getRightBoundary()
	{
		for (int i = 3; i >= 0; i--) {
			for (int j = 0; j <= 3; j++) {
				if (this.body[i][j] == 1) {
					return i;
				}
			}
		}
		return 0;
	}

	public void drawForecast(Graphics g, Ground ground)
	{
		int forecast = this.y;
		int[][] temp = ground.getBlock();
		boolean flag = true;
		do
		{
			if (forecast + 1 >= 32 - getLowerBoundary()) {
				flag = false;
			} else {
				for (int i = getLeftBoundary(); i <= getRightBoundary(); i++) {
					for (int j = getUpperBoundary(); j <= getLowerBoundary(); j++) {
						if ((forecast + j >= 0) &&
								(this.body[i][j] + temp[(getx() + i)][(forecast + j + 1)] > 1)) {
							flag = false;
						}
					}
				}
			}
			forecast++;
		} while (flag);
		forecast--;



		g.setColor(Global.FORECASTCOLOR);
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				if (this.body[row][col] == 1) {
					g.fill3DRect((row + this.x) * 25 + 1, (col + forecast) * 25 + 1, 24, 24, true);
				}
			}
		}
	}

	public void setColor(int c)
	{
		switch (c)
		{
			case 0:
				this.color = Color.GREEN;
				break;
			case 1:
				this.color = Color.BLUE;
				break;
			case 2:
				this.color = Color.ORANGE;
				break;
			case 3:
				this.color = Color.RED;
				break;
			case 4:
				this.color = Color.PINK;
				break;
			case 5:
				this.color = Color.YELLOW;
				break;
			case 6:
				this.color = Color.MAGENTA;
		}
	}
}
