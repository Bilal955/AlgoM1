package geometrie;

import java.util.Comparator;

public class myComparatorArete implements Comparator<Arete> {

	@Override
	public int compare(Arete o1, Arete o2) {
		if(o1.getPoids() == o2.getPoids())
			return 0;
		else if(o1.getPoids() > o2.getPoids())
			return 1;
		else 
			return -1;
	}

}
