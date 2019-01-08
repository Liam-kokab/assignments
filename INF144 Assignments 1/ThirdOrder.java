import java.util.ArrayList;
import java.util.Random;

public class ThirdOrder {
	private static ArrayList<Character>[][][] charList;
	private static HouseKeeping hk;

	public static void main(String[] args) {
		
		
		
		String inputFile = "Folktale.txt";
		String outputFile = "ThirdOrderOutput.txt";
		int textLenght = 8000;
		
		//input file can be given with a parameter 
		try { inputFile = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {}
		
		
		hk = new HouseKeeping();
		
		charList = new ArrayList[30][30][30];
		Analyse(hk.ReadFile(inputFile));
		String t = GeneText(textLenght);
		hk.WreiteToFile(t, outputFile);
		System.out.println("Done");
		
		
	}
	
	
	private static String GeneText(int size){
		Random ran = new Random();
		
		String s = "det";
				
		for (int i = 0; i < size; i++) {
			
			int index1 = hk.indexGen(s.charAt(s.length()-3));
			int index2 = hk.indexGen(s.charAt(s.length()-2));
			int index3 = hk.indexGen(s.charAt(s.length()-1));
			
			char a = charList[index1][index2][index3].get(ran.nextInt(charList[index1][index2][index3].size()));
			s = s + a;
		}	
		return s;
	}
	
	private static void Analyse(String text){
		for (int i = 0; i < charList.length; i++) 
			for (int j = 0; j < charList.length; j++)
				for (int k = 0; k < charList.length; k++)
					charList[i][j][k] = new ArrayList<>();
		
		for (int i = 0; i < text.length()-3; i++) {
			charList[hk.indexGen(text.charAt(i))][hk.indexGen(text.charAt(i+1))][hk.indexGen(text.charAt(i+2))].add(text.charAt(i+3));
		}
	}
	
	
}