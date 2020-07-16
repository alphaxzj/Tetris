package cn.alpha.entities;

import cn.alpha.listener.ShapeListener;
import cn.alpha.utils.Global;

import java.awt.Graphics;




/**
 * @author alpha
 */
public class Shape {

	public static final int ROTATE=1;
	public static final int DOWN=2;
	public static final int LEFT=3;
	public static final int RIGHT=4;
	
	private int x= Global.WIDTH/2-2,y=0;
	int[][]body=new int[4][4];
	int status=0;
	private Thread thread;
	private ShapeListener shapelistener;
	protected int Upper=0,Lower=0,Left=0,Right=0;
	public Shape(int [][]body,int status)
	{
		this.body=body;
		this.status=status;
		y=-this.getLowerBoundary()-1;
		thread=new Thread(new ShapeDriver());
		thread.start();
	}
	
	private class ShapeDriver implements Runnable
	{
		@Override
		public void run() {
			while(shapelistener.isMoveDownable(Shape.this)&&Global.GAME_STATUS==Global.ALIVE)
			{
				moveDown();
				shapelistener.shapeMoveDown(Shape.this);
				try
				{
					Thread.sleep(Global.SPEED);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void stop()
	{
		try {
			thread.interrupt();
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int[][] getBody()
	{
		int temp[][]=new int[4][4];
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++)
			{
				temp[i][j]=body[i][j];
			}
		}
		return temp;
	}
	
	public void moveDown()
	{
		y++;
		System.out.println("Shape's moveDown");
	}
	
	public void moveLeft()
	{
		x--;
		System.out.println("Shape's moveLeft");
	}
	
	public void moveRight()
	{
		x++;
		System.out.println("Shape's moveRight");
	}
	
	public void rotate()
	{
		int [][]temp=new int[4][4];
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++)
			{
				temp[i][j]=body[3-j][i];
			}
		}	
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++)
			{
				body[i][j]=temp[i][j];
			}
		}
		
	}
	
	public void drawMe(Graphics g)
	{
		g.setColor(Global.SHAPECOLOR);
		for(int row=0;row<4;row++)
		{
			for(int col=0;col<4;col++)
			{
				if(col+y<0) {
					continue;
				}
				if(body[row][col]==1)
				{
					g.fill3DRect((row+x)*Global.SIZE+Global.BORDER, (col+y)*Global.SIZE+Global.BORDER, Global.SIZE-2*Global.BORDER+1, Global.SIZE-2*Global.BORDER+1,true);
				}
			}
			if(row+x<0)continue;
		}		
	}
	
	public void setStatus(int r)
	{
		for(int i=0;i<=r;i++)
		{
			rotate();
		}
	}
	
	
	public void addShapeListener(ShapeListener shapelistener)
	{
		if(shapelistener!=null)
		{
			this.shapelistener=shapelistener;
		}
	}
	
	public int getx()
	{
		return x;
	}
	
	public int gety()
	{
		return y;
	}
	
	public int getUpperBoundary()
	{
		for(int i=0;i<=3;i++) {
			for(int j=0;j<=3;j++) {
				if(body[j][i]==1) {
					return i;
				}
			}
		}
		return 0;
	}
	
	public int getLowerBoundary()
	{
		for(int i=3;i>=0;i--) {
			for(int j=0;j<=3;j++) {
				if(body[j][i]==1) {
					return i;
				}
			}
		}
		return 0;
	}
	
	public int getLeftBoundary()
	{
		for(int i=0;i<=3;i++) {
			for(int j=0;j<=3;j++) {
				if(body[i][j]==1) {
					return i;
				}
			}
		}
		return 0;
	}
	
	public int getRightBoundary()
	{
		for(int i=3;i>=0;i--) {
			for(int j=0;j<=3;j++) {
				if(body[i][j]==1) {
					return i;
				}
			}
		}
		return 0;
	}
	public void drawForecast(Graphics g,Ground ground)
	{
		int temp[][];
		int forecast=y;
		temp=ground.getBlock();
		boolean flag=true;
		
		while(true)
		{
			if(forecast+1>=Global.HEIGHT-getLowerBoundary()) {
				flag=false;
			} else {
				for(int i=getLeftBoundary();i<=getRightBoundary();i++)
				{
					for(int j=getUpperBoundary();j<=getLowerBoundary();j++)
					{
						if(forecast+j<0) {
							continue;
						}
						if(body[i][j]+temp[getx()+i][forecast+j+1]>1) {
							flag=false;
						}
					}
				}
			}
			forecast++;
			if(flag==false)
			{
				forecast--;
				break;
			}
		}
		g.setColor(Global.FORECASTCOLOR);
		for(int row=0;row<4;row++)
		{
			for(int col=0;col<4;col++)
			{
				if(body[row][col]==1)
				{
					g.fill3DRect((row+x)*Global.SIZE+Global.BORDER, (col+forecast)*Global.SIZE+Global.BORDER, Global.SIZE-2*Global.BORDER+1, Global.SIZE-2*Global.BORDER+1,true);
				}
			}
		}
		
	}
}
