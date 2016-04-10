package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import geometrie.Arete;

public class Tmp {


	public Tree2D calculSteinerBarycentre(ArrayList<Point> points) {
		ArrayList<Arete> listTri = allArete(points);
		ArrayList<Arete> res = new ArrayList<Arete>();
		int i = 0;
		while (res.size() < (points.size() - 1)) {
			if (!detectCycle(res, listTri.get(i))) {
				res.add(listTri.get(i));
			}

			i++;
		}
		Point barycentre = null;
		Point a = null;
		Point b = null;
		Point c = null;
		Arete a1, a2;
		double oldscore = getScore(res);
		double newscore = getScore(res) - 1;
		System.out.println(res.size() + " et " + getScore(res));
		int n = res.size();
		for (i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				a1 = res.get(i);
				a2 = res.get(j);
				if (a1.equals(a2))
					continue;
				if (a1.getPointA().equals(a2.getPointA())) {
					barycentre = getPointBarycentre(a1.getPointB(), a1.getPointA(), a2.getPointB());
					a = a1.getPointB();
					b = a1.getPointA();
					c = a2.getPointB();
				} else if (a1.getPointA().equals(a2.getPointB())) {
					barycentre = getPointBarycentre(a1.getPointB(), a1.getPointA(), a2.getPointA());
					a = a1.getPointB();
					b = a1.getPointA();
					c = a2.getPointA();

				} else if (a1.getPointB().equals(a2.getPointA())) {
					barycentre = getPointBarycentre(a1.getPointA(), a1.getPointB(), a2.getPointB());
					a = a1.getPointA();
					b = a1.getPointB();
					c = a2.getPointB();

				} else if (a1.getPointB().equals(a2.getPointB())) {
					barycentre = getPointBarycentre(a1.getPointA(), a1.getPointB(), a2.getPointA());
					a = a1.getPointA();
					b = a1.getPointB();
					c = a2.getPointA();

				}
				if (barycentre == null)
					continue;
				double tmp = newscore;
				double poidsarr = distanceSSQRT(a, b) + distanceSSQRT(b, c);
				double poidsbar = distanceSSQRT(a, barycentre)
						+ distanceSSQRT(b, barycentre)
						+ distanceSSQRT(c, barycentre);
				newscore = oldscore - poidsarr + poidsbar;
				oldscore = tmp;
				if (newscore < oldscore) {
					res.remove(a1);
					res.remove(a2);
					res.add(new Arete(a, barycentre));
					res.add(new Arete(barycentre, b));
					res.add(new Arete(barycentre, c));
				}
				barycentre = null;

			}
		}
		return toTree2D(res.get(0).getPointA(), res);
	}

	public double getScore(ArrayList<Arete> arr) {
		double res = 0;
		for (Arete a : arr)
			res += a.getPoids();
		return res;
	}

	public Point getPointBarycentre(Point a, Point b, Point c) {
		int xc = (a.x + b.x + c.x) / 3;
		int yc = (a.y + b.y + c.y) / 3;
		return new Point(xc, yc);
	}

	public boolean isBetter(Point a, Point b, Point c, Point bar) {
		double sum = distanceSSQRT(a, b) + distanceSSQRT(b, c);
		double barsum = distanceSSQRT(a, bar) + distanceSSQRT(bar, b)
		+ distanceSSQRT(bar, c);
		return barsum < sum;
	}

	public static double distanceSSQRT(Point a, Point b) {
		return (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y);
	}

	public Tree2D calculKruskal(ArrayList<Point> points) {
		ArrayList<Arete> listTri = allArete(points);
		ArrayList<Arete> res = new ArrayList<Arete>();

		int i = 0;
		while (res.size() < (points.size() - 1)) {

			if (!detectCycle(res, listTri.get(i))) {
				res.add(listTri.get(i));
			}

			i++;
		}

		// Le cide ou tu fais le calcul du barycentre
		Point alea = res.get((int) Math.random() * res.size()).getPointA();
		return toTree2D(alea, res);
	}

	public Tree2D toTree2D(Point res, ArrayList<Arete> list) {
		if (list.isEmpty())
			return new Tree2D(res, new ArrayList<Tree2D>());
		ArrayList<Tree2D> fils = new ArrayList<Tree2D>();
		ArrayList<Arete> AreteFromRes = new ArrayList<Arete>();
		for (Arete e : list) {
			if (e.getPointA() == res || e.getPointB() == res) {
				AreteFromRes.add(e);
			}
		}
		list.removeAll(AreteFromRes);
		for (Arete e : AreteFromRes) {
			if (e.getPointA() == res) {
				fils.add(toTree2D(e.getPointB(), list));
			}
			if (e.getPointB() == res) {
				fils.add(toTree2D(e.getPointA(), list));

			}
		}
		return new Tree2D(res, fils);
	}

	public ArrayList<Arete> allArete(ArrayList<Point> points) {
		ArrayList<Arete> Aretes = new ArrayList<Arete>();

		for (int i = 0; i < points.size(); i++)
			for (int j = i + 1; j < points.size(); j++)
				Aretes.add(new Arete(points.get(i), points.get(j)));

		Aretes.sort(new ComparatorArete());

		return Aretes;
	}

	public boolean detectCycle(ArrayList<Arete> list, Arete toAdd) {
		int i = 1;
		ArrayList<Arete> listCpy = new ArrayList<Arete>();
		listCpy.addAll(list);
		listCpy.add(toAdd);
		HashMap<Point, Integer> etiquette = new HashMap<Point, Integer>();
		for (Arete Arete : listCpy) { // Donner une etiquette a tout les
			// points
			etiquette.put(Arete.getPointA(), i++);
			etiquette.put(Arete.getPointB(), i++);
		}
		for (Arete Arete : listCpy) {
			int etiqA = etiquette.get(Arete.getPointA());
			int etiqB = etiquette.get(Arete.getPointB());
			if (etiqA == etiqB) {
				return true;
			}
			for (Point x : etiquette.keySet()) {
				if (etiquette.get(x) == etiqA) {
					etiquette.put(x, etiqB);
				}
			}
		}
		return false;
	}

	class ComparatorArete implements Comparator<Arete> {

		@Override
		public int compare(Arete o1, Arete o2) {
			if (o1.getPoids() < o2.getPoids())
				return -1;
			else if (o1.getPoids() > o2.getPoids())
				return 1;
			else
				return 0;
		}

	}
}

