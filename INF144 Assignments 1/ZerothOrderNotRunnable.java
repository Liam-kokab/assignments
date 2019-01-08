import java.util.Random;

public class ZerothOrderNotRunnable {
	
	public String getText(){

		String inputFile = "Folktale.txt";
		int textLenght = 8000;

		HouseKeeping hk = new HouseKeeping();

		return GeneText(hk.ReadFile(inputFile), textLenght);

	}

	private String GeneText(String text, int size){
		Random ran = new Random();
		String s = "";
		
		for (int i = 0; i < size; i++) 
			s = s + text.charAt(ran.nextInt(text.length()));
		
		
		return s;
	}
}
