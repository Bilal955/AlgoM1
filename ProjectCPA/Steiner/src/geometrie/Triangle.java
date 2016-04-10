package geometrie;

import java.awt.Point;

public class Triangle {

	private Point a;
	private Point b;
	private Point c;

	public Triangle(Point a, Point b, Point c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Point getA() {
		return a;
	}

	public Point getB() {
		return b;
	}

	public Point getC() {
		return c;
	}

	public Point pointDeFermat() {
		// Cercle circ a AB
		MySegment segAB = new MySegment(new MyPoint(a),new MyPoint(b));
		MyCircle AB = new MyCircle(segAB.getMilieu(), a.distance(b) / 2);
		// Cercle circ a AC
		MySegment segAC = new MySegment(new MyPoint(a),new MyPoint(c));
		MyCircle AC = new MyCircle(segAC.getMilieu(), a.distance(c) / 2);
		// Cercle circ a CB
		MySegment segCB = new MySegment(new MyPoint(c),new MyPoint(b));
		MyCircle CB = new MyCircle(segCB.getMilieu(), c.distance(b) / 2);

		MyPoint p1 = AB.getCentre();
		MyPoint p2 = AC.getCentre();
		MyPoint p3 = CB.getCentre();

		double x = Math.pow(p3.getX(), 2) - Math.pow(p1.getX(), 2) +
				Math.pow(p3.getY(), 2) - Math.pow(p1.getY(), 2);
		x = x / (2*(p3.getX() - p1.getX()) * 2*(p3.getY() - p1.getY()));
	
	
		double y = Math.pow(p3.getX(), 2) - Math.pow(p2.getX(), 2) +
				Math.pow(p3.getY(), 2) - Math.pow(p2.getY(), 2);
		y = y / (2*(p3.getX() - p2.getX()) * 2*(p3.getY() - p2.getY()));
		
		return new Point((int)x, (int)y);
	}
	
	public Triangle getTriangleEquil(Point a, Point b) {
		MyCircle c1 = new MyCircle(new MyPoint(a), a.distance(b)/2);
		MyPoint tmp = new MyPoint((a.x + b.x)/2, (a.y + b.y)/2);
		double diametre = Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
		MyCircle c2 = new MyCircle(tmp, diametre/2);
		
		return null;
	}

}
