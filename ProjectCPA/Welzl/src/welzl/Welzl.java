package welzl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import supportGUI.Circle;

public class Welzl {
	
	
	/************Methode avec Welzl***************/
	public static Circle calculCercleMinWelzl(ArrayList<Point> points){

		if(points.isEmpty())
			return null;

		@SuppressWarnings("unchecked")
		ArrayList<Point> P = (ArrayList<Point>) points.clone();
		ArrayList<Point> S = new ArrayList<Point>();

		Random rand = new Random();
		Point r = P.get(rand.nextInt(P.size()));
		P.remove(r);
		S.add(r);      //Au debut le cercle minimum est reduit a un point choisi au hasard
		//System.out.println("Debut PROG");
		//java.util.Collections.shuffle(P);
		Circle res = welzl(P, S).toCircle();
		return res;

	}

	static Random rand = new Random();

	public static MyCircle welzl(ArrayList<Point> P, ArrayList<Point> S) {


		if(P.isEmpty() || S.size() >= 3){
			if(S.size() == 4)
				System.out.println("AVEC 4 !!!!!!!");
			return makeSphere(S);
		}

		Point r = P.get(rand.nextInt(P.size()));
		//Point r = P.get(0);    //Revient au meme que random car on fait shuffle de la liste

		P.remove(r);
		MyCircle W = welzl(P, S);
		if(Tools.estDansCercle(r, W)){
			return W;
		}

		S.add(r);
		return welzl(P, S);

	}



