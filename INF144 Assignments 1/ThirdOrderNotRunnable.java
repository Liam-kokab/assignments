import java.util.ArrayList;
import java.util.Random;

public class ThirdOrderNotRunnable {
	private static ArrayList<Character>[][][] charList;
	private static HouseKeeping hk;

	public String getText(){
		String inputFile = "Folktale.txt";
		int textLenght = 8000;
		
		hk = new HouseKeeping();
		
		charList = new ArrayList[30][30][30];
		Analyse(hk.ReadFile(inputFile));
		return GeneText(textLenght);
	}
	
	
	private String GeneText(int size){
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
	
	private void Analyse(String text){
		for (int i = 0; i < charList.length; i++) 
			for (int j = 0; j < charList.length; j++)
				for (int k = 0; k < charList.length; k++)
					charList[i][j][k] = new ArrayList<>();
		
		for (int i = 0; i < text.length()-3; i++) {
			charList[hk.indexGen(text.charAt(i))][hk.indexGen(text.charAt(i+1))][hk.indexGen(text.charAt(i+2))].add(text.charAt(i+3));
		}
	}
}