package algorithms;

import java.awt.Point;

import geometrie.MyCircle;
import geometrie.MyDroite;
import geometrie.MyPoint;
import geometrie.Tools;

public class Fermat {

//	public static void main(String [] args) {
//		MyPoint a = new MyPoint(2345, 146);
//		MyPoint b = new MyPoint(944, 14);
//		MyPoint c = new MyPoint(3, 3432);
//
//		Point ferm1 = FermatToricelliSimple(a, b, c);
//		Point ferm2 = FermatToricelli(a, b, c);
//		Point bary1 = Tools.getPointBarycentre(a, b, c);
//		MyPoint mferm1 = new MyPoint(ferm1);
//		MyPoint mferm2 = new MyPoint(ferm2);
//		MyPoint mbary = new MyPoint(bary1);
//
//		double distFerm1 = mferm1.distance(a) + mferm1.distance(b) + mferm1.distance(c);
//		double distFerm2 = mferm2.distance(a) + mferm2.distance(b) + mferm2.distance(c);
//		double distBary = mbary.distance(a) + mbary.distance(b) + mbary.distance(c);
//
//		System.out.println("Fermat1 = "+ferm1+", dist = "+distFerm1);
//		System.out.println("Fermat2 = "+ferm2+", dist = "+distFerm2);
//		System.out.println("Barycentre = "+mbary+", dist = "+distBary);
//	}

	
	
	
	public static Point FermatToricelli(Point a, Point b, Point c) {
		return FermatToricelli(new MyPoint(a), new MyPoint(b), new MyPoint(c));
	}
	public static Point FermatToricelliSimple(Point a, Point b, Point c) {
		return FermatToricelliSimple(new MyPoint(a), new MyPoint(b), new MyPoint(c));
	}


	public static Point FermatToricelliSimple(MyPoint pA, MyPoint pB, MyPoint pC) {
		MyPoint [] prim = Fermat.getPointsPrim(pA, pB, pC);
		MyPoint Aprim = prim[0];
		MyPoint Bprim = prim[1];

		double coeff1 = (Aprim.y - pA.y) / (Aprim.x - pA.x);
		double coeff2 = (Bprim.y - pB.y) / (Bprim.x - pB.x);
		MyDroite d1 = new MyDroite( coeff1, pA.y - coeff1*pA.x);
		MyDroite d2 = new MyDroite( coeff2, pB.y - coeff2*pB.x);		

		double a1 = d1.getCoeffDirecteur();
		double a2 = d2.getCoeffDirecteur();
		double b1 = d1.getConstante();
		double b2 = d2.getConstante();

		MyPoint inter = new MyPoint((b2 - b1) / (a1 - a2), ((a1*b2) - (a2*b1)) / (a1 - a2));
		return new Point(Math.round((int)inter.getX()), (int)Math.round(inter.getY()));
	}


	public static Point FermatToricelli(MyPoint pA, MyPoint pB, MyPoint pC) {
		MyPoint [] prim = Fermat.getPointsPrim(pA, pB, pC);
		MyPoint Aprim = prim[0];
		MyPoint Bprim = prim[1];
		MyPoint Cprim = prim[2];

		MyCircle c1 = MyCircle.getCercleCirconscrit(Aprim, pB, pC);
		MyCircle c2 = MyCircle.getCercleCirconscrit(Bprim, pC, pA);
		MyCircle c3 = MyCircle.getCercleCirconscrit(Cprim, pA, pB);



		// Cercle circonscrit ok

		MyPoint A = c1.getCentre();
		double r = c1.getRayon();
		MyPoint B = c2.getCentre();
		double R = c2.getRayon();

		double xa = A.getX();
		double ya = A.getY();

		double xb = B.getX();
		double yb = B.getY();

		double a = 2 * (xb - xa);
		double b = 2 * (yb - ya);
		double c = ((xb-xa)*(xb-xa)) + ((yb-ya)*(yb-ya)) - R*R + r*r;
		double delta = ((2*a*c)*(2*a*c)) - 4*(a*a + b*b)*(c*c - b*b*r*r);

		double xp = xa + ( ((2*a*c) - Math.sqrt(delta)) / (2*(a*a + b*b)) );
		double xq = xa + ( ((2*a*c) + Math.sqrt(delta)) / (2*(a*a + b*b)) );

		double yp, yq;
		if( Math.abs(b) < 0.01) { // b == 0 ??
			yp = ya + (b/2) +// ou -
					Math.sqrt(R*R - Math.pow( (2*c - a*a)/(2*a), 2));
			yq = ya + (b/2) -// ou -
					Math.sqrt(R*R - Math.pow( (2*c - a*a)/(2*a), 2));
		}
		else { // b != 0
			yp = ya + ((c - a*(xp - xa)) / b);
			yq = ya + ((c - a*(xq - xa)) / b);
		}

		MyPoint p = new MyPoint(xp, yp);
		MyPoint q =  new MyPoint(xq, yq);

		//
		//				System.out.println("[DEB] \n");
		//				System.out.println("\ta = "+pA +", b = "+pB+", c = "+pC);
		//				System.out.println("\tc1 = "+c1);
		//				System.out.println("\tc2 = "+c2);
		//				System.out.println("\tc3 = "+c3);
		//				System.out.println("\tc1 collision ==> "+c1.collision(c2)+" "+c1.collision(c3));
		//				System.out.println("\tc2 collision ==> "+c2.collision(c1)+" "+c2.collision(c3));
		//				System.out.println("\tc3 collision ==> "+c3.collision(c1)+" "+c3.collision(c2));

		//				System.out.println("\tp = "+p+", q = "+q);
		//				System.out.println("\tC1 contains ==> "+"p :"+c1.contains(p) + ", q : " + c1.contains(q));
		//				System.out.println("\tC2 contains ==> "+"p :"+c2.contains(p) + ", q : " + c2.contains(q));
		//				System.out.println("\tC3 contains ==> "+"p :"+c3.contains(p) + ", q : " + c3.contains(q));
		//				System.out.println("Dist c3 q = "+c3.getCentre().distance(q));
		//				System.out.println("[FIN] \n");

		if(c3.contains(p))
			return new Point((int)Math.round(p.getX()), (int)Math.round(p.getY()));
		else if(c3.contains(q))
			return new Point((int)Math.round(q.getX()), (int)Math.round(q.getY()));
		else {
			System.out.println("WHAT ???!!!");
			return null;
		}
	}













