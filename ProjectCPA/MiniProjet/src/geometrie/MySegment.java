package geometrie;

public class MySegment {

	private MyPoint a;
	private MyPoint b;

	public MySegment(MyPoint a, MyPoint b) {
		super();
		this.a = a;
		this.b = b;
	}

	public MyPoint getA() {
		return a;
	}

	public MyPoint getB() {
		return b;
	}

	public MyPoint getMilieu() {
		return new MyPoint(
				((a.getX() + b.getX()) / 2),
				((a.getY() + b.getY()) / 2));
	}


	// Coefficient directeur
	public double getCoeffDirecteur() {
		double tmp = b.getX() - a.getX();
		if(tmp < 0.1) // Verifier !!!!!!!!!!!!!!!!!!!!!!!!!!
			return b.getY() - a.getY();
		return ((b.getY() - a.getY()) / tmp);
	}

	public double getCoeffDirecteurOppose() {
		double tmp = b.getY() - a.getY();
		if(tmp < 0.1) // Verifier !!!!!!!!!!!!!!!!!!!!!!!!!!
			return b.getX() - a.getX();
		return -1 * ((b.getX() - a.getX()) / tmp);
	}



	// Peut etre verifier si division par 0 !!!!!!!!!!!!!!!
	public MyDroite getEquationMediatrice() {
		double xa = a.getX(), xb = b.getX();
		double ya = a.getY(), yb = b.getY();
		// ax + by + c = 0;
		double aa = 2*(xb-xa);
		double bb = 2*(yb-ya);
		double cc = (xa+xb)*(xa-xb)+(ya+yb)*(ya-yb);

//		if(bb < 0.001)
//			return new MyDroite(0, 0);
//		System.out.println("\nax = "+xa+", ay = "+ya);
//		System.out.println("bx = "+xb+", by = "+yb);
//		System.out.println("aa = "+aa+", bb = "+bb+", cc = "+cc);
		return new MyDroite(-aa/bb, -cc/bb);
	}


	// Good, juste gerer le cas ou y a une division par 0
	// y = ax + b, res[0] -> a, res[1] ->b 
	public double[] getEquationMediatrice2() {
		MyPoint milieuSegment = this.getMilieu();
		double xMilieu = milieuSegment.getX();
		double yMilieu = milieuSegment.getY();

		double coefDirecteurMediatrice = this.getCoeffDirecteurOppose();
		double cste = yMilieu - (coefDirecteurMediatrice * xMilieu);
		double [] res = {coefDirecteurMediatrice, cste};

		//////////
//		System.out.println("[DEBUT] Calcul mediatrice");
//		System.out.println("\t[Segment] = "+this.toString());
//		System.out.println("\t[Milieu] = "+milieuSegment);
//		System.out.println("\t[CoefDirecteurMediatrice] = "+coefDirecteurMediatrice);
//		System.out.println("\t[Constante] = "+cste);
//		System.out.println("\t[Mediatrice] => y = "+coefDirecteurMediatrice+"x + "+cste);
//		System.out.println("[DEBUT] Fin mediatrice");

		return res;
	}

	public String toString() {
		return "[pt1 = "+this.a+"], [pt2 = "+this.b+"]";
	}
	//	
	//	public static void main(String args) {
	//		MySegment s = new MySegment(new MyPoint(2,5), new MyPoint(8, 3));
	//		double [] eq = s.getEquationMediatrice();
	//		System.out.println("EQ ==> y = "+eq[0]+" * x + "+eq[1]);
	//	}

}