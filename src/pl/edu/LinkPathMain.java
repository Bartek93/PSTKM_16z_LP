package pl.edu;

import java.util.List;
import java.util.Map;

import edu.asu.emit.algorithm.graph.Path;
import pl.edu.pojo.CplexInput;
import pl.edu.pojo.Demand;
import pl.edu.pojo.Edge;

public class LinkPathMain {

	public static void main(String[] args) {
		
		Model model = new Model();
		int numberOfPaths = 5;
		
		System.out.println("---------- 1st example ----------");
//		CplexInput input  = Parser.parse("resources/trivial.txt", "resources/demands_trivial.txt", numberOfPaths);
		
		CplexInput input  = Parser.parse("resources/non-trivial_1.txt", "resources/demands_nontrivial1.txt", numberOfPaths);
		
		
		System.out.print("--- Edges ---");
                for (Edge e : input.getEdges())
                    System.out.print(e);
                
                System.out.println("\n\n--- Demands ---");
		System.out.print(input.getDemandPathsMap());
		
		
//		// 1st
//		Parser.dumpToFile(input.getDemandPathsMap(), "trivial_dump.txt");
		// 2nd 
		Parser.dumpToFile(input.getDemandPathsMap(), "nontrivial1_dump.txt");
		
		model.createModel(input, numberOfPaths);
		
		
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
