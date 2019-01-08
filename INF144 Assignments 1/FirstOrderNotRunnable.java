import java.util.ArrayList;
import java.util.Random;

public class FirstOrderNotRunnable {
	private static ArrayList<Character>[] charList;
	private static HouseKeeping hk;

	public String getText(){

		String inputFile = "Folktale.txt";
		int textLenght = 8000;


		hk = new HouseKeeping();

		charList = new ArrayList[30];
		Analyse(hk.ReadFile(inputFile));
		return GeneText(textLenght);
	}


	private void Analyse(String text){
		for (int i = 0; i < charList.length; i++) charList[i] = new ArrayList<>();

		for (int i = 0; i < text.length()-1; i++) {
			charList[hk.indexGen(text.charAt(i))].add(text.charAt(i+1));
		}
	}

	private String GeneText(int size){
		Random ran = new Random();

		String s = "e";

		for (int i = 0; i < size; i++) {
			int index1 = hk.indexGen(s.charAt(s.length()-1));
			char a = charList[index1].get(ran.nextInt(charList[index1].size()));
			s = s + a;
		}	
		return s;
	}
}


