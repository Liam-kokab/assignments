import java.util.ArrayList;
import java.util.Random;

public class SecondOrder {
	
	private static ArrayList<Character>[][] charList;
	private static HouseKeeping hk;

	public static void main(String[] args) {

		String inputFile = "Folktale.txt";
		String outputFile = "SecondOrderOutput.txt";
		int textLenght = 8000;
		
		//input file can be given with a parameter 
		try { inputFile = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {}
		
		
		hk = new HouseKeeping();
		
		charList = new ArrayList[30][30];
		Analyse(hk.ReadFile(inputFile));
		String t = GeneText(textLenght);
		hk.WreiteToFile(t, outputFile);
		System.out.println("Done");
		
		
	}
	
	
	private static String GeneText(int size){
		Random ran = new Random();
		
		String s = "de";
				
		for (int i = 0; i < size; i++) {
			int index1 = hk.indexGen(s.charAt(s.length()-2));
			int index2 = hk.indexGen(s.charAt(s.length()-1));
			char a = charList[index1][index2].get(ran.nextInt(charList[index1][index2].size()));
			s = s + a;
		}	
		return s;
	}
	
	private static void Analyse(String text){
		for (int i = 0; i < charList.length; i++) 
			for (int j = 0; j < charList.length; j++)
				charList[i][j] = new ArrayList<>();
		
		for (int i = 0; i < text.length()-2; i++) {
			charList[hk.indexGen(text.charAt(i))][hk.indexGen(text.charAt(i+1))].add(text.charAt(i+2));
		}
	}
	
}
