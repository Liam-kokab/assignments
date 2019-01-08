import java.io.FileInputStream;
import java.io.IOException;


public class FasterSearchInText {

	private static int bSize;

	public static void main(String[] args) {
		long time = System.currentTimeMillis();

		bSize = 4800401;
		String fileName = "compressed.dat";

		String s;
		try {
			s = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("String Not found");
			System.out.println("Please try: \njava FasterSearchInText <String>");
			return;
		}

		try {
			FileInputStream in = new FileInputStream(fileName);

			if(readBooleans(fileName, lineTohash(s, 61)))
				if(readBooleans(fileName, lineTohash(s, 29)))
					if(readBooleans(fileName, lineTohash(s, 2))){
						System.out.println("Found String: " + s);
						System.out.println("Time: " + (System.currentTimeMillis() - time));
						return;
					}	
			System.out.println("String dose not exist in file");
		} catch (IOException e) {
			System.out.println("file not found");
		}
		System.out.println("Time: " + (System.currentTimeMillis() - time));
	}
	/**
	 * Checks to see if that exact bit in given file is 1 or 0 
	 * @param fileName
	 * @param i
	 * @return true is 1 and false if 0
	 * @throws IOException
	 */
	public static boolean readBooleans(String fileName ,int i) throws IOException{
		FileInputStream in = new FileInputStream(fileName);
		boolean[] isIt = new boolean[8];
		in.skip(i/8);	
		int b = in.read();
		for (int j = 0; j < 8 ; j++){
			isIt[j] = (b & 1) != 0;
			b >>>= 1;
		}in.close();
		return isIt[i%8];
	}

	/**
	 * 
	 * @param line
	 * @param j
	 * @return hashcode
	 */
	private static int lineTohash(String line, int j) {
		int hash = 0;
		for (int i = 0; i < line.length(); i++){
			hash = (j * hash + line.charAt(i)) % bSize;
		}
		return hash;
	}

}
