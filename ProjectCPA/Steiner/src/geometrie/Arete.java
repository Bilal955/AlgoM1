package geometrie;

import java.awt.Point;

public class Arete {

	private Point a;
	private Point b;

	private double poids;
	
	public Arete(Point a, Point b) {
		this.a = a;
		this.b = b;
		this.poids = a.distance(b);
	}

	public Arete(Point a, Point b, double poids) {
		this.a = a;
		this.b = b;
		this.poids = poids;
	}

	public Point getPointA() {
		return a;
	}
	
	public Point getPointB() {
		return b;
	}
	
	public double getPoids() {
		return poids;
	}
	
	public String toString() {
		return "["+a+", "+b+"]";
	}
	
}
