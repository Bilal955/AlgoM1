package geometrie;

import java.awt.Point;

import algorithms.RegleDuBarycentre;

public class Test {

	public static void main(String[] args) {
		Point a = new Point(0, 0);
		Point b = new Point(0, 200);
		Point c = new Point(200, 0);
		
//		Triangle t = new Triangle(a, b, c);
//		Point fermat = t.pointDeFermat();
//		System.out.println(fermat);
		System.out.println("Bary = "+RegleDuBarycentre.getPointBarycentre(a, b, c));
		System.out.println("Fermat = "+Tools.FermatToricelli(a, b, c));
	}
	
	

}
