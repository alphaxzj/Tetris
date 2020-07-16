package cn.alpha.entities;

import cn.alpha.listener.ShapeListener;
import cn.alpha.utils.Global;

import java.awt.*;
import java.util.Random;



/**
 * @author alpha
 */
public class ShapeFactory {

	private Shape shape;
	private int [][]body=new int[Global.SHAPE_LEN][Global.SHAPE_LEN];
	private int [][][]dir={	{{0,0,0,0},{0,0,0,0},{1,1,1,1},{0,0,0,0}},
							{{0,0,0,0},{0,1,1,0},{0,1,1,0},{0,0,0,0}},
							{{0,1,0,0},{0,1,1,0},{0,0,1,0},{0,0,0,0}},
							{{0,0,1,0},{0,1,1,0},{0,1,0,0},{0,0,0,0}},
							{{0,0,1,0},{0,1,1,0},{0,0,1,0},{0,0,0,0}},
							{{0,0,1,0},{0,0,1,0},{0,1,1,0},{0,0,0,0}},
							{{0,1,0,0},{0,1,0,0},{0,1,1,0},{0,0,0,0}}};
	private Color[] color = new Color[7];
	public Shape getShape(ShapeListener shapelistener)
	{
		int random = new Random().nextInt(this.dir.length);
		int rotate = new Random().nextInt(Global.SHAPE_LEN);
		for (int row = 0; row < Global.SHAPE_LEN; row++) {
			for (int col = 0; col < Global.SHAPE_LEN; col++) {
				this.body[row][col] = this.dir[random][row][col];
			}
		}
		this.shape = new Shape(this.body, rotate);
		this.shape.setStatus(rotate);
		this.shape.setColor(random);
		this.shape.addShapeListener(shapelistener);
		return this.shape;
	}
}
