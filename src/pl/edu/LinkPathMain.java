package pl.edu;

public class LinkPathMain {

	public static void main(String[] args) {
		Model model = new Model();
		Parser.parse("resources/trivial.txt", 0, 2, 2);
	}
	
}
