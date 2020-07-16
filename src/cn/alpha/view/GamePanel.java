package cn.alpha.view;

import cn.alpha.entities.Ground;
import cn.alpha.entities.Shape;
import cn.alpha.utils.Global;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;



/**
 * @author alpha
 */
public class GamePanel extends JPanel{

	private Shape shape;
	private Ground ground;
	private Image image;
	public GamePanel()
	{	
		setSize(Global.WIDTH*Global.SIZE,Global.HEIGHT*Global.SIZE);
		setBackground(Global.BGCOLOR);
	}
	public void display(Shape shape,Ground ground)
	{
		this.shape=shape;
		this.ground=ground;
		this.drawBufferImage();
		this.repaint();
		System.out.println("GamePanel's display");
	}
	public void drawBufferImage()
	{
		image=createImage(Global.WIDTH*Global.SIZE,Global.HEIGHT*Global.HEIGHT);
		Graphics g=image.getGraphics();
		g.setColor(Global.BGCOLOR);
		g.clearRect(0, 0, Global.WIDTH*Global.SIZE,Global.HEIGHT*Global.SIZE);
		g.fillRect(0, 0, Global.WIDTH*Global.SIZE,Global.HEIGHT*Global.SIZE);
		g.setColor(Global.LINECOLOR);
		for(int i=0;i<Global.WIDTH;i++)
		{
			g.drawLine((i+1)*Global.SIZE, 0,(i+1)*Global.SIZE , Global.HEIGHT*Global.SIZE);
		}
		for(int i=0;i<Global.HEIGHT;i++)
		{
			g.drawLine( 0,(i+1)*Global.SIZE,Global.WIDTH*Global.SIZE,(i+1)*Global.SIZE);
		}
		shape.drawForecast(g, ground);
		shape.drawMe(g);
		ground.drawMe(g);
		
		
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		g.drawImage(image, 0, 0, this);
	}
}
