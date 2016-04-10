package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import geometrie.Arete;
import geometrie.Tools;

public class DefaultTeam {
	public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
		double oldScore, newScore;
		Tree2D result, res = null;
		
		result = Steiner.calculSteiner(points, edgeThreshold, hitPoints);
		oldScore = Tools.scoreTree2D(result);
//		System.out.println("NbArete = "+Tools.Tree2Aretes(result).size());
//		for(Arete tmp : Tools.Tree2Aretes(result))
//			System.out.println(tmp);
//		if(true)
//			return result;
		
		result = RegleDuBarycentre.regleDuBarycentre3(result, points);
		if(result == null)
			System.out.println("WHY result == null");
		System.out.println("Score Result = "+Tools.scoreTree2D(result));
		newScore = Tools.scoreTree2D(result);
	
		while(newScore < oldScore) {
			oldScore = newScore;
			res = result; // Pas utile devient pas pire normalement mais egal
			result = RegleDuBarycentre.regleDuBarycentre3(result, points);
	
			newScore = Tools.scoreTree2D(result);
		}
		
		if(res == null)
			System.out.println("RES == NULL");
		System.out.println("Score Res = "+Tools.scoreTree2D(res)+", Score result = "+Tools.scoreTree2D(result));

		return res;
	}

	
	
	
	
	public Tree2D calculSteinerBudget(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
		//REMOVE >>>>>
		Tree2D leafX = new Tree2D(new Point(700,400),new ArrayList<Tree2D>());
		Tree2D leafY = new Tree2D(new Point(700,500),new ArrayList<Tree2D>());
		Tree2D leafZ = new Tree2D(new Point(800,450),new ArrayList<Tree2D>());
		ArrayList<Tree2D> subTrees = new ArrayList<Tree2D>();
		subTrees.add(leafX);
		subTrees.add(leafY);
		subTrees.add(leafZ);
		Tree2D steinerTree = new Tree2D(new Point(750,450),subTrees);
		//<<<<< REMOVE

		return steinerTree;
	}
}
