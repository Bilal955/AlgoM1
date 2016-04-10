package welzl;

import java.awt.Point;

public class Tools {

	public static Boolean containsPoint(double r, double cx, double cy, Point p){
		if( Math.sqrt(Math.pow(cx-p.getX(), 2) + Math.pow(cy-p.getY(), 2)) > r )
			return false;
		return true;
	}
	
	public static MyCircle centreCercleCircon(Point s1, Point s2, Point s3){
		
		double a = ((s1.getX() - s2.getX()) / (s2.getY() - s1.getY()));
		double b = (s2.getX() * s2.getX() - s1.getX() * s1.getX() + s2.getY() * s2.getY() - s1.getY() * s1.getY())
				/ (2*(s2.getY() - s1.getY()));
		
		double abis = ((s2.getX() - s3.getX()) / (s3.getY() - s2.getY()));
		double bbis = (s3.getX() * s3.getX() - s2.getX() * s2.getX() + s3.getY() * s3.getY() - s2.getY() * s2.getY())
				/ (2*(s3.getY() - s2.getY()));

		MyPoint G  = intersectionDroites(a, b, abis, bbis);
		double rayon = (Math.sqrt(Math.pow(G.getX()-s1.getX(), 2) + Math.pow(G.getY()-s1.getY(), 2)));
		return new MyCircle(G, rayon);
		
	}



	public static MyPoint intersectionDroites(double a, double b, double abis, double bbis){
		double x = (b - bbis) / (abis - a);
		double y = a*x + b;
		return new MyPoint(x, y);
	}


	public static boolean estDansCercle(Point r, MyCircle c) {
		if((c.getCenter().getX() - r.getX()) * (c.getCenter().getX() - r.getX()) + ((c.getCenter().getY() - r.getY()) * (c.getCenter().getY() - r.getY())) <= c.getRadius() * c.getRadius())   //Inf ou egale ??
			return true;
		return false;
	}

}
