package cn.alpha.listener;


import cn.alpha.entities.Shape;

public interface ShapeListener {
	void shapeMoveDown(Shape shape);
	boolean isMoveDownable(Shape shape);
}