	/////////////////////////////////////////////////////////////////////

	public static MyCircle centreCercleCircon(MyPoint s1, MyPoint s2, MyPoint s3){
		Point p1 = new Point((int)s1.getX(), (int)s1.getY());	
		Point p2 = new Point((int)s2.getX(), (int)s2.getY());	
		Point p3 = new Point((int)s3.getX(), (int)s3.getY());	
		return centreCercleCircon(p1, p2, p3);
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
	///////////////////////////////////////////////////////////////////////////////////////:




	public static MyPoint newVecteur(MyPoint a, MyPoint b) {
		return new MyPoint(b.getX()-a.getX(), b.getY()-a.getY());
	}

	public static double produitVectoriel(MyPoint u, MyPoint v) {
		return (u.getX()*v.getY()) - (u.getY() * v.getX());
	}

	public static MyPoint [] getPointsPrim(MyPoint a, MyPoint b, MyPoint c) {
		//prod = produitVectoriel(PQ, PZ);
		MyPoint res1, res2, res3;

		MyPoint pA = a;
		MyPoint pB = b;
		MyPoint pC = c;

		// APrim
		MyPoint Aprim = Tools.getAutrePointDuTriangle(pB, pC);
		MyPoint Aprim2 = Tools.getAutrePointDuTriangle2(pB, pC);

		MyPoint vectBC = newVecteur(pB, pC);
		MyPoint vectBAprim = newVecteur(pB, Aprim);
		MyPoint vectBA = newVecteur(pB, pA);
		double prod1 = produitVectoriel(vectBC, vectBA);
		double prod2 = produitVectoriel(vectBC, vectBAprim);
		if(prod1 * prod2 > 0)
			res1 = Aprim2;
		else
			res1 = Aprim;

		// BPrim
		MyPoint Bprim = Tools.getAutrePointDuTriangle(pC, pA);
		MyPoint Bprim2 = Tools.getAutrePointDuTriangle2(pC, pA);

		MyPoint vectCA = newVecteur(pC, pA);
		MyPoint vectCBprim = newVecteur(pC, Bprim);
		MyPoint vectCB = newVecteur(pC, pB);
		prod1 = produitVectoriel(vectCA, vectCB);
		prod2 = produitVectoriel(vectCA, vectCBprim);
		if(prod1 * prod2 > 0)
			res2 = Bprim2;
		else
			res2 = Bprim;

		// CPrim
		MyPoint Cprim = Tools.getAutrePointDuTriangle(pA, pB);
		MyPoint Cprim2 = Tools.getAutrePointDuTriangle2(pA, pB);

		vectBA = newVecteur(pB, pA);
		MyPoint vectBCprim = newVecteur(pB, Cprim);
		vectBC = newVecteur(pB, pC);
		prod1 = produitVectoriel(vectBA, vectBC);
		prod2 = produitVectoriel(vectBA, vectBCprim);
		if(prod1 * prod2 > 0)
			res3 = Cprim2;
		else
			res3 = Cprim;

		MyPoint [] res = new MyPoint[3];
		res[0] = res1;
		res[1] = res2;
		res[2] = res3;
		return res;
	}

}
