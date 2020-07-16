package cn.alpha.entities;

import cn.alpha.utils.Global;

import java.awt.Graphics;



/**
 * @author alpha
 */
public class Ground
{
	private int[][] block = new int[Global.WIDTH][Global.HEIGHT];
	private int[][] color = new int[Global.WIDTH][Global.HEIGHT];
	private int score = 0;

	public void drawMe(Graphics g)
	{
		g.setColor(Global.GROUNDCOLOR);
		for (int row = 0; row < Global.WIDTH; row++) {
			for (int col = 0; col < Global.HEIGHT; col++) {
				if (this.block[row][col] == 1) {
					g.fill3DRect(row * 25 + 1, col * 25 + 1, 24, 24, true);
				}
			}
		}
	}

	public void transform(Shape shape)
	{
		int[][] temp = shape.getBody();
		int x = shape.getx();
		int y = shape.gety();
		int left = shape.getLeftBoundary();
		int right = shape.getRightBoundary();
		int upper = shape.getUpperBoundary();
		int lower = shape.getLowerBoundary();
		for (int i = left; i <= right; i++) {
			for (int j = upper; j <= lower; j++) {
				if (temp[i][j] == 1) {
					this.block[(x + i)][(y + j)] += temp[i][j];
				}
			}
		}
	}

	public boolean isMoveable(Shape shape, int action)
	{
		switch (action)
		{
			case 1:
				int[][] temp = new int[4][4];
				int left = 0;int right = 0;int upper = 0;int lower = 0;
				boolean findit = false;
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						temp[i][j] = shape.body[(3 - j)][i];
					}
				}
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						if (temp[i][j] == 1)
						{
							right = i;
							lower = j;
							if (!findit)
							{
								left = i;
								upper = j;
								findit = true;
							}
						}
					}
				}
				if ((shape.getx() < -left) || (shape.getx() > 15 - right)) {
					return false;
				}
				for (int i = left; i <= right; i++) {
					for (int j = upper; j <= lower; j++) {
						if (temp[i][j] + this.block[(shape.getx() + i)][(shape.gety() + j)] > 1) {
							return false;
						}
					}
				}
				break;
			case 2:
				if (shape.gety() + 1 >= Global.HEIGHT - shape.getLowerBoundary()) {
					return false;
				}
				for (int i = shape.getLeftBoundary(); i <= shape.getRightBoundary(); i++) {
					for (int j = shape.getUpperBoundary(); j <= shape.getLowerBoundary(); j++)
					{
						if (shape.gety() + j < 0) {
							return true;
						}
						if (shape.body[i][j] + this.block[(shape.getx() + i)][(shape.gety() + j + 1)] > 1) {
							return false;
						}
					}
				}
				break;
			case 3:
				if (shape.getx() - 1 < -shape.getLeftBoundary()) {
					return false;
				}
				for (int i = shape.getLeftBoundary(); i <= shape.getRightBoundary(); i++) {
					for (int j = shape.getUpperBoundary(); j <= shape.getLowerBoundary(); j++)
					{
						if (shape.gety() + j < 0) {
							return true;
						}
						if (shape.body[i][j] + this.block[(shape.getx() + i - 1)][(shape.gety() + j)] > 1) {
							return false;
						}
					}
				}
				break;
			case 4:
				if (shape.getx() + 1 > 15 - shape.getRightBoundary()) {
					return false;
				}
				for (int i = shape.getLeftBoundary(); i <= shape.getRightBoundary(); i++) {
					for (int j = shape.getUpperBoundary(); j <= shape.getLowerBoundary(); j++)
					{
						if (shape.gety() + j < 0) {
							return true;
						}
						if (shape.body[i][j] + this.block[(shape.getx() + i + 1)][(shape.gety() + j)] > 1) {
							return false;
						}
					}
				}
		}
		return true;
	}

	public void getScore()
	{
		int max = 0;
		int[] save = new int[Global.HEIGHT];
		for (int i = 31; i >= 0; i--)
		{
			boolean flag = true;
			for (int j = 0; j < Global.WIDTH; j++) {
				if (this.block[j][i] == 0)
				{
					flag = false;
					break;
				}
			}
			if (flag) {
				save[i] = (max++);
			} else {
				save[i] = max;
			}
		}
		System.out.println("max=" + max);
		for (int i = Global.HEIGHT - max - 1; i >= 0; i--) {
			for (int j = 0; j < Global.WIDTH; j++) {
				this.block[j][(i + save[i])] = this.block[j][i];
			}
		}
		int temp=this.score;
		this.score = ((int)(this.score + Math.pow(2.0D, max - 1)));
		if(this.score>temp){
			System.out.println("Score=" + this.score);
		}

	}

	public void init()
	{
		this.score = 0;
		for (int row = 0; row < Global.WIDTH; row++) {
			for (int col = 0; col < Global.HEIGHT; col++) {
				this.block[row][col] = 0;
			}
		}
	}

	public int[][] getBlock()
	{
		int[][] temp = new int[Global.WIDTH][Global.HEIGHT];
		for (int i = 0; i < Global.WIDTH; i++) {
			for (int j = 0; j < Global.HEIGHT; j++) {
				temp[i][j] = this.block[i][j];
			}
		}
		return temp;
	}

	public boolean isFull()
	{
		for (int i = 0; i < Global.WIDTH; i++) {
			if (this.block[i][0] == 1) {
				return true;
			}
		}
		return false;
	}
}
