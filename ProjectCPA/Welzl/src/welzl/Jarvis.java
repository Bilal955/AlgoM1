package welzl;

import java.awt.Point;
import java.util.ArrayList;

public class Jarvis {
	
	
	//Return 1 si du bon cote, -1 du mauvais et 0 sur la ligne
		public int pdtVecto(Point c, Point p, Point q){  
			Point pq = new Point(q.x-p.x, q.y-p.y);   //u
			Point pc = new Point(c.x-p.x, c.y-p.y);   //v
			int res = pq.x*pc.y - pq.y*pc.x;
			if(res > 0)
				return 1;
			if(res < 0)
				return -1;
			return 0;

		}
	
	
	public ArrayList<Point> enveloppeConvexeJarvis(ArrayList<Point> points){
		if (points.size()<3) {
			return null;
		}

		ArrayList<Point> enveloppe = new ArrayList<Point>();

		boolean estCote = true, gauche = false, droite = false;
		Point p = points.get(0);
		Point q = points.get(0);
		double max = points.get(0).x;


		/*******************
		 * PARTIE A ECRIRE *
		 *******************/

		for(Point a : points){
			if(a.x < max){
				max = a.x;
				p = a;
			}
		}

		for(Point b : points){
			if(p.equals(b))
				continue;
			estCote = true;
			for(Point c : points){
				if(c.equals(p) || c.equals(b))
					continue;
				if(pdtVecto(c, p, b) > 0)
					droite = true;
				else if(pdtVecto(c, p, b) < 0)
					gauche = true;
				if(gauche && droite){
					estCote = false;
					break;
				}
			}
			if(estCote){
				q = b;
				break;
			}
			droite = false;
			gauche = false;
		}

		enveloppe.add(p);
		enveloppe.add(q);
		Point r = q;

		Point pq = new Point(q.x-p.x, q.y-p.y);   //PQ
		Point f = points.get(0);
		for(Point a : points){
			if(!a.equals(p) && !a.equals(q)) {
				f = a;
				break;
			}
		}
		Point qf = new Point(f.x-q.x, f.y-q.y);   //QF
		//max = (pq.x * qf.x + pq.y * qf.y) / (Math.sqrt(Math.pow(p.x-q.x, 2) + Math.pow(p.y-q.y, 2)) * Math.sqrt(Math.pow(f.x-q.x, 2) + Math.pow(f.y-q.y, 2)));
		//System.out.println("MAx: " + max);
		max = Double.NEGATIVE_INFINITY;
		Point p0 = p;


		while(! r.equals(p0)){
			for(Point c : points){
				if(p.equals(c) || q.equals(c))
					continue;
				Point qc = new Point(c.x-q.x, c.y-q.y);   //QR
				double pdtScal = (pq.x * qc.x + pq.y * qc.y) / (Math.sqrt(Math.pow(p.x-q.x, 2) + Math.pow(p.y-q.y, 2)) * Math.sqrt(Math.pow(c.x-q.x, 2) + Math.pow(c.y-q.y, 2)));
				if(pdtScal > max){
					max = pdtScal;
					r = c;
				}
			}
			max = Double.NEGATIVE_INFINITY;
			enveloppe.add(r);
			p = q;
			q = r;
			pq = new Point(q.x-p.x, q.y-p.y);
		}

		return enveloppe;

	}

}
