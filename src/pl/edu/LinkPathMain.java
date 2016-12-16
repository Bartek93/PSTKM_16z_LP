package pl.edu;

public class LinkPathMain {

	public static void main(String[] args) {
		Model model = new Model();
                System.out.println("---------- 1st example ----------");
		Parser.parse("resources/trivial.txt", 0, 2, 2);
                
		System.out.println("---------- 2nd example ----------");
		Parser.parse("resources/non-trivial_1.txt", 1, 3, 4);
		Parser.parse("resources/non-trivial_1.txt", 1, 4, 4);
		
                System.out.println("---------- 3rd example ----------");
		Parser.parse("resources/non-trivial_2.txt", 6, 0, 4);
		Parser.parse("resources/non-trivial_2.txt", 10, 4, 9); //it doesn't get the shortest, it gets last in list
		Parser.parse("resources/non-trivial_2.txt", 2, 9, 4);	
        }
}
