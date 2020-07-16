package cn.alpha.controller;

import cn.alpha.entities.Ground;
import cn.alpha.entities.Shape;
import cn.alpha.entities.ShapeFactory;
import cn.alpha.listener.ShapeListener;
import cn.alpha.utils.Global;
import cn.alpha.view.GamePanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



/**
 * @author alpha
 */
public class Controller extends KeyAdapter implements ShapeListener {

	private Shape shape;
	private Ground ground;
	private ShapeFactory shapefactory=new ShapeFactory();
	private GamePanel gamepanel;
	@Override
	public void shapeMoveDown(Shape shape) {
			gamepanel.display(shape,ground);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP:
				if(ground.isMoveable(shape, Shape.ROTATE)&& Global.GAME_STATUS==Global.ALIVE)shape.rotate();
				break;
			case KeyEvent.VK_DOWN:
				if(ground.isMoveable(shape, Shape.DOWN)&&Global.GAME_STATUS==Global.ALIVE)shape.moveDown();
				break;
			case KeyEvent.VK_LEFT:
				if(ground.isMoveable(shape, Shape.LEFT)&&Global.GAME_STATUS==Global.ALIVE)shape.moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				if(ground.isMoveable(shape, Shape.RIGHT)&&Global.GAME_STATUS==Global.ALIVE)shape.moveRight();
				break;
			case KeyEvent.VK_N:
				newGame();
				break;
			case KeyEvent.VK_C:
				Global.GAME_STATUS=Global.ALIVE;
				break;
			case KeyEvent.VK_P:
				Global.GAME_STATUS=Global.PAUSE;
				break;
			case KeyEvent.VK_NUMPAD8:
				setShiftDown();
				System.out.println("Speed="+Global.SPEED);
				break;
			case KeyEvent.VK_NUMPAD5:
				Global.SPEED=800;
				break;
			case KeyEvent.VK_NUMPAD2:
				setSpeedUp();
				System.out.println("Speed="+Global.SPEED);
				break;
			default:
				break;
		}
		gamepanel.display(shape,ground);
	}
	public void newGame()
	{
		shape=shapefactory.getShape(this);
		ground.init();
		Global.GAME_STATUS=Global.ALIVE;
	}
	public Controller(Ground ground,ShapeFactory shapefactory,GamePanel gamepanel)
	{
		this.ground=ground;
		this.shapefactory=shapefactory;
		this.gamepanel=gamepanel;
	}
	@Override
	public synchronized boolean isMoveDownable(Shape shape) {
		if(ground.isMoveable(this.shape, Shape.DOWN))
		{
			return true;
		}
		else
		{
			this.ground.transform(shape);
			ground.getScore();
			this.shape=shapefactory.getShape(this);
			gameover();
			return false;
		}
	}
	public void gameover()
	{
		if(ground.isMoveable(this.shape, Shape.DOWN)==true&&ground.isFull()==true)
		{
			Global.GAME_STATUS=Global.OVER;
			System.out.println("GAME OVER!");
		}
	}
	public void setShiftDown()
	{
		Global.SPEED=Global.SPEED+100;
	}
	public void setSpeedUp()
	{
		if(Global.SPEED>100) {
			Global.SPEED=Global.SPEED-100;
		}
	}
	
}
