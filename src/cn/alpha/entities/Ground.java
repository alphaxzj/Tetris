package cn.alpha.entities;

import cn.alpha.utils.Global;

import java.awt.Graphics;



/**
 * @author alpha
 */
public class Ground {

	private int[][]block=new int[Global.WIDTH][Global.HEIGHT];
	private int score=0;
	public void drawMe(Graphics g)
	{
		g.setColor(Global.GROUNDCOLOR);
		for(int row=0;row<Global.WIDTH;row++)
		{
			for(int col=0;col<Global.HEIGHT;col++)
			{
				if(block[row][col]==1)
				{
					g.fill3DRect(row*Global.SIZE+Global.BORDER, col*Global.SIZE+Global.BORDER, Global.SIZE-2*Global.BORDER+1, Global.SIZE-2*Global.BORDER+1,true);
				}
			}
		}
	}
	public void transform(Shape shape)
	{
		int temp[][]=shape.getBody();
		int x=shape.getx();
		int y=shape.gety();
		int left=shape.getLeftBoundary();
		int right=shape.getRightBoundary();
		int upper=shape.getUpperBoundary();
		int lower=shape.getLowerBoundary();
		for(int i=left;i<=right;i++)
		{
			for(int j=upper;j<=lower;j++)
			{
				if(temp[i][j]==1) {
					block[x+i][y+j]+=temp[i][j];
				}
			}
		}
	}
	public boolean isMoveable(Shape shape,int action)
	{
		int x,y;
		switch(action)
		{
		case Shape.ROTATE:
			int temp[][]=new int[4][4];
			int left=0,right=0,upper=0,lower=0;
			boolean findit=false;
			for(int i=0;i<4;i++)
				for(int j=0;j<4;j++)
					temp[i][j]=shape.body[3-j][i];
			for(int i=0;i<4;i++)
			{
				for(int j=0;j<4;j++)
				{
					if(temp[i][j]==1)
					{
						right=i;
						lower=j;
						if(findit==false)
						{
							left=i;
							upper=j;
							findit=true;
						}
					}
				}
			}
			if(shape.getx()<-left||shape.getx()>Global.WIDTH-1-right)
				return false;
			for(int i=left;i<=right;i++)
			{
				for(int j=upper;j<=lower;j++)
				{
					if(temp[i][j]+block[shape.getx()+i][shape.gety()+j]>1)
						return false;
				}
			}
			System.out.println("Shape's rotate");
			break;
		case Shape.DOWN:
			if(shape.gety()+1>=Global.HEIGHT-shape.getLowerBoundary())
				return false;
			else {
				for(int i=shape.getLeftBoundary();i<=shape.getRightBoundary();i++)
				{
					for(int j=shape.getUpperBoundary();j<=shape.getLowerBoundary();j++)
					{
						if(shape.gety()+j<0)
							return true;
						if(shape.body[i][j]+block[shape.getx()+i][shape.gety()+j+1]>1)
							return false;
					}
				}
			}
			break;
		case Shape.LEFT:
			if(shape.getx()-1<-shape.getLeftBoundary())
				return false;
			else
			{
				for(int i=shape.getLeftBoundary();i<=shape.getRightBoundary();i++)
				{
					for(int j=shape.getUpperBoundary();j<=shape.getLowerBoundary();j++)
					{
						if(shape.gety()+j<0)
							return true;
						if(shape.body[i][j]+block[shape.getx()+i-1][shape.gety()+j]>1)
							return false;
					}
				}
			}
			break;
		case Shape.RIGHT:
			if(shape.getx()+1>Global.WIDTH-1-shape.getRightBoundary())
				return false;
			else
			{
				for(int i=shape.getLeftBoundary();i<=shape.getRightBoundary();i++)
				{
					for(int j=shape.getUpperBoundary();j<=shape.getLowerBoundary();j++)
					{
						if(shape.gety()+j<0)
							return true;
						if(shape.body[i][j]+block[shape.getx()+i+1][shape.gety()+j]>1)
							return false;
					}
				}
			}
			break;
		}
		return true;
	}
	public void getScore()
	{
		int max=0;
		int []save=new int[Global.HEIGHT]; 
		for(int i=Global.HEIGHT-1;i>=0;i--)
		{
			boolean flag=true;
			for(int j=0;j<Global.WIDTH;j++)
			{
				if(block[j][i]==0)
				{
					flag=false;
					break;
				}
			}
			if(flag==true)
			{
				save[i]=max++;
			}
			else
			{
				save[i]=max;
			}
		}
		System.out.println("max="+max);
		for(int i=Global.HEIGHT-max-1;i>=0;i--)
		{
			for(int j=0;j<Global.WIDTH;j++)
			{
				block[j][i+save[i]]=block[j][i];
			}
		}
		score+=Math.pow(2, max-1);
		System.out.println("Score="+score);
	}
	public void init()
	{
		score=0;
		for(int row=0;row<Global.WIDTH;row++)
		{
			for(int col=0;col<Global.HEIGHT;col++)
			{
				block[row][col]=0;
			}
		}
	}
	public int[][] getBlock()
	{
		int [][]temp=new int[Global.WIDTH][Global.HEIGHT];
		for(int i=0;i<Global.WIDTH;i++)
		{
			for(int j=0;j<Global.HEIGHT;j++)
			{
				temp[i][j]=block[i][j];
			}
		}
		return temp;
	}
	public boolean isFull()
	{
		for(int i=0;i<Global.WIDTH;i++)
		{
			if(block[i][0]==1) {
				return true;
			}
		}
		return false;
	}
}
