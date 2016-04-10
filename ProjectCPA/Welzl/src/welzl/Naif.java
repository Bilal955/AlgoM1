package welzl;

import java.awt.Point;
import java.util.ArrayList;

import supportGUI.Circle;

public class Naif {

	public static Circle calculCercleMinNaif(ArrayList<Point> points) {

		if (points.isEmpty()) {
			return null;
		}

		/************Pour faire le calcul avec Jarvis****************/
		Jarvis j = new Jarvis();
		points = j.enveloppeConvexeJarvis(points);
				
		double diam = 0;
		double r = 0;
		Point pa = new Point(), pb = new Point();

		for(Point p : points){
			for(Point p2 : points){
				if(p2.equals(p))
					continue;
				r = Math.sqrt(Math.pow(p.getX()-p2.getX(), 2) + Math.pow(p.getY()-p2.getY(), 2));
				if(diam < r){
					diam = r;
					pa = p;
					pb = p2;
				}
			}
		}

		MyPoint center = new MyPoint((pa.getX() + pb.getX())/2, (pa.getY() + pb.getY())/2);

		double radius = diam/2;

		if( ! containsPoints1(radius, center.getX(), center.getY(), points))
			return calculCercleMinTroisPoints(points);
			//System.out.println("Tout est contenu avec 2 points!");
		//else{
			//System.out.println("Avec Trois points");
			
		//}

		return new Circle(center.toPoint(),(int)radius);
	}







	public static Circle calculCercleMinTroisPoints(ArrayList<Point> points){

		if (points.size()<3) {
			return null;
		}


		double a, b, abis, bbis;   //Coeff directeur des droites pour trouver le GRAAL
		MyPoint GRAAL = new MyPoint(0, 0);
		Point GRAAL_FINAL = new Point();
		double rayon = Double.POSITIVE_INFINITY;
		double min = Double.POSITIVE_INFINITY;

		for(Point x1 : points){

			for(Point x2 : points){

				if(x2.equals(x1))
					continue;

				a = ((x1.getX() - x2.getX()) / (x2.getY() - x1.getY()));
				b = (Math.pow(x2.getX(), 2) - Math.pow(x1.getX(), 2) + Math.pow(x2.getY(), 2) - Math.pow(x1.getY(), 2))
						/ (2*(x2.getY() - x1.getY()));

				for(Point x3 : points){

					if(x3.equals(x1) || x3.equals(x2))
						continue;


					abis = ((x2.getX() - x3.getX()) / (x3.getY() - x2.getY()));
					bbis = (Math.pow(x3.getX(), 2) - Math.pow(x2.getX(), 2) + Math.pow(x3.getY(), 2) - Math.pow(x2.getY(), 2))
							/ (2*(x3.getY() - x2.getY()));

					//Intersection coordonnées des 2 droites
					if((abis - a) != 0){
						GRAAL = Tools.intersectionDroites(a, b, abis, bbis);
						rayon = (Math.sqrt(Math.pow(GRAAL.getX()-x1.getX(), 2) + Math.pow(GRAAL.getY()-x1.getY(), 2)));
					}
					else
						continue;

					if(containsPoints1(rayon, GRAAL.getX(), GRAAL.getY(), points)){
						if(min > rayon){
							min = rayon;
							GRAAL_FINAL = GRAAL.toPoint();
						}
					}
				}
			}
		}

		//System.out.println("FINISH : " + GRAAL_FINAL.getY() + " et " + min);
		return new Circle(GRAAL_FINAL, (int)min);
	}


	public static Boolean containsPoints1(double r, double cx, double cy, ArrayList<Point> points){
		for(Point p : points)
			if( Math.sqrt(Math.pow(cx-p.getX(), 2) + Math.pow(cy-p.getY(), 2)) > r )
				return false;
		return true;
	}
	
}
