package algorithms;

import java.awt.Point;

public class Edge implements Comparable<Edge> {
	
	private int etiquette;
	private double distance;
	Point p, q;

	public Edge(Point p, Point q) {
		this.p = p;
		this.q = q;
		this.distance = p.distanceSq(q);
	}
	
	public Edge(Point p, Point q, int etiquette) {
		this.p = p;
		this.q = q;
		this.etiquette = etiquette;
		this.distance = p.distanceSq(q);
	}
	
	public Edge(Point p, Point q, double d, int etiquette) {
		this.p = p;
		this.q = q;
		this.distance = d;
		this.etiquette = etiquette;
	}

	@Override
	public int compareTo(Edge e) {
		// TODO Auto-generated method stub
		if(this.distance < e.getDistance())
			return -1;
		else if(this.distance > e.getDistance())
			return 1;
		else
			return 0;
	}
	
	public double getDistance(){ return this.distance; }
	public int getEtiquette(){ return this.etiquette; }
	
	public void setEtiquette(int i){ this.etiquette = i; }
	public void setDistance(double d){ this.distance = d; }
	
	public String toString(){
		return "P: " + getP() + " Q: " + getQ() + " distance: " + distance + " etiquette : "+ etiquette;
	}
	
	public Point getP(){ return this.p; }
	public Point getQ(){ return this.q; }

}
