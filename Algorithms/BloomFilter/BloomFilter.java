import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Compulsory assignment 2-INF 102-Autumn 2016
 * @author Liam Kokab(lko015)
 * 
 *
 */
public class BloomFilter {

	private static int bSize;
	private static String compFaleName;
	
	public static void main(String[] args) {
		bSize = 0;
		try {
			bSize = Integer.parseInt(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
		} catch (NumberFormatException e) {
		}
		if(bSize == 0) bSize = 4800401; //4800401

		int lineCount = 1000000;
		compFaleName = "compressed.dat";
		String fileName = "Ecoli.1M.36mer.txt";

		boolean[] isIt = null;
		try {
			isIt = readFile(fileName, lineCount);
		} catch (FileNotFoundException e) {
			System.out.println("Truble reading from file: " + e.getMessage() );
			return;
		}
		if(isIt == null) return;
		int o = 0, z=0;
		
		
		
		for (int i = 0; i < isIt.length; i++) {
			if(isIt[i]) o++;
			else z++;
		}
		double oa = ((double)o / (double)bSize);
		double za = ((double)z / (double)bSize);
		System.out.println(oa);
		double res = -oa*log2(oa)-za*log2(za);
		System.out.println(res);
		
		writeBooleans(isIt, compFaleName);

	}
	private static double log2(double a){
		return Math.log(a)/Math.log(2);
	}

	/**
	 * Read the file and call write to file when done
	 * @param fileName
	 * @param LineCount
	 * @throws FileNotFoundException
	 */
	private static boolean[] readFile(String fileName, int LineCount) throws FileNotFoundException{
		String s;
		boolean[] isIt = new boolean[bSize];

		File file = new File(fileName);
		Scanner read = new Scanner(file);
		
		for (int i = 0; i < LineCount; i++) {
			s = read.nextLine();
			isIt[lineToHash(s, 61)] = true;
			isIt[lineToHash(s, 29)] = true;
			isIt[lineToHash(s, 2)] = true;

		}
		return isIt;
		//writeBooleans(isIt, compFaleName);
	}

	/**
	 * @param line
	 * @param j
	 * @return Hash
	 */
	private static int lineToHash(String line, int j) {
		int hash = 0;
		for (int i = 0; i < line.length(); i++){
			hash = (j * hash + line.charAt(i)) % bSize;
		}
		return hash;
	}

	/**
	 * write to a file as binary
	 * @param Boolean array  
	 * @param fileName to write to
	 */
	public static void writeBooleans(boolean[] isIt,String fileName){
		try {
			FileOutputStream out = new FileOutputStream(fileName);

			for (int i = 0; i < isIt.length; i += 8) {
				int b = 0;
				for (int j = Math.min(i + 7, isIt.length-1); j >= i; j--) {
					b = (b << 1) | (isIt[j] ? 1 : 0);
				}
				out.write(b);
			}
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * this is not compration :(
	 * @param a
	 * @return
	 */
	public static Boolean[] boolComp(boolean[] a){
		ArrayList<Boolean> b = new ArrayList();
		int i = 0;
		while(i<a.length){
			boolean tO = a[i]; // tO = thisOne		
			int count = 0;
			for (int j = i+1; j < i+4; j++) {
				if(j < a.length && tO == a[j]) count++;
				else break;
			}
			i = i + count + 1;
			String s =Integer.toBinaryString(count);
			
			b.add(tO);
			
			if(s.length() > 2)System.out.println(s);
			
			if(s.length() == 1) b.add(false);
			//if(s.length() < 3) b.add(false);
			
			for (int j = 0; j < s.length(); j++) {			
				b.add(s.charAt(j) == '1');
			}
			tO = (tO)? false : true;
		}
		
		return b.toArray(new Boolean[b.size()]);
		
	}

}
