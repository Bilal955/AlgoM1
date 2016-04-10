package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import geometrie.Arete;
import geometrie.Tools;

public class DefaultTeam {


	public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
		double oldScore = 0, newScore = 0;
		ArrayList<Arete> result = null, bestRes = null; 

		System.out.println("--------- Steiner ------------");
		result = Steiner.calculSteiner(points, edgeThreshold, hitPoints);
		newScore = Tools.scoreListeAretes(result);
		System.out.println("newScore = "+newScore);
		
		System.out.println("--------- 4 points -----------");
		do {
			oldScore = newScore;
			bestRes = result; 
			result = Optimisation.Optimisation4Points(result, points, edgeThreshold, hitPoints);
			newScore = Tools.scoreListeAretes(result);
			System.out.println("oldScore = "+oldScore+", newScore = "+newScore);
		} 	while(newScore < oldScore);


		System.out.println("--------- 3 points -----------");
		result = bestRes; 
		do {
			oldScore = newScore;
			bestRes = result; 
			result = Optimisation.Optimisation3Points(result, points, edgeThreshold, hitPoints);
			newScore = Tools.scoreListeAretes(result);
			System.out.println("oldScore = "+oldScore+", newScore = "+newScore);
		} while(newScore < oldScore);


		System.out.println("Score final = "+oldScore);
		return Tools.AreteToTree(bestRes);
	}




	public Tree2D calculSteinerBudget(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
		int budget = 1664;
		System.out.println("Calcul avec budget de "+budget);
		return calculWithBudget_V3(points, edgeThreshold, hitPoints, budget);
	}
	private Tree2D calculWithBudget_V3(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints, int budget) {
		ArrayList<Point> hitPointsCopie = new ArrayList<Point>(hitPoints);
		Point aSup;
		ArrayList<Arete> aretes = Steiner.calculSteiner(points, edgeThreshold, hitPointsCopie); ////////

		double score = Tools.scoreListeAretes(aretes);
		while(score > budget) {
			aSup = getNextPointASup(aretes, hitPointsCopie);
			hitPointsCopie.remove(aSup);
			aretes = Steiner.calculSteiner(points, edgeThreshold, hitPointsCopie);  
			score = Tools.scoreListeAretes(aretes);
		}

		return Tools.AreteToTree(aretes);
	}


	/////////// Fonctions pour budget //////////////


	private Point getNextPointASup(ArrayList<Arete> aretes, ArrayList<Point> hitPoints) {
		ArrayList<Point> hitPointsExtremite = getListeHitPointExtremite(aretes, hitPoints);
		double scoreMax = -1;
		Point ptMax = null;
		double score;
		for(Point hit : hitPointsExtremite) {
			score = getScoreWithNextHitPoint(hit, aretes, hitPoints);
			if(score > scoreMax) {
				scoreMax = score;
				ptMax = hit;
			}
		}
		return ptMax;
	}

	private double getScoreWithNextHitPoint(Point point, ArrayList<Arete> aretes, ArrayList<Point> hitPoints) {
		Arete arr = getArete(point, aretes);
		Point first = point;
		Point second = (arr.getPointA().equals(first)) ? arr.getPointB() : arr.getPointA();
		double score = arr.getPoids();

		while(!hitPoints.contains(second)) {
			arr = getSecondeAreteContainsMe(arr, second, aretes);  
			score += arr.getPoids();
			first = second;
			second = (arr.getPointA().equals(first)) ? arr.getPointB() : arr.getPointA();
		}
		return score;
	}

	private ArrayList<Point> getListeHitPointExtremite(ArrayList<Arete> aretes, ArrayList<Point> hitPoints) {
		ArrayList<Point> ptSeuls =  getAllPointsSeul(aretes);
		ArrayList<Point> hitPointSeul = new ArrayList<Point>();
		for(Point tmp : ptSeuls) {
			if(hitPoints.contains(tmp))
				hitPointSeul.add(tmp);
		}
		return hitPointSeul;
	}

	private Arete getSecondeAreteContainsMe(Arete notMe, Point second, ArrayList<Arete> aretes) {
		for(Arete tmp : aretes) {
			if(tmp.equals(notMe))
				continue;
			if(tmp.getPointA().equals(second) || tmp.getPointB().equals(second))
				return tmp;
		}
		return null;
	}

	private Arete getArete(Point point, ArrayList<Arete> aretes) {
		for(Arete tmp : aretes) {
			if(tmp.getPointA().equals(point) || tmp.getPointB().equals(point))
				return tmp;
		}
		return null;
	}

	// Point connecte a exactement une arete
	private ArrayList<Point> getAllPointsSeul(ArrayList<Arete> aretes) {
		HashMap<Point, Integer> map = new HashMap<Point, Integer>();
		for(Arete tmp : aretes) {
			Point a = tmp.getPointA();
			Point b = tmp.getPointB();
			Integer resA = (map.get(a) == null) ? 1 : map.get(a)+1;
			Integer resB = (map.get(b) == null) ? 1 : map.get(b)+1;
			map.put(a, resA);
			map.put(b, resB);
		}

		ArrayList<Point> res = new ArrayList<Point>();
		for(Point tmp : map.keySet()) {
			if(map.get(tmp) == 1)
				res.add(tmp);
		}
		return res;
	}













	///////////////////////////////////// Experimentation //////////////////////////////////


	//	private ArrayList<Arete> getPoids(ArrayList<Point> aretes, int dist) {
	//		ArrayList<Arete> res = new ArrayList<Arete>();
	//		for(int i=0 ; i<aretes.size() ; i++) {
	//			for(int j=i+1 ; j<aretes.size() ; j++) {
	//				if(aretes.get(i).distance(aretes.get(j))<=dist)
	//					res.add(new Arete(aretes.get(i), aretes.get(j)));
	//			}
	//		}
	//		return res;
	//	}

	//
	//	// La V2 marche pas 
	//	private Tree2D calculWithBudget_V2(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints, int budget) {
	//		Tree2D tree = calculSteiner(points, edgeThreshold, hitPoints); 
	//		ArrayList<Arete> aretes = Tools.Tree2Aretes(tree);
	//		ArrayList<Point> hitPointsCopie = new ArrayList<Point>(hitPoints);
	//
	//		double score = Tools.scoreListeAretes(aretes);
	//		while(score > budget) {
	//			Point aSup = getPointASup(aretes);
	//			if(!hitPointsCopie.contains(aSup))
	//				System.out.println("Contient pas !!!");
	//			hitPointsCopie.remove(aSup);
	//			aretes = Steiner.calculSteiner(points, edgeThreshold, hitPointsCopie);  
	//			score = Tools.scoreListeAretes(aretes);
	//			System.out.println("Score = "+score+", sup : "+aSup);
	//		}
	//
	//		tree = Tools.AreteToTree(aretes);
	//		return tree;
	//	}
	//
	//
	//	private Point getPointASup(ArrayList<Arete> aretes) {
	//		ArrayList<Arete> aretesSeul = getAretesSeul(aretes);
	//		Arete aSup = getWithMaxDist(aretesSeul);
	//		Point ptSeulASup = getPointSeul(aSup, aretes);
	//		if(ptSeulASup == null)
	//			System.out.println("??????????????????????????????");
	//		return ptSeulASup;
	//	}
	//
	//
	//	private Point getPointSeul(Arete aSup, ArrayList<Arete> aretes) {
	//		int cptA = 0;
	//		int cptB = 0;
	//		Point A = aSup.getPointA();
	//		Point B = aSup.getPointB();
	//		for(Arete tmp : aretes) {
	//			if(tmp.getPointA().equals(A))
	//				cptA++;
	//			else if(tmp.getPointA().equals(B))
	//				cptB++;
	//			if(tmp.getPointB().equals(A))
	//				cptA++;
	//			else if(tmp.getPointB().equals(B))
	//				cptB++;
	//			if(cptA > 1)
	//				return B;
	//			else if(cptB > 1)
	//				return A;
	//		}
	//		return null;
	//	}
	//
	//
	//
	//
	//
	//	private Tree2D calculWithBudget_V1(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints, int budget) {
	//		ArrayList<Arete> aretesSeul = null;
	//		Tree2D tree = calculSteiner(points, edgeThreshold, hitPoints); 
	//		ArrayList<Arete> aretes = Tools.Tree2Aretes(tree);
	//		double score = Tools.scoreListeAretes(aretes);
	//
	//		while(score > budget) {
	//			aretesSeul = getAretesSeul(aretes);
	//			Arete aSup = getWithMaxDist(aretesSeul);
	//			aretes.remove(aSup);
	//			score = Tools.scoreListeAretes(aretes);
	//			System.out.println("Score = "+score);
	//		}
	//
	//		tree = Tools.AreteToTree(aretes);
	//		return tree;
	//	}
	//
	//
	//
	//	private Arete getWithMaxDist(ArrayList<Arete> aretes) {
	//		double max = aretes.get(0).getPoids();
	//		Arete res = aretes.get(0);
	//		for(Arete tmp : aretes) {
	//			if(tmp.getPoids() > max) {
	//				max = tmp.getPoids();
	//				res = tmp;
	//			}
	//		}
	//		return res;
	//	}
	//
	//	// arete connecte d'un cote
	//	private ArrayList<Arete> getAretesSeul(ArrayList<Arete> aretes) {
	//		ArrayList<Point> ptsSeul = getAllPointsSeul(aretes);
	//		ArrayList<Arete> res = new ArrayList<Arete>();
	//		for(Arete tmp : aretes) {
	//			if(ptsSeul.contains(tmp.getPointA()) || ptsSeul.contains(tmp.getPointB())) 
	//				res.add(tmp);
	//		}
	//
	//		//System.out.println(res.size());
	//		return res;
	//	}


}
