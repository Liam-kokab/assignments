import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SearchInText {

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		try {
			File file = new File(args[0]);
			Scanner read = new Scanner(file);
			
			if(checkString(read, args[1])) System.out.println("Found String: " + args[1] );
			else System.out.println("String dose not exist in file");
			
		} catch (FileNotFoundException e) {
			System.out.println("File " + args[0] + " was not fount");
			return;
		} catch (ArrayIndexOutOfBoundsException b){
			System.out.println("agrument was not found");
			System.out.println("Please try: \njava SearchInText <TextFile> <String>");
			return;
		}

		System.out.println("Time: " + (System.currentTimeMillis() - time));
	}
	/**
	 * @param read
	 * @param text
	 * @return true if String found false if not.
	 */
	private static boolean checkString(Scanner read, String text){
		while (read.hasNext()) {
			if(read.next().compareTo(text) == 0) return true;
		}return false;
	}
	

}
