package cn.alpha.entities;

import cn.alpha.listener.ShapeListener;

import java.util.Random;



/**
 * @author alpha
 */
public class ShapeFactory {

	private Shape shape;
	private int [][]body=new int[4][4];
	private int [][][]dir={	{{0,0,0,0},{0,0,0,0},{1,1,1,1},{0,0,0,0}},
							{{0,0,0,0},{0,1,1,0},{0,1,1,0},{0,0,0,0}},
							{{0,1,0,0},{0,1,1,0},{0,0,1,0},{0,0,0,0}},
							{{0,0,1,0},{0,1,1,0},{0,1,0,0},{0,0,0,0}},
							{{0,0,1,0},{0,1,1,0},{0,0,1,0},{0,0,0,0}},
							{{0,0,1,0},{0,0,1,0},{0,1,1,0},{0,0,0,0}},
							{{0,1,0,0},{0,1,0,0},{0,1,1,0},{0,0,0,0}}};
	public Shape getShape(ShapeListener shapelistener)
	{
		int random=new Random().nextInt(dir.length);
		int rotate=new Random().nextInt(4);
		for(int row=0;row<4;row++)
		{
			for(int col=0;col<4;col++)
			{
				body[row][col]=dir[random][row][col];
			}
		}
		shape=new Shape(body,rotate);
		shape.setStatus(rotate);
		shape.addShapeListener(shapelistener);
		return shape;
	}
}
