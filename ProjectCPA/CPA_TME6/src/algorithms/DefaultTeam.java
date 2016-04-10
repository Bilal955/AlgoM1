package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import utils.Utils;

public class DefaultTeam {
  public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
	 Tree2D result;
	 double oldScore, newScore;
	System.out.println("hitPoints size = "+ hitPoints.size());
	Tree2D t = SteinerHeuristic.calcSteinerHeuristic(points, edgeThreshold, hitPoints);
	System.out.println("Score = "+Utils.score(t));
	
	result = RegleDuBarycentre.regleDuBarycentre3(t, points);
	
	oldScore = Utils.score(t);
	newScore = oldScore;
	
	while(newScore < oldScore) {
		oldScore = newScore;
		result = RegleDuBarycentre.regleDuBarycentre3(result, points);
		
		newScore = Utils.score(result);
	}
	
	//Tree2D t = SteinerTree.steinerBarycentre(points, edgeThreshold, hitPoints);
    return result;
  }
  
  public Tree2D calculSteinerBudget(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
   
	  int budget = 1664;
	  Point maison_mere = hitPoints.get(0);
	  ArrayList<Point> points_res = new ArrayList<Point>();
	  points_res.add(maison_mere);
	  
	  int [][]PCC = SteinerHeuristic.calculShortestPaths(points, edgeThreshold);
	  Tree2D retour = null;

	  ArrayList<Edge> res = new ArrayList<>();
	  double score = 0;
	  
	  while(score < budget){
		  retour = Kruskal2.EdgeToTree(res);
		  Edge edge_ajout = null;
		  double min = Double.POSITIVE_INFINITY;
		  for(int i = 0; i < points_res.size(); i++){
			  int cur = hitPoints.indexOf(points_res.get(i));
			  points_res.add(hitPoints.get(cur));
			  for (int j = 0; j < hitPoints.size()-points_res.size(); j++) {
				  if(! points_res.contains(hitPoints.get(i)))
					  continue;
				  if(min > PCC[cur][i]){
					  min = PCC[cur][i];
					  edge_ajout = new Edge(hitPoints.get(cur), hitPoints.get(i));
				  }
			}
		  }
		  score = Utils.score(Kruskal2.EdgeToTree(res));
		  res.add(edge_ajout);
	  }

	  return retour;
  }
  
  
}
