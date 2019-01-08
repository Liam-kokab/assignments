import java.util.Random;

public class ZerothOrder {
	
	
	private static HouseKeeping hk;
	
	/**
	 * log base, if it is not 2 you can change it here.
	 */
	private static int logBase = 2;
	
	
	/**
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

		String inputFile = "Folktale.txt";
		String outputFile = "ZerothOrderOutput.txt";
		int textLenght = 8000;

		//input file can be given with a parameter 
		try { inputFile = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {}

		hk = new HouseKeeping();
		String text = hk.ReadFile(inputFile);
				
		int[] integerListForOrigianlText = Analyse(text);
				
		//printing numbers for the original text
		System.out.println("Original text Probabilities:");
		printProbabilities(integerListForOrigianlText, text.length());
		
		System.out.println();
		System.out.println();
		
		String t = GeneText(integerListForOrigianlText.clone() , text.length());		
		int[] integerListForNewGeneratedText = Analyse(t);
		
		//print numbers for new generated text
		System.out.println("new generated text Probabilities:");
		printProbabilities(integerListForNewGeneratedText, t.length());
		
		System.out.println("entropy calculation: ");
		System.out.println("for otiginal text H(x) = " + entropy(integerListForOrigianlText, text.length()));
		System.out.println("for new generated text H(x) = " + entropy(integerListForNewGeneratedText, text.length()));
		
		hk.WreiteToFile(t, outputFile);
		
		System.out.println("Done");

	}
	
	/**
	 * Calculating H(x) = sum (- pi log2(pi) ) 30 times, one time for each letter, 
	 * @param integerList
	 * @param length
	 * @return H(x) = 
	 */
	private static double entropy(int[] integerList, int length) {
		double Hx = 0;
		for (int i = 0; i < integerList.length; i++) {
			double Probability = (double)integerList[i] / length;
			Hx = Hx - (Probability * log(Probability, logBase));
		}
		return Hx;
	}
	
	/**
	 * @param x
	 * @param base
	 * @return log(base) for (x)
	 */
	private static double log(double x, int base){
		if(x == 0) return 0;
	    return (Math.log(x) / Math.log(base));
	}

	/**
	 * Print "Number of (every letter) :(Number of occurrence in text) 
	 * this gives (every letter) a Probability of (occurrence/ length of the text)"
	 * @param integerList
	 * @param length
	 */
	private static void printProbabilities(int[] integerList, int length) {
		for (int i = 0; i < integerList.length; i++) {
			//%.4f for 4 floating points, for more of less you can change 4.
			String Probability = String.format("%.4f", ((double)integerList[i] / length));
			
			System.out.println("Number of ("+ hk.rIG(i) +") :" + integerList[i] + "\t this gives (" +  hk.rIG(i) + ") a Probability of " + Probability);
		}
	}
	
	/**
	 * generate list of 30 with number of occurrence of each letter on there respected position.
	 * @param text
	 * @return list of integer
	 */
	private static int[] Analyse(String text){
		int[] charList = new int[30];
		
		for (int i = 0; i < text.length(); i++) {
			charList[hk.indexGen(text.charAt(i))]++;
		}
		return charList;
	}
	
	/**
	 * this one uses only numbers to generate text.
	 * @param integerList
	 * @param size
	 * @return String of new generated Text
	 */	
	private static String GeneText(int[] integerList, int size){
		Random random = new Random();
		for (int i = 1; i < integerList.length; i++) {
			integerList[i] = integerList[i] + integerList[i-1]; 
		}
		
		String s  = "";
		for (int i = 0; i < size; i++) {
			int ran = random.nextInt(integerList[29]);
			for (int j = 0; j < integerList.length; j++) {
				if(ran <= integerList[j]){
					s = s + hk.rIG(j);
					break;
				}
			}
		}
		return s;
	}
}
