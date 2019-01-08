import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Compulsory assignment 2-INF 102-Autumn 2016
 * @author Liam Kokab(lko015)
 * 
 *
 */
public class BloomFilterReader {

	private static int bSize;
	private static String compFaleName;
	public static void main(String[] args) {
		bSize = 0;
		try {
			bSize = Integer.parseInt(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
		} catch (NumberFormatException e) {
		}
		if(bSize == 0) bSize = 4800401;

		compFaleName = "compressed.dat";
		int lineCount = 2000000;
		int posetiv;
		String fileName = "Ecoli.2M.36mer.txt";


		try {
			posetiv = readFile(fileName, lineCount, readBooleans(compFaleName));
		} catch (FileNotFoundException e) {
			System.out.println("Truble reading from file: " + e.getMessage() );
			return;
		}

		System.out.println("Number of positive: " + posetiv);

		//auto calc the score and print it out !!!
		printScore(posetiv); 

	}
	/**
	 * read the files and count the positives
	 * @param fileName
	 * @param LineCount number of line in the file
	 * @param isIt
	 * @return number of positives 
	 * @throws FileNotFoundException
	 */
	public static int readFile(String fileName, int LineCount, Boolean[] isIt) throws FileNotFoundException{
		String s;
		int hits = 0;

		File file = new File(fileName);
		Scanner read = new Scanner(file);

		for (int i = 0; i < LineCount; i++) {
			s = read.nextLine();
			if(isIt[lineTohash(s,61)])
				if(isIt[lineTohash(s,29)])
					if(isIt[lineTohash(s,2)])
						hits++;
		}
		return hits;
	}
	/**
	 * @param line
	 * @param j
	 * @return Hash
	 */
	private static int lineTohash(String line, int j) {
		int hash = 0;
		for (int i = 0; i < line.length(); i++){
			hash = (j * hash + line.charAt(i)) % bSize;
		}
		return hash;
	}

	/**
	 * read the compressed file and returns a boolean array.
	 * @param Size of expected array
	 * @return array of boolean
	 */
	/*public static boolean[] readBooleans(String fileName){
		boolean[] isIt = new boolean[bSize];

		try {
			FileInputStream in = new FileInputStream(fileName);
			for (int i = 0; i < isIt.length; i += 8){
				int b = in.read();
				for (int j = i; j < i + 8 && j < isIt.length; j++){
					isIt[j] = (b & 1) != 0;
					b >>>= 1;
				}
			}
			in.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return isIt;
	}*/
	public static Boolean[] readBooleans(String fileName){
		ArrayList<Boolean> bool = new ArrayList<>();
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileName);
			for (int i = 0;; i += 8){
				int b = in.read();
				for (int j = i; j < i + 8; j++){
					bool.add((b & 1) != 0);
					b >>>= 1;
				}
			}

		} catch (IOException e) {
			System.out.println("weited error");

		}
		if(in != null)
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return bool.toArray(new Boolean[bool.size()]);
	}

	/**
	 * prints out the score
	 * @param Nuber of positive hits
	 */
	private static void printScore(int posetiv){
		long fileSizeC =0;
		long fileSizeO =0;

		try {
			fileSizeC = Files.size(Paths.get(compFaleName));
			fileSizeO = Files.size(Paths.get("Ecoli.1M.36mer.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("FPR: \t" + ((double)(posetiv - 1000000)/(double)posetiv));

		System.out.println("FSi: \t" + fileSizeO);
		System.out.println("FSc: \t" + fileSizeC);



		double Score  = (double)((double)fileSizeO/ (double) fileSizeC)*(1.0-(2.0*((double)(posetiv - 1000000)/(double)posetiv)));	
		System.out.println("Score: " + Score);



	}

}
