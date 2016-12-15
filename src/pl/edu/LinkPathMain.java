package pl.edu;

public class LinkPathMain {

	public static void main(String[] args) {
		Model model = new Model();
		Parser.parse("resources/trivial.txt", 0, 2, 2);
		
		Parser.parse("resources/non-trivial_1.txt", 1, 3, 4);
		Parser.parse("resources/non-trivial_1.txt", 1, 4, 4);
		
		Parser.parse("resources/non-trivial_1.txt", 6, 0, 4);
		Parser.parse("resources/non-trivial_1.txt", 10, 4, 4);
		Parser.parse("resources/non-trivial_1.txt", 2, 9, 4);
		
	}
	
}
