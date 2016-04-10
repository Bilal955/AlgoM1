package algorithms;
import java.awt.Point;
import java.util.ArrayList;

public class MyCircle {

	private MyPoint centre;
	private double rayon;

	public MyCircle(MyPoint centre, double rayon) {
		super();
		this.centre = centre;
		this.rayon = rayon;
	}

	public MyCircle(Point centre, double rayon) {
		super();
		this.centre = new MyPoint(centre);
		this.rayon = rayon;
	}

	public MyPoint getCentre() {
		return centre;
	}

	public double getRayon() {
		return rayon;
	}

	public boolean contains(MyPoint p) { 
		return (centre.distance(p) < rayon) || (Math.abs(centre.distance(p) - rayon) < 0.001);
	}
	public boolean contains(Point p) {
		return contains(new MyPoint(p));
	}

	public boolean containsAll2(ArrayList<Point> points) {
		for(Point p : points) {
			if(!contains(new MyPoint(p)))
				return false;
		}
		return true;
	}

	public boolean containsAll(ArrayList<MyPoint> points) {
		for(MyPoint p : points) {
			if(!contains(p))
				return false;
		}
		return true;
	}

	public static MyCircle getCercleCirconscrit(MyPoint a, MyPoint b, MyPoint c) {
		MyDroite medAB = (new MySegment(a, b)).getEquationMediatrice();
		MyDroite medBC = (new MySegment(b, c)).getEquationMediatrice();
		double a1 = medAB.getCoeffDirecteur();
		double a2 = medBC.getCoeffDirecteur();
		double b1 = medAB.getConstante();
		double b2 = medBC.getConstante();

		if(sontAlignes(a, b, c, 0.01)) 
			return null;
		if((Math.abs(a1-a2) < 0.001) && (Math.abs(b2-b1) > 0.1)) { 
			// mediatrices presque paralleles, return enorme rayon on pourrait return null
		}

		MyPoint center = new MyPoint((b2 - b1) / (a1 - a2), ((a1*b2) - (a2*b1)) / (a1 - a2));
		MyCircle res = new MyCircle(center, center.distance(a));

		//if(aff) {
		//							System.out.println("[DEBUG]");
		//							System.out.println("\ta"+a+", b"+b+", c"+c);
		//							System.out.println("\ta1["+a1+"], "+"a2["+a2+"], "+"b1["+b1+"], "+"b2["+b2+"]");
		//							System.out.println("\t[medAB] ==> y = "+a1+"x + "+b1);
		//							System.out.println("\t[medBC] ==> y = "+a2+"x + "+b2);
		//							System.out.println("\t[Distance MA] = "+center.distance(a));
		//							System.out.println("\t[Distance MB] = "+center.distance(b));
		//							System.out.println("\t[Distance MC] = "+center.distance(c));
		//							System.out.println("\t[Centre] == "+center);
		//							System.out.println("\t[Rayon] = "+res.getRayon());
		//							System.out.println("[FIN DEBUG]");
		//}

		return res;
	}

	public static MyCircle getCercleCirconscrit(Point a, Point b, Point c) {
		MyPoint aPrim = new MyPoint(a);
		MyPoint bPrim = new MyPoint(b);
		MyPoint cPrim = new MyPoint(c);
		return getCercleCirconscrit(aPrim, bPrim, cPrim);
	}

	public String toString() {
		return "[Circle] { Center = "+this.centre+", Rayon = "+this.getRayon();
	}

	public static void main(String [] args) {
		MyPoint a = new MyPoint(1, 1);
		MyPoint b = new MyPoint(1, 3);
		MyPoint c = new MyPoint(-1, 0);
		MyCircle cer = getCercleCirconscrit(a,b,c);
		System.out.println("[RES] ==>  "+cer);
	}


	private static boolean sontAlignes(MyPoint a, MyPoint b, MyPoint c, double precision) {
		MyPoint AB = MyPoint.newVecteur(a, b);
		MyPoint AC = MyPoint.newVecteur(a, c);
		double tmp = Math.abs((AB.getX() * AC.getY()) - (AB.getY() * AC.getX()));
		return tmp < precision;
	}



}
