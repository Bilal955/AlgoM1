package welzl;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import supportGUI.Circle;

public class TestNaif {

	public static void main(String[] args) throws IOException, InterruptedException {

		FileWriter f_res_time = new FileWriter("./res_time_naif");
		FileWriter f_res_rayon = new FileWriter("./res_rayon_naif");
		File dir = new File("./samples");
		File[] directoryListing = dir.listFiles();
		ArrayList<Point> points = new ArrayList<Point>();
		long startTime2 = System.currentTimeMillis();
		long startTime, end;
		
		for (File child : directoryListing) {
			

			FileReader f = new FileReader(child.getAbsolutePath());
			BufferedReader br = new BufferedReader(f);
			String s = "";

			while((s = br.readLine()) != null){
				points.add(new Point(Integer.valueOf(s.split(" ")[0]), Integer.valueOf(s.split(" ")[1])));
			}

			br.close();

			startTime = System.nanoTime();
			Circle cm = Naif.calculCercleMinNaif(points);
			end = (System.nanoTime() - startTime);
			//System.out.println("TEMPS : " + end + " et rayon vaut : " + cm.getRadius());
			f_res_time.write(end + "\n");
			f_res_rayon.write(cm.getRadius() + "\n");
			points.removeAll(points);
			
		}
		end = (System.currentTimeMillis() - startTime2);
		System.out.println("TEMPS : " + end);
		f_res_time.close();
		f_res_rayon.close();
	}
	
}
