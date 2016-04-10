package welzl;

import supportGUI.Circle;

public class MyCircle {
	
	private MyPoint p;
	private double r;

	public MyCircle(MyPoint myPoint, double d) {
		p = myPoint;
		r = d;
	}

	public Circle toCircle() {
		return new Circle(p.toPoint(), (int)r + 2);
	}

	public double getRadius() {
		return r;
	}

	public MyPoint getCenter() {
		return p;
	}
	
	

}
