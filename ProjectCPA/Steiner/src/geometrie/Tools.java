package geometrie;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import algorithms.Tree2D;

public class Tools {

	
	public static ArrayList<Arete> Tree2Aretes(Tree2D arbre) {
		ArrayList<Arete> res = new ArrayList<Arete>();

		Point cur = arbre.getRoot();
		for(Tree2D voisin : arbre.getSubTrees()) 
			res.add(new Arete(cur, voisin.getRoot(), cur.distance(voisin.getRoot())));
		for(Tree2D voisin : arbre.getSubTrees()) 
			res.addAll(Tree2Aretes(voisin));

		return res;
	}
	
	
	public static double scoreTree2D(Tree2D tree) {
		double score = tree.distanceRootToSubTrees();
		for(Tree2D tmp : tree.getSubTrees()) 
			score += scoreTree2D(tmp);
		return score;
	}

	public static double scoreListeAretes(ArrayList<Arete> aretes) {
		double res = 0;
		for(Arete tmp : aretes)
			res += tmp.getPoids();
		return res;
	}

	public static Tree2D AreteToTree(ArrayList<Arete> solution) {
		return AreteToTreeRec(solution.get(0).getPointA(), solution);
	}

	@SuppressWarnings("unchecked")
	private static Tree2D AreteToTreeRec(Point myPoint, ArrayList<Arete> solution) {
		ArrayList<Arete> solution2; 
		Point cur = myPoint;
		ArrayList<Point> fils = getAllExtre(cur, solution);
		Tree2D cur2D = new Tree2D(cur, new ArrayList<Tree2D>());

		if(fils.isEmpty())
			return cur2D;

		for(Point tmp : fils) {
			solution2 = (ArrayList<Arete>) solution.clone();
			Arete branche = getArete(myPoint, tmp, solution2);
			solution2.remove(branche);

			Tree2D fils2D = AreteToTreeRec(tmp, solution2);
			cur2D.getSubTrees().add(fils2D);
		}
		return cur2D;
	}

	private static Arete getArete(Point point1, Point point2, ArrayList<Arete> solution) {
		for(Arete tmp : solution) {
			if(tmp.getPointA().equals(point1) && tmp.getPointB().equals(point2))
				return tmp;
			if(tmp.getPointA().equals(point2) && tmp.getPointB().equals(point1))
				return tmp;
		}
		System.out.println("DEBUFFFFFFFFGGGGGGGGGGGG !!!");
		return null;
	}

	private static ArrayList<Point> getAllExtre(Point cur, ArrayList<Arete> solution) {
		ArrayList<Point> res = new ArrayList<Point>();

		for(Arete a : solution) {
			if(a.getPointA().equals(cur)) 
				res.add(a.getPointB());
			else if(a.getPointB().equals(cur))
				res.add(a.getPointA());

		}
		return res;
	}

	public static ArrayList<Point> AretesToPoints(ArrayList<Arete> aretes) {
		Set<Point> setTmp = new HashSet<Point>();
		for(Arete tmp : aretes) {
			setTmp.add(tmp.getPointA());
			setTmp.add(tmp.getPointB());
		}
		return new ArrayList<Point>(setTmp);
	}

	public static MyPoint getAutrePointDuTriangle(MyPoint a, MyPoint b) { // OK!
		double x = a.x + (b.x - a.x)*Math.cos(Math.PI / 3) - (b.y - a.y)*Math.sin(Math.PI/3);
		double y = a.y + (b.x - a.x)*Math.sin(Math.PI/3) + (b.y - a.y)*Math.cos(Math.PI/3);
		return new MyPoint(x, y);
	}

	public static Point FermatToricelli(Point pointA, Point pointB, Point pointC) {
		//System.out.println("[DEBUG]");
		MyPoint pA = new MyPoint(pointA);
		MyPoint pB = new MyPoint(pointB);
		MyPoint pC = new MyPoint(pointC);
		
		MyPoint Aprim = Tools.getAutrePointDuTriangle(pB, pC);
		MyPoint Bprim = Tools.getAutrePointDuTriangle(pC, pA);
		MyPoint Cprim = Tools.getAutrePointDuTriangle(pA, pB);

		// OK
	//	System.out.println("Others points = "+Aprim+ " | "+Bprim+ " | "+Cprim);
		//System.out.println("Test Eq ==> "+Aprim.distance(pB)+ " "+Aprim.distance(pC) + " "+pB.distance(pC));
		//System.out.println("Test Eq ==> "+Bprim.distance(pC)+ " "+Bprim.distance(pA) + " "+pC.distance(pA));
		//System.out.println("Test Eq ==> "+Cprim.distance(pA)+ " "+Cprim.distance(pB) + " "+pA.distance(pB));
		
		MyCircle c1 = MyCircle.getCercleCirconscrit(Aprim, pB, pC);
		MyCircle c2 = MyCircle.getCercleCirconscrit(Bprim, pC, pA);
		//System.out.println("C2 = "+Bprim+ " | "+pC+ " | "+pA);
		MyCircle c3 = MyCircle.getCercleCirconscrit(Cprim, pA, pB);
		if(c1 == null || c2 == null || c3 == null)
			return null;
		
//		System.out.println("\t"+pA);
//		System.out.println("\t"+pB);
//		System.out.println("\t"+pC);
//		System.out.println();
//		System.out.println("\t"+c1);
//		System.out.println("\t"+c2);
//		System.out.println("\t"+c3);

		
//		System.out.println("dist 1 = " +c1.getCentre().distance(Aprim) 
//				+" "+c1.getCentre().distance(pB)
//				+" "+c1.getCentre().distance(pC));
//		
//		System.out.println("dist 2 = " +c2.getCentre().distance(Bprim) 
//				+" "+c2.getCentre().distance(pC)
//				+" "+c2.getCentre().distance(pA));
//		
//		System.out.println("dist 3 = " +c3.getCentre().distance(Cprim) 
//				+" "+c3.getCentre().distance(pA)
//				+" "+c3.getCentre().distance(pB));
		
		MyPoint A = c1.getCentre();
		double r = c1.getRayon();
		MyPoint B = c2.getCentre();
		double R = c2.getRayon();

		double xa = A.getX();
		double ya = A.getY();

		double xb = B.getX();
		double yb = B.getY();

		///////////
		double a = 2 * (xb - xa);
		double b = 2 * (yb - ya);
		double c = Math.pow(xb - xa, 2) + 
				Math.pow(yb - ya, 2) - R*R + r*r;
		double delta = ((2*a*c)*(2*a*c)) -
				4*(a*a + b*b)*(c*c - b*b*r*r);
		///////////////////////////

		double xp = xa + 
				( ((2*a*c) - Math.sqrt(delta)) / (2*(a*a + b*b)) );
		double xq = xa +
				(((2*a*c) + Math.sqrt(delta)) / (2*(a*a + b*b)));

		double yp, yq;
		if(((int)b) == 0) {
			// xp == xq, a tester
			//System.out.println(xp + " == "+xq);
			yp = ya + (b/2) + // ou -
					Math.sqrt(R*R - Math.pow( (2*c - a*a)/2*a, 2));

			yq = pA.getY() + (b/2) + // ou -
					Math.sqrt(R*R - Math.pow( (2*c - a*a)/2*a, 2));
		}
		else { // b != 0
			yp = ya + ((c - a*(xp - xa)) / b);
			yq = ya + ((c - a*(xq - xa)) / b);
		}

		//System.out.println("[DEBUG]");
		MyPoint p = new MyPoint(xp, yp);
		MyPoint q = new MyPoint(xq, yq);
		//System.out.println("P = "+p);
		//System.out.println("Q = "+q);
		if(c3.contains(p))
			return new Point((int)p.getX(), (int)p.getY());
		else if(c3.contains(q))
			return new Point((int)q.getX(), (int)q.getY());
		else
			return null;
	}


}
