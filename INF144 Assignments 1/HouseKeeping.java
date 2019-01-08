import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class HouseKeeping {

	/**
	 * @param charAt
	 * @return index
	 */
	public int indexGen(char charAt) {
		if(charAt >= 'a' && charAt <= 'z') return (int)charAt - (int)'a';
		if(charAt >= 'A' && charAt <= 'Z') return (int)charAt - (int)'A';		
		if(charAt == 'æ' || charAt == 'Æ') return 26;
		if(charAt == 'ø' || charAt == 'Ø') return 27;
		if(charAt == 'å' || charAt == 'Å') return 28;
		return 29;
	}
	/**
	 * returns the char for a number where a = 0 ... z = 25 , æ = 26 , ø = 27 , å = 28 and ' ' = 29
	 * @param int
	 * @return char
	 */
	public char rIG(int a){
		if(a >= 0 && a < 26) return (char)(a + (int)'a');
		
		if(a == 26) return 'æ';
		if(a == 27) return 'ø';
		if(a == 28) return 'å';
		return ' ';
	}

	/**
	 * read input file 
	 * @return String in the file
	 */
	public String ReadFile(String inputFile){
		try {
			return new Scanner(new File(inputFile)).nextLine();
		} catch (FileNotFoundException e) {
			System.out.println("File troble :P");
			return null;
		}
	}
	/**
	 * read a file of numbers
	 * @return int[]
	 */
	public int[] ReadFileInteger(String fileName){
		Scanner read;
		ArrayList<Integer> a = new ArrayList<>();
		try {
			read = new Scanner(new File(fileName));
			while(read.hasNextInt()) a.add(read.nextInt());		
		} catch (FileNotFoundException e) {
			System.out.println("File troble :P");
			return null;
		}
		
		int[] b = new int[a.size()];
		for (int i = 0; i < b.length; i++) b[i] = a.get(i);
		return b;
	}

	/**
	 * writes the text to outputfile
	 * auto new Line after every 100 character
	 * @param outText
	 * * @param OutputFile
	 */
	public void WreiteToFile(String outText, String OutputFile){
		String[] words = outText.split(" ");
		ArrayList<String> text = new ArrayList<>();
		String temp = "";
		for (int i = 0; i < words.length; i++) {
			temp = temp + words[i] + " ";
			if(temp.length() > 100){
				text.add(temp);
				temp = "";
			}
		}

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(OutputFile));
			for (String s : text) writer.println(s);
		}catch(FileNotFoundException e) {
			System.out.println("Writing to file error");
		}finally{
			writer.close();
		}
	}
	/**
	 * write 
	 * @param outText
	 * @param OutputFile
	 */
	public void WreiteToFileOneLine(String outText, String OutputFile){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(OutputFile));
			writer.println(outText);
			writer.flush();
			writer.close();
		}catch(FileNotFoundException e) {
			System.out.println("Writing to file error");
		}
	}
	
	/**
	 * Write a list of int to file
	 * @param outInt
	 * @param OutputFile
	 */
	public void WreiteToFile(int[] outInt, String OutputFile){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(OutputFile));
			
			for (int i = 0; i < outInt.length; i++) writer.print(outInt[i] + " ");
		}catch(FileNotFoundException e) {
			System.out.println("Writing to file error");
		}finally{
			writer.close();
		}
	}
	
	/**
	 * write to a file as binary
	 * @param boolean[] 
	 * @param fileName to write to
	 */
	public void writeBooleans(boolean[] boolList, String fileName){
		try {
			FileOutputStream out = new FileOutputStream(fileName);

			for (int i = 0; i < boolList.length; i += 8) {
				int b = 0;
				for (int j = Math.min(i + 7, boolList.length-1); j >= i; j--) {
					b = (b << 1) | (boolList[j] ? 1 : 0);
				}
				out.write(b);
			}
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * write to a file as binary
	 * @param Boolean[] 
	 * @param fileName
	 */
	public void writeBooleans(Boolean[] boolList, String fileName) {
		try {
			FileOutputStream out = new FileOutputStream(fileName);

			for (int i = 0; i < boolList.length; i += 8) {
				int b = 0;
				for (int j = Math.min(i + 7, boolList.length-1); j >= i; j--) {
					b = (b << 1) | (boolList[j] ? 1 : 0);
				}
				out.write(b);
			}
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}		
	}
	
	/**
	 * read binary file and returns a boolean array.
	 * @param Size of expected array
	 * @return array of boolean
	 */
	public Boolean[] readBooleans(String fileName){
		ArrayList<Boolean> boolList = new ArrayList<>();
		
		try {
			FileInputStream in = new FileInputStream(fileName);
			int i = 0;
			while(true){
				int b = in.read();
				if(b == -1) break;
				for (int j = i; j < i + 8 && j < 99999999; j++){
					boolList.add((b & 1) != 0);
					b >>>= 1;
				}i++;
			}
			in.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return boolList.toArray(new Boolean[boolList.size()]);
	}
	/**
	 * 
	 * @param FileName
	 * @return file size or -1 if file not fund
	 */
	public long FileSize(String FileName){
		try {
			return Files.size(Paths.get(FileName));
		} catch (IOException e) {
			return -1;
		}
	}

	
}
