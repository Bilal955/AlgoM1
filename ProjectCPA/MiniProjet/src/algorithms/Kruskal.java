package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import geometrie.Arete;
import geometrie.myComparatorArete;

public class Kruskal {	

	public static ArrayList<Arete> kruskal(ArrayList<Point> points, ArrayList<Arete> poids) {
		HashMap<Point, Integer> myHash = getHash(points);
		ArrayList<Arete> aretes = poids;
		aretes.sort(new myComparatorArete());

		ArrayList<Arete> solution = new ArrayList<Arete>();
		Set<Point> visite = new HashSet<Point>();

		for(Arete cur : aretes) {
			Point p1 = cur.getPointA();
			Point p2 = cur.getPointB();
			int x = myHash.get(p1);
			int y = myHash.get(p2);

			if(x == y)
				continue;

			visite.add(p1);
			visite.add(p2);
			reetiqueter(x, y, myHash);
			solution.add(cur);
		}

		return solution;	
	}

	private static void reetiqueter(int x, int y, HashMap<Point, Integer> myHash) {
		for(Point p : myHash.keySet()) {
			if(x == myHash.get(p))
				myHash.replace(p, y);
		}
	}

	private static HashMap<Point, Integer> getHash(ArrayList<Point> points) {
		HashMap<Point, Integer> res = new HashMap<Point, Integer>();
		int cpt = 0;
		for(Point tmp : points) 
			res.put(tmp, cpt++);
		return res;
	}







	////////////////////////////NOT SAFE NEW !!!!!!!!!!!
	private static ArrayList<Arete> getAretes(ArrayList<Point> points) {
		ArrayList<Arete> aretes = new ArrayList<Arete>();
		for(int i=0 ; i<points.size() ; i++)
			for(int j=i+1 ; j<points.size() ; j++) {
				if(points.get(i).equals(points.get(j)))
					continue;
				aretes.add(new Arete(points.get(i), points.get(j)));
			}
		return aretes;
	}
	public static ArrayList<Arete> kruskal(ArrayList<Point> points) {
		HashMap<Point, Integer> myHash = getHash(points);
		ArrayList<Arete> aretes = getAretes(points);
		aretes.sort(new myComparatorArete());

		ArrayList<Arete> solution = new ArrayList<Arete>();
		Set<Point> visite = new HashSet<Point>();

		for(Arete cur : aretes) {
			Point p1 = cur.getPointA();
			Point p2 = cur.getPointB();
			int x = myHash.get(p1);
			int y = myHash.get(p2);

			if(x == y)
				continue;

			visite.add(p1);
			visite.add(p2);
			reetiqueter(x, y, myHash);
			solution.add(cur);
		}

		return solution;	
	}
	////////////////////////////////////////////////////////////////////////////////////////

}
