import java.util.ArrayList;
import java.util.Random;

public class FirstOrder {
	private static HouseKeeping hk;

	public static void main(String[] args) {

		String inputFile = "Folktale.txt";
		String outputFile = "FirstOrderOutput.txt";
		int textLenght = 8000;

		//input file can be given with a parameter 
		try { inputFile = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {}


		hk = new HouseKeeping();
		String originalText = hk.ReadFile(inputFile);

		//charList = new ArrayList[30];
		int[][] integerListForOrigianlText = Analyse(originalText);
		System.out.println("print probabilities for original text");
		printProbabilities(integerListForOrigianlText, textLenght);
		
		String t = GeneText(integerListForOrigianlText.clone() , originalText.length());
		int[][] integerListForNewGenText = Analyse(t);
		
		System.out.println("print probabilities for new generated text------------------------------------------------------------");
		printProbabilities(integerListForNewGenText, textLenght);
		hk.WreiteToFile(t ,outputFile);
		System.out.println("Done");
	}

	private static int[][] Analyse(String text){
		int[][] integerList =  new int[30][30];
		for (int i = 0; i < text.length()-1; i++) {			
			integerList[hk.indexGen(text.charAt(i))][hk.indexGen(text.charAt(i+1))]++;			
		}
		return integerList;
	}
	
	private static String GeneText(int[][] integerList, int size){
		Random random = new Random();
		for (int i = 1; i < integerList.length; i++) {
			for (int j = 0; j < integerList.length; j++) {
				integerList[j][i] = integerList[j][i] + integerList[j][i-1];
			}
			 
		}
		
		String s  = " ";
		for (int i = 1; i < size; i++) {
			int ran = random.nextInt(integerList[hk.indexGen(s.charAt(i-1))][29]);
			for (int j = 0; j < integerList.length; j++) {
				if(ran <= integerList[hk.indexGen(s.charAt(i-1))][j]){
					s = s + hk.rIG(j);
					break;
				}
			}
		}
		return s;	
	}
	
	private static void printProbabilities(int[][] integerList, int length) {
		int sum[] = new int[30];
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				sum[i] = sum[i] + integerList[i][j];
			}
		}
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				System.out.print("Given a: (" + hk.rIG(i) + ") (" + hk.rIG(j) + ") occurred " + integerList[i][j] + " times.");
				String Probability = String.format("%.4f", ((double)integerList[i][j] / sum[i]));
				System.out.println("\t this gives (" + hk.rIG(j) + ") " + Probability + " chance of occurrence given (" + hk.rIG(i) + ")");
			}
		}
	}
	
}


