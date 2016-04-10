package algorithms;
import java.awt.Point;
import java.util.ArrayList;

public class MyPoint {

	public double x, y;

	public MyPoint(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public MyPoint(Point p) {
		super();
		this.x = p.getX();
		this.y = p.getY();
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double distance(MyPoint p) {
		return Math.sqrt(Math.pow((p.x - x), 2) + Math.pow((p.y - y), 2));
	}

	public MyPoint getMilieu(MyPoint p) {
		return new MyPoint((x + p.getX())/2, (y + p.getY())/2);
	}

	public static MyPoint getMilieu(MyPoint a, MyPoint b) {
		return new MyPoint((a.getX() + b.getX())/2, (a.getY() + b.getY())/2);
	}

	public static MyPoint getMilieu(Point a, Point b) {
		return getMilieu(new MyPoint(a), new MyPoint(b));
	}

	public static MyPoint newVecteur(MyPoint a, MyPoint b) {
		return new MyPoint(b.x - a.x, b.y - a.y);
	}

	public double distanceCarre(MyPoint p) {
		return Math.pow((p.x - x), 2) + Math.pow((p.y - y), 2);
	}



	public static ArrayList<MyPoint> getListePoints(ArrayList<Point> points) {
		ArrayList<MyPoint> res = new ArrayList<MyPoint>();
		for(Point tmp : points)
			res.add(new MyPoint(tmp));
		return res;
	}

	public String toString() {
		return "[x = "+x+", y = "+y+"]";
	}

	
	// Faire un bon point equals
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		
		if (obj instanceof MyPoint) {
			MyPoint pt = (MyPoint)obj;
			return (Math.abs(x - pt.x) < 0.001) && (Math.abs(y - pt.y) < 0.001);
		}
		return super.equals(obj);
	}

	public static ArrayList<MyPoint> pointsToMyPoints(ArrayList<Point> points) {
		ArrayList<MyPoint> res = new ArrayList<MyPoint>();
		for(Point p : points) {
			res.add(new MyPoint(p));
		}
		return res;
	}

}