	public static MyCircle makeSphere(ArrayList<Point> S) {

		if(S.size() == 1)
			return new MyCircle(new MyPoint(S.get(0).getX(), S.get(0).getY()), 0);


		Point s1 = S.get(0);
		Point s2 = S.get(1);
		if(S.size() == 2){
			return new MyCircle(new MyPoint((s1.getX() + s2.getX()) / 2, (s1.getY() + s2.getY()) / 2), (Math.sqrt(Math.pow(s1.getX() - s2.getX(), 2) + Math.pow(s1.getY() - s2.getY(), 2)))/2);
		}


		Point s3 = S.get(2);  //Le nouveau point a ajouter
		if(S.size() == 3){
			boolean b1, b2;

			MyCircle c_final = null;    //cercle final a renvoyer
			MyCircle c0 = Tools.centreCercleCircon(s1, s2, s3);    //Test le min est le cercle forme de (s1, s2, s3)

			@SuppressWarnings("unchecked")
			ArrayList<Point> Sclone = (ArrayList<Point>) S.clone();
			Sclone.remove(s2);
			MyCircle c1 = makeSphere(Sclone);   //Test le min est le cercle forme de (s1, s3)
			ArrayList<Point> test1 = new ArrayList<Point>();
			test1.add(s2);
			if(containsPoints(c1, test1))
				b1 = true;
			else
				b1 = false;

			@SuppressWarnings("unchecked")
			ArrayList<Point> Sclone2 = (ArrayList<Point>) S.clone();
			Sclone2.remove(s1);
			MyCircle c2 = makeSphere(Sclone2);    //Test le min est le cercle forme de (s2, s3)
			ArrayList<Point> test2 = new ArrayList<Point>();
			test2.add(s1);
			if(containsPoints(c2, test2))
				b2 = true;
			else
				b2 = false;

			double min_c1, min_c2;

			if(b1)
				min_c1 = c1.getRadius();
			else
				min_c1 = Double.POSITIVE_INFINITY;

			if(b2)
				min_c2 = c2.getRadius();
			else
				min_c2 = Double.POSITIVE_INFINITY;

			if(c0.getRadius() <= min_c1){
				c_final = c0;
			}
			else{
				c_final = c1;
				S.remove(s2);
			}
			if(min_c2 < c_final.getRadius()){
				c_final = c2;
				S.remove(s1);
			}

			return c_final;
		}


		else{
			//System.out.println("4 Points");
			Point s4 = S.get(3);    //Nouveau point a integrer a la sphere
			MyCircle c_final = null;    //cercle final a renvoyer
			boolean b1, b2, b3, b4, b5, b6;

			@SuppressWarnings("unchecked")  //Test le min est le cercle forme de (s1, s4)
			ArrayList<Point> Sclone1 = (ArrayList<Point>) S.clone();
			Sclone1.remove(s2);
			Sclone1.remove(s3);
			MyCircle c1 = makeSphere(Sclone1);
			ArrayList<Point> test1 = new ArrayList<Point>();
			test1.add(s2);
			test1.add(s3);
			if(containsPoints(c1, test1))
				b1 = true;
			else
				b1 = false;

			@SuppressWarnings("unchecked")   //Test le min est le cercle forme de (s2, s4)
			ArrayList<Point> Sclone2 = (ArrayList<Point>) S.clone();
			Sclone2.remove(s1);
			Sclone2.remove(s3);
			MyCircle c2 = makeSphere(Sclone2);
			ArrayList<Point> test2 = new ArrayList<Point>();
			test2.add(s1);
			test2.add(s3);
			if(containsPoints(c2, test2))
				b2 = true;
			else
				b2 = false;

			@SuppressWarnings("unchecked")    //Test le min est le cercle forme de (s3, s4)
			ArrayList<Point> Sclone3 = (ArrayList<Point>) S.clone();
			Sclone3.remove(s1);
			Sclone3.remove(s2);
			MyCircle c3 = makeSphere(Sclone3);
			ArrayList<Point> test3 = new ArrayList<Point>();
			test3.add(s1);
			test3.add(s2);
			if(containsPoints(c3, test3))
				b3 = true;
			else
				b3 = false;

			MyCircle c4 = Tools.centreCercleCircon(s1, s2, s4);   //Test le min est le cercle forme de (s1, s2, s4)
			ArrayList<Point> test4 = new ArrayList<Point>();
			test4.add(s3);
			if(containsPoints(c4, test4))
				b4 = true;
			else
				b4 = false;

			MyCircle c5 = Tools.centreCercleCircon(s1, s3, s4);     //Test le min est le cercle forme de (s1, s3, s4)
			ArrayList<Point> test5 = new ArrayList<Point>();
			test5.add(s2);
			if(containsPoints(c5, test5))
				b5 = true;
			else
				b5 = false;

			MyCircle c6 = Tools.centreCercleCircon(s2, s3, s4);     //Test le min est le cercle forme de (s2, s3, s4)
			ArrayList<Point> test6 = new ArrayList<Point>();
			test6.add(s1);
			if(containsPoints(c6, test6))
				b6 = true;
			else
				b6 = false;

			double min_c1, min_c2, min_c3, min_c4, min_c5, min_c6, min_final;
			if(!b1)
				min_c1 = Double.POSITIVE_INFINITY;
			else
				min_c1 = c1.getRadius();
			if(!b2)
				min_c2 = Double.POSITIVE_INFINITY;
			else
				min_c2 = c2.getRadius();
			if(!b3)
				min_c3 = Double.POSITIVE_INFINITY;
			else
				min_c3 = c3.getRadius();
			if(!b4)
				min_c4 = Double.POSITIVE_INFINITY;
			else
				min_c4 = c4.getRadius();
			if(!b5)
				min_c5 = Double.POSITIVE_INFINITY;
			else
				min_c5 = c5.getRadius();
			if(!b6)
				min_c6 = Double.POSITIVE_INFINITY;
			else
				min_c6 = c6.getRadius();

			if(min_c1 <= min_c2){
				min_final = min_c1;
				c_final = c1;
			}
			else{
				min_final = min_c2;
				c_final = c2;
			}

			if(min_final > min_c3){
				min_final = min_c3;
				c_final = c3;
			}
			if(min_final > min_c4){
				min_final = min_c4;
				c_final = c4;
			}
			if(min_final > min_c5){
				min_final = min_c5;
				c_final = c5;
			}
			if(min_final > min_c6){
				min_final = min_c6;
				c_final = c6;
			}


			if(min_final == min_c1){
				S.remove(s2);
				S.remove(s3);
			}
			if(min_final == min_c2){
				S.remove(s1);
				S.remove(s3);
			}
			if(min_final == min_c3){
				S.remove(s1);
				S.remove(s2);
			}
			if(min_final == min_c4){
				S.remove(s3);
			}
			if(min_final == min_c5){
				S.remove(s2);
			}
			if(min_final == min_c6){
				S.remove(s1);
			}

			return c_final;
		}

	}


	public static Boolean containsPoints(MyCircle c, ArrayList<Point> points){
		for(Point p : points)
			if( ((c.getCenter().getX()-p.getX()) * (c.getCenter().getX()-p.getX())) + ((c.getCenter().getY()-p.getY()) * (c.getCenter().getY()-p.getY())) > c.getRadius() * c.getRadius() )
				return false;
		return true;
	}


	
	

}
