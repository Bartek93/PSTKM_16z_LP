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
		int numberOfPaths = 1;
		                
                CplexInput input = null;
                String dumpFileName = "";
                
                Integer scenario = printMenu();
                numberOfPaths = getNumOfPaths(scenario);
                Config.MODULARITY = setModularity();

                if (scenario == 1)
                {
                    input = Parser.parse("resources/trivial.txt", "resources/demands_trivial.txt", numberOfPaths);
                    dumpFileName = "trivial_dump.txt";
                }
		else if (scenario == 2)
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
        public static Integer printMenu()
        {
            String choice = "";
            do
            {
                System.out.println("*****************************************************************************");
                System.out.println("*** Type number to run specific scenario: ***");
                System.out.println("1. Trivial scenario");
                System.out.println("2. Non-trivial 1 scenario");
                System.out.println("3. Non-trivial 2 scenario");
                System.out.println("Type number of scenario: ");
                Scanner scan = new Scanner(System.in);
                choice = scan.nextLine();
            }
            while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3"));

            return Integer.parseInt(choice);
        }
        
        public static Integer getNumOfPaths(int scenario)
        {
            String choice = "";
            System.out.println("*** Set number of paths for demands ***");
            if (scenario == 1)
                System.out.println("Trivial scenario - max: 2");
            else if (scenario == 2)
                System.out.println("Non-trivial 1 scenario - max: 4");
            else
                System.out.println("Non-trivial 2 scenario - max: 10");
            System.out.println("Type number of paths: ");
            Scanner scan = new Scanner(System.in);
            choice = scan.nextLine();
            
            return Integer.parseInt(choice);
            
        }
        
        public static Integer setModularity()
        {
            String choice = "";
            System.out.println("*** Set modularity for network ***");
            System.out.println("Type modularity (number): ");
            Scanner scan = new Scanner(System.in);
            choice = scan.nextLine();
            
            return Integer.parseInt(choice);
        }
}
