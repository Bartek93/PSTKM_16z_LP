package pl.edu;

import java.util.List;
import java.util.Map;

import edu.asu.emit.algorithm.graph.Path;
import pl.edu.pojo.Demand;

public class LinkPathMain {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		System.out.println("---------- 1st example ----------");
		Map<Demand, List<Path>> demandPathsMap = Parser.parse("resources/trivial.txt", "resources/demands_trivial.txt", 2);
		
		Parser.dumpToFile(demandPathsMap, "trivial_dump.txt");
		
//		System.out.println("---------- 2nd example ----------");
//		Parser.parse("resources/non-trivial_1.txt", 1, 3, 4);
//		Parser.parse("resources/non-trivial_1.txt", 1, 4, 4);
//
//		System.out.println("---------- 3rd example ----------");
//		Parser.parse("resources/non-trivial_2.txt", 6, 0, 4);
//		Parser.parse("resources/non-trivial_2.txt", 10, 4, 9); // it doesn't get
//																// the shortest,
//																// it gets last
//																// in list
//		Parser.parse("resources/non-trivial_2.txt", 2, 9, 4);
		
		
		
	}
}
