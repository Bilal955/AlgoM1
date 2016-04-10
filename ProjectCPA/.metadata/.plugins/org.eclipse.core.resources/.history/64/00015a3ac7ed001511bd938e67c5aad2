package utils;

import java.awt.Point;

import algorithms.Tree2D;

public class Utils {

	static double SQRT32=0.866025403784439;
	
	public static double score(Tree2D tree){
		double d = tree.distanceRootToSubTrees();
		for (int i = 0; i < tree.getSubTrees().size(); i++) {
			d += score(tree.getSubTrees().get(i));
		}
		return d;
	}

	public static Point barycentre(Point A, Point B, Point C){
		return new Point(A.x+B.x+C.x/3,A.y+B.y+C.y/3);
	}

	public static double prodScalaire(Point A, Point B, Point C){
		return (B.getX()-A.getX())*(C.getX()-A.getX())+(B.getY()-A.getY())*(C.getY()-A.getY());
	}

	public static double angle(Point A, Point B, Point C){
		return prodScalaire(A, B, C)/(B.distance(A)*C.distance(A));
	}

	
	
	public static Point torricelliFermat(Point A, Point B, Point C){
		
		int i;
		double cos_A, sin_A, sin_O, dist_AC, l, ext;
		
		cos_A = angle(B, A, C);
		cos_A = -cos_A;
		sin_A = Math.sqrt(1-cos_A*cos_A);
		sin_O = SQRT32*cos_A + 0.5*sin_A;
		dist_AC = C.distance(A);
		ext = (dist_AC / sin_O) * SQRT32;
		
		l=(1.0/B.distance(A));
		
		return null;
		
	}
}
