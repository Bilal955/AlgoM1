package geometrie;

public class MyDroite {
	
	private double coeffDirecteur;
	private double constante;
	
	public MyDroite(double coeffDirecteur, double constante) {
		super();
		this.coeffDirecteur = coeffDirecteur;
		this.constante = constante;
	}

	public double getCoeffDirecteur() {
		return coeffDirecteur;
	}

	public double getConstante() {
		return constante;
	}

}
