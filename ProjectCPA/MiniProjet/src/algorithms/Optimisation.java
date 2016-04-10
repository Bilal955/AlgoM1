package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import geometrie.Arete;

public class Optimisation {


	public static ArrayList<Arete> Optimisation3Points(ArrayList<Arete> grapheRouges, ArrayList<Point> allPoints, int distance, ArrayList<Point> hitPoints) {
		ArrayList<Arete> arbreCur = new ArrayList<Arete>(grapheRouges);

		Arete a1 = null, a2 = null;
		Point a = null, b = null, c = null;
		Point pointCommun;
		double oldScore, newScore;

		int size = arbreCur.size();
		for(int i=0 ; i<size ; i++) {
			for(int j=i+1 ; j<size ; j++) {
				/* Recuperation du triangle */
				a1 = arbreCur.get(i);
				a2 = arbreCur.get(j);
				if((pointCommun = pointsCommun(a1, a2)) == null)
					continue;

				/* Les 3 points du triangle */
				a = pointCommun;
				b = (a1.getPointA().equals(pointCommun)) ? a1.getPointB() : a1.getPointA();
				c = (a2.getPointA().equals(pointCommun)) ? a2.getPointB() : a2.getPointA();

				/* Recuperation du barycentre/fermat */
				//				Point bary = Tools.getPointBarycentre(a, b, c);
				//				Point ferma = Fermat.FermatToricelliSimple(a, b, c); ///////////
				//				Point I = (getNewScore(bary, a, b, c) < getNewScore(ferma, a, b, c)) ? bary : ferma;
				//				I = Tools.getPlusProcheDansLeTriangle(allPoints, I, a, b, c);
				Point I = getBest(allPoints, a, b, c);
				if(I == null)
					continue;

				oldScore = a1.getPoids() + a2.getPoids();
				newScore = getNewScore(I, a, b, c);

				if(newScore < oldScore) {
					if(!hitPoints.contains(I))
						hitPoints.add(I);
					return Steiner.calculSteiner(allPoints, distance, hitPoints);
				}
			}
		}
		return arbreCur;
	}



	public static ArrayList<Arete> Optimisation4Points(ArrayList<Arete> grapheRouges, ArrayList<Point> allPoints, int distance, ArrayList<Point> hitPoints) {
		ArrayList<Arete> arbreCur = new ArrayList<Arete>(grapheRouges);
		Arete a1 = null, a2 = null;
		Point a = null, b = null, c = null;
		Point pointCommun;
		double oldScore, newScore;

		int size = arbreCur.size();
		for(int i=0 ; i<size ; i++) {
			for(int j=i+1 ; j<size ; j++) {
				/* Recuperation du triangle */
				a1 = arbreCur.get(i);
				a2 = arbreCur.get(j);
				if((pointCommun = pointsCommun(a1, a2)) == null)
					continue;

				a = pointCommun;
				b = (a1.getPointA().equals(pointCommun)) ? a1.getPointB() : a1.getPointA();
				c = (a2.getPointA().equals(pointCommun)) ? a2.getPointB() : a2.getPointA();
				Point d = null;
				Arete myAr = null;
				for(int k=0 ; k<size ; k++) {
					myAr = arbreCur.get(k);
					if(myAr.equals(a1) || myAr.equals(a2))
						continue;
					if(b.equals(myAr.getPointA()))
						d = myAr.getPointB();
					else if(b.equals(myAr.getPointB()))
						d = myAr.getPointA();
					else if(c.equals(myAr.getPointA()))
						d = myAr.getPointB();
					else if(c.equals(myAr.getPointB()))
						d = myAr.getPointA();
					if(d != null)
						break;
				}

				Point I = getBest(allPoints, a, b, c, d);
				if(I == null)
					continue;

				oldScore = a1.getPoids() + a2.getPoids() + myAr.getPoids();
				newScore = getNewScore(I, a, b, c, d);
				if(newScore < oldScore) {
					if(!hitPoints.contains(I))
						hitPoints.add(I);
					return Steiner.calculSteiner(allPoints, distance, hitPoints);
				}
			}
		}
		return arbreCur;
	}





	//	private static ArrayList<Arete> getAretes(ArrayList<Point> ptsRouge, int distance) {
	//		ArrayList<Arete> res = new ArrayList<Arete>();
	//		for(int i=0 ; i<ptsRouge.size() ; i++)
	//			for(int j=i+1 ; j<ptsRouge.size() ; j++)
	//				if(ptsRouge.get(i).distance(ptsRouge.get(j)) <= distance)
	//					res.add(new Arete(ptsRouge.get(i), ptsRouge.get(j)));
	//		return res;
	//	}


	private static double getNewScore(Point I, Point a, Point b, Point c) {
		if(I.equals(a)) 
			return a.distance(b) + a.distance(c);
		else if(I.equals(b)) 
			return  b.distance(a) + b.distance(c);
		else if(I.equals(c)) 
			return c.distance(a) + c.distance(b);
		else
			return  a.distance(I) + b.distance(I) + c.distance(I);
	}

	private static double getNewScore(Point I, Point a, Point b, Point c, Point d) {
		if(I.equals(a)) 
			return a.distance(b) + a.distance(c) + a.distance(d);
		else if(I.equals(b)) 
			return  b.distance(a) + b.distance(c)  + b.distance(d);
		else if(I.equals(c)) 
			return c.distance(a) + c.distance(b)  + c.distance(d);
		else if(I.equals(d)) 
			return d.distance(a) + d.distance(b)  + d.distance(c);
		else
			return  a.distance(I) + b.distance(I) + c.distance(I) + d.distance(I);
	}

	//	private static Point getPlusProche(ArrayList<Point> points, Point p, Point a, Point b, Point c) {
	//		Point res = null;
	//		double distMin = Double.MAX_VALUE;
	//
	//		for(Point tmp : points) {
	//			if(distMin < tmp.distance(p))
	//				continue;
	//			distMin = tmp.distance(p);
	//			res = tmp;
	//		}
	//		return res;
	//	}


	public static Point getBest(ArrayList<Point> l, Point a, Point b, Point c) {
		double min = Double.MAX_VALUE;
		double score;
		Point ptMin = null;

		for(Point tmp : l) {
			//if(!Tools.aLinterieurDuTriangle(tmp, a, b, c))
			//continue;
			score = tmp.distance(a) + tmp.distance(b) + tmp.distance(c);
			if(score < min) {
				min = score;
				ptMin = tmp;
			}
		}

		return ptMin;
	}

	public static Point getBest(ArrayList<Point> l, Point a, Point b, Point c, Point d) {
		double min = Double.MAX_VALUE;
		double score;
		Point ptMin = null;

		for(Point tmp : l) {
			//if(!Tools.aLinterieurDuTriangle(tmp, a, b, c))
			//continue;
			score = tmp.distance(a) + tmp.distance(b) + tmp.distance(c) + tmp.distance(d);
			if(score < min) {
				min = score;
				ptMin = tmp;
			}
		}

		return ptMin;
	}



	private static Point pointsCommun(Arete a, Arete b) {
		if(a.getPointA().equals(b.getPointA()))
			return a.getPointA();
		if(a.getPointA().equals(b.getPointB()))
			return a.getPointA();

		if(a.getPointB().equals(b.getPointA()))
			return a.getPointB();
		if(a.getPointB().equals(b.getPointB()))
			return a.getPointB();

		return null;
	}



}
