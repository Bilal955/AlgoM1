package algorithms;


import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RegleDuBarycentre {


	public static Tree2D regleDuBarycentre3(Tree2D arbreRouge, ArrayList<Point> pointsBleu) {
		ArrayList<Arete> arbreCur = new ArrayList<Arete>(Tools.Tree2Aretes(arbreRouge));
		Arete a1 = null, a2 = null, a3 = null;
		Point a = null, b = null, c = null;
		double oldScore, newScore;

		////////////////////////////////////////////////////////



		// Parcourir les sommets et determiner une config A, B, C ou B et C sont voisins de A
		int size = arbreCur.size();
		for(int i=0 ; i<size ; i++) {
			for(int j=0 ; j<size ; j++) {
				/* Recuperation du triangle */
				a1 = arbreCur.get(i);
				a2 = arbreCur.get(j);
				if(a1.equals(a2))
					continue;
				Point pointCommun = pointsCommun(a1, a2);
				if(pointCommun == null)
					continue;
				/* Les 3 points du triangle */
				a = pointCommun;
				b = (a1.getPointA().equals(pointCommun)) ? a1.getPointB() : a1.getPointA();
				c = (a2.getPointA().equals(pointCommun)) ? a2.getPointB() : a2.getPointA();

				/* Recuperation du barycentre */
				Point I = getPointBarycentre(a, b, c);

				/*System.out.println("[DEB]");
				System.out.println("BARYCENTRE = "+I+", res = "+(
						I.distance(a)+I.distance(b)+I.distance(c))+"\na = "+a +", b = "+b+"c ="+c);	
				System.out.println("[FIN]");*/
				//				I = Tools.FermatToricelli(a, b, c);
				//				if(I == null) {
				//					System.out.println("================> I == null");
				//					I = getPointBarycentre(a, b, c);
				//				}
				//				System.out.println("[Fermat] = "+I+", res = "+(
				//						I.distance(a)+I.distance(b)+I.distance(c)));
				//				System.out.println("[FIN]");
				Point newI = I; ///////////////////////////////////////////////////////////
				//newI = getPlusProcheDansLeTriangle(pointsBleu, I, a, b, c);
				if(newI == null) 
					continue;

				// Nouvelle arbre
				Point plusProche = getPlusProcheDansLeTriangle(pointsBleu, I, b, c, a);
				Arete AI = null, BI = null, CI = null;
				if(plusProche != null){
					AI = new Arete(a, plusProche);
					BI = new Arete(b, plusProche);
					CI = new Arete(c, plusProche);
					newScore = AI.getPoids() + BI.getPoids() + CI.getPoids();
				}
				else
					newScore = a1.getPoids() + a2.getPoids();

				oldScore = a1.getPoids() + a2.getPoids();

				if(newScore < oldScore && plusProche != null) {
						arbreCur.remove(a1);
						arbreCur.remove(a2);
						if(a3 != null) {
							System.out.println("aSUP EXISTEEEEEEEEEEEEEEee");
							System.exit(0);
							arbreCur.remove(a3); ///////////////////// ATTENTION
						}
						arbreCur.add(AI);
						arbreCur.add(BI);
						arbreCur.add(CI);

						arbreRouge = Tools.AreteToTree(arbreCur);
						//pointsBleu.remove(plusProche);
				}
			}
		}
		return arbreRouge;
	}

	public static Tree2D regleDuBarycentre(Tree2D arbreRouge, ArrayList<Point> pointsBleu) {

		ArrayList<Arete> arbreCur = Tools.Tree2Aretes(arbreRouge);
		Arete a1 = null, a2 = null, a3 = null;
		Point a = null, b = null, c = null;
		double oldScore, newScore;

		Set<Arete> res = new HashSet<Arete>();
		res.addAll(arbreCur);

		// Parcourir les sommets et determiner une config A, B, C ou B et C sont voisins de A
		int size = arbreCur.size();
		for(int i=0 ; i<size ; i++) {
			for(int j=0 ; j<size ; j++) {
				/* Recuperation du triangle */
				a1 = arbreCur.get(i);
				a2 = arbreCur.get(j);
				if(a1.equals(a2))
					continue;
				Point pointCommun = pointsCommun(a1, a2);
				if(pointCommun == null)
					continue;
				/* Les 3 points du triangle */
				a = pointCommun;
				b = (a1.getPointA().equals(pointCommun)) ? a1.getPointB() : a1.getPointA();
				c = (a2.getPointA().equals(pointCommun)) ? a2.getPointB() : a2.getPointA();

				/* Recuperation du barycentre */
				//Point I = getPointBarycentre(a, b, c);
				Point I = Tools.FermatToricelli(a, b, c);
				//Point newI = getPlusProcheDansLeTriangle(pointsBleu, I, a, b, c);
				Point newI = I;
				if(newI == null) {
					//System.out.println("ERRRRRRREEEEEEEEEEEEEEEUUUUUUUUUUUUUURRRRRRRRRRRRR NEWI = NULL");
					continue;
				}


				// Nouvelle arbre
				Arete AI = new Arete(a, newI);
				Arete BI = new Arete(b, newI);
				Arete CI = new Arete(c, newI);

				oldScore = a1.getPoids() + a2.getPoids();
				newScore = AI.getPoids() + BI.getPoids() + CI.getPoids();
				if(newScore < oldScore) {
					System.out.println("-------> oldScore = "+oldScore+", newScore = "+newScore);
					//					arbreCur.remove(a1);
					//					arbreCur.remove(a2);
					//					if(a3 != null) {
					//						System.out.println("aSUP EXISTEEEEEEEEEEEEEEee");
					//						arbreCur.remove(a3); ///////////////////// ATTENTION
					//					}
					//					arbreCur.add(AI);
					//					arbreCur.add(BI);
					//					arbreCur.add(CI);
					res.remove(a1);
					res.remove(a2);
					res.add(AI);
					res.add(BI);
					res.add(CI);
				}
			}
		}

		//System.out.println("SIZE OLD TREE = "+arbreCur.size()+" | SIZE NEW TREE = "+arbreCur.size());
		//System.out.println("OLDSCORE = "+Tools.scoreTree2D(arbreRouge)+", NEWSCORE = "+Tools.scoreTree2D(Tools.AreteToTree(arbreCur)));
		//return Tools.AreteToTree(arbreCur);
		System.out.println("================================================== >RES = "+res.size());
		ArrayList<Arete> tmpList = new ArrayList<Arete>(res);
		System.out.println("================================================== >tmpList= "+tmpList.size());
		return Tools.AreteToTree(tmpList);
	}






	private static Arete getPointArete(ArrayList<Arete> aretes, Point a, Point b) {
		for(Arete tmp : aretes) {
			if(tmp.getPointA().equals(a) && tmp.getPointB().equals(b))
				return tmp;
			if(tmp.getPointA().equals(b) && tmp.getPointB().equals(a))
				return tmp;
		}
		return null;
	}



	private static Point getPlusProche(ArrayList<Point> points, Point p, Point a, Point b, Point c) {
		Point res = null;
		double distMin = Double.MAX_VALUE;

		for(Point tmp : points) {
			if(tmp.equals(a) || tmp.equals(b) || tmp.equals(c))
				continue;
			if(distMin < tmp.distance(p))
				continue;


			System.out.println("DEBUG 3");
			distMin = tmp.distance(p);
			res = tmp;
		}
		//if(res == null)
		//System.out.println("[getPlusProche] RETURN NULL !!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return res;
	}


	private static Point getPlusProcheDansLeTriangle(ArrayList<Point> points, Point p, Point a, Point b, Point c) {
		Point res = null;
		double distMin = Double.MAX_VALUE;

		for(Point tmp : points) {
			if(tmp.equals(a) || tmp.equals(b) || tmp.equals(c))
				continue;
			if(distMin < tmp.distance(p))
				continue;
			//System.out.println("DEBUG 1");
			if(!interieurDuTriangle(tmp, a, b, c)) 
				continue;

			//	System.out.println("DEBUG 3");
			distMin = tmp.distance(p);
			res = tmp;
		}
		//	if(res == null)
		//	System.out.println("[getPlusProcheDansLeTriangle] RETURN NULL !!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return res;
	}



	public static boolean interieurDuTriangle(Point x, Point a, Point b, Point c) {
		double div = ((b.y - c.y)*(a.x - c.x) + (c.x - b.x)*(a.y - c.y));

		double l1 = ((b.y - c.y)*(x.x - c.x) + (c.x - b.x)*(x.y - c.y)) / div;
		double l2 = ((c.y - a.y)*(x.x - c.x) + (a.x - c.x)*(x.y - c.y)) / div;
		double l3 = 1 - l1 - l2;

		return ((l1 >= 0) && (l1 <= 1)) &&
				((l2 >= 0) && (l2 <= 1)) &&
				((l3 >= 0) && (l3 <= 1));
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


	public static Point getPointBarycentre(Point a, Point b, Point c) {
		double x = (a.getX() + b.getX() + c.getX())/3;
		double y = (a.getY() + b.getY() + c.getY())/3;
		return new Point((int)x, (int)y);
	}



}
