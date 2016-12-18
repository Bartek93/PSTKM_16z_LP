package pl.edu;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.asu.emit.algorithm.graph.Path;
import pl.edu.pojo.CplexInput;
import pl.edu.pojo.Demand;
import pl.edu.pojo.Edge;

public class LinkPathMain {

	public static void main(String[] args) {
		
		Model model = new Model();
		int numberOfPaths = 5;
		
		System.out.println("---------- 1st example ----------");
                
                CplexInput input = null;
                String scenario = printMenu();
                String dumpFileName = "";
                if (scenario.equals("1"))
                {
                    input = Parser.parse("resources/trivial.txt", "resources/demands_trivial.txt", numberOfPaths);
                    dumpFileName = "trivial_dump.txt";
                }
		else if (scenario.equals("2"))
                {
                    input  = Parser.parse("resources/non-trivial_1.txt", "resources/demands_nontrivial1.txt", numberOfPaths);
                    dumpFileName = "nontrivial1_dump.txt";
                }
		else
                {
                    input  = Parser.parse("resources/non-trivial_2.txt", "resources/demands_nontrivial2.txt", numberOfPaths); 
                    dumpFileName = "nontrivial2_dump.txt";
                }
                    
		System.out.print("--- Edges ---");
                for (Edge e : input.getEdges())
                    System.out.print(e);
                
                System.out.println("\n\n--- Demands ---");
		System.out.print(input.getDemandPathsMap());
		
		
		Parser.dumpToFile(input.getDemandPathsMap(), dumpFileName);
		
		model.createModel(input, numberOfPaths);
		
	}
        public static String printMenu()
        {
            String choice = "a";
            do
            {
                System.out.println("*****************************************************************************");
                System.out.println("Wybierz scenariusz ktory chcesz uruchomić poprzez podanie wybranej liczby:");
                System.out.println("1. Scenariusz trywialny");
                System.out.println("2. Scenariusz nietrywialny 1");
                System.out.println("3. Scenariusz nietrywialny 2");
                System.out.println("Your choice: ");
                Scanner scan = new Scanner(System.in);
                choice = scan.nextLine();
            }
            while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3"));

            return choice;
        }
}
