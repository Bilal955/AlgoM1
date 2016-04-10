package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import geometrie.Arete;

public class Steiner {

	private static Object[] calculShortestPaths(ArrayList<Point> points, int edgeThreshold) {
		int [][] paths = new int[points.size()][points.size()];
		double [][] D = new double[points.size()][points.size()];
		double dist;

		for(int i=0 ; i<points.size() ; i++) {
			for(int j=0 ; j<points.size() ; j++) {
				dist = points.get(i).distance(points.get(j));
				if(dist <= edgeThreshold) 
					D[i][j] = dist;		
				else if(i == j) 
					D[i][j] =  0;
				else 
					D[i][j] =  Double.POSITIVE_INFINITY;

				paths[i][j] = j;
			}
		}
		for(int k=1 ; k<points.size() ; k++) {
			for(int i=0 ; i<points.size() ; i++) {
				for(int j=0 ; j<points.size() ; j++) {
					if(D[i][k] + D[k][j] < D[i][j]) {
						D[i][j] = D[i][k] + D[k][j];
						paths[i][j] = paths[i][k];
					}	
				} 
			}
		}

		Object [] res = {paths, D};
		return res;
	}




	public static ArrayList<Arete> calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
		ArrayList<Arete> K = new ArrayList<Arete>();
		double dist;
		Point u, v;
		int indU, indV;
		
		Object [] tmpRes = calculShortestPaths(points, edgeThreshold);
		int [][] paths = (int[][]) tmpRes[0];
		double [][]D = (double[][]) tmpRes[1];

		// Construire le graphe pondere
		for(int i=0 ; i<hitPoints.size() ; i++) {
			u = hitPoints.get(i);
			for(int j=i+1 ; j<hitPoints.size() ; j++) {
				v = hitPoints.get(j);
				indU = points.indexOf(u);
				indV = points.indexOf(v);
				dist = D[indU][indV];
				K.add(new Arete(u, v, dist));
			}
		}
		// Construire l'arbre couvrant TO
		ArrayList<Arete> T0 = Kruskal.kruskal(hitPoints, K);
		ArrayList<Arete> H = new ArrayList<Arete>();

		
		// Remplace chaque arete par le bon chemin
		for(Arete tmp : T0) { // U to V
			ArrayList<Arete> newChemin = new ArrayList<Arete>();
			int indPremier = points.indexOf(tmp.getPointA());
			int indDernier = points.indexOf(tmp.getPointB());
			Point pointPremier = tmp.getPointA();
			Point pointFinal = tmp.getPointB();
			
			int indNext = paths[indPremier][indDernier];
			Point nextPoint = points.get(indNext);
			
			int indCur = indPremier;
			Point curPoint = pointPremier;
			
			while(!curPoint.equals(pointFinal)) {
				newChemin.add(new Arete(curPoint, nextPoint, curPoint.distance(nextPoint)));
				curPoint = nextPoint;
				indCur = indNext;
				indNext = paths[indCur][indDernier];
				nextPoint = points.get(indNext);
			}
			H.addAll(newChemin);
		}

		// Dans H faire Tprim
		Set<Point> setTmp = new HashSet<Point>();
		for(Arete tmp : H) {
			setTmp.add(tmp.getPointA());
			setTmp.add(tmp.getPointB());
		}
		ArrayList<Arete> Tprim = Kruskal.kruskal(new ArrayList<Point>(setTmp), H);
		return Tprim;
	}


}
