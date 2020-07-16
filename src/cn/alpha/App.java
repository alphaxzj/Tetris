package cn.alpha;

import cn.alpha.controller.Controller;
import cn.alpha.entities.Ground;
import cn.alpha.entities.ShapeFactory;
import cn.alpha.view.GamePanel;

import javax.swing.*;

/**
 * @ClassName App
 * @Author alpha
 * @Date 2020/7/16 23:15
 * @Version 1.0
 * @Description TODO
 **/
public class App extends Thread {


    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        GamePanel gamepanel = new GamePanel();
        Ground ground = new Ground();
        ShapeFactory shapefactory = new ShapeFactory();
        Controller controller = new Controller(ground, shapefactory, gamepanel);
        frame.setSize(gamepanel.getWidth() + 6, gamepanel.getHeight() + 29);
        frame.addKeyListener(controller);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);
        frame.add(gamepanel);
        frame.setResizable(false);
        controller.newGame();
    }
}

