import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LZW_Compress {

	public static void main(String[] args) {

		String inputFile = "Folktale.txt";
		String boolFile = "compressBooleanFile.mz";
		String outputFile = "deCompressControllText.txt";


		HouseKeeping hk = new HouseKeeping();
		
		
		//Ignoring UpperCase, the assignment specified 30 letters.
		String inputData = hk.ReadFile(inputFile).toLowerCase();
		
		boolean[] compressDataOut = compress(inputData);

		hk.writeBooleans(compressDataOut, boolFile);
		Boolean[] compressDataInn = hk.readBooleans(boolFile);

		String outputData = deCompress(compressDataInn);

		

		long fileSizeCompress =0;
		long fileSizeOiginal =0;		
		try {
			fileSizeCompress = Files.size(Paths.get(boolFile));
			fileSizeOiginal = Files.size(Paths.get(inputFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(inputData.equals(outputData)) System.out.println("Input text and Decompress Text match");
		else System.out.println("Input text and Decompress Text don't match");
		
		System.out.println("Original File size: " + fileSizeOiginal + " bytes");
		System.out.println("Compress File size: " + fileSizeCompress+ " bytes");
		System.out.println("compression rate: " + ((double)fileSizeOiginal/(double)fileSizeCompress));

	}
	/**
	 * @param text
	 * @return boolean[] 
	 */
	public static boolean[] compress(String text) {
		Map<String,Integer> dictionary = new HashMap<String,Integer>();

		for (int i = 0; i < 26; i++)
			dictionary.put((char)(i+(int)'a') + "", i);

		dictionary.put("æ", 26);
		dictionary.put("ø", 27);
		dictionary.put("å", 28);
		dictionary.put(" ", 29);


		String w = "";
		ArrayList<Integer> result = new ArrayList<>();

		int i = dictionary.size();
		for (char c : text.toCharArray()) {
			String wc = w + c;
			if (dictionary.containsKey(wc))
				w = wc;
			else {
				result.add(dictionary.get(w));
				dictionary.put(wc, i++);
				w = "" + c;
			}
		}
		if (!w.equals("")) result.add(dictionary.get(w));

		int max = 0;
		for (Integer value : result) if(value > max) max = value;

		int longest = Integer.toBinaryString(max).length();

		boolean[] bi  = new boolean[longest * result.size() + (longest * 2)];

		for (int j = 0; j < longest*2; j++) bi[j] = (j<longest); 
		int index = longest * 2;
		for (int j = 0; j < result.size(); j++) {
			String s = Integer.toBinaryString(result.get(j));
			for(int k = 0; k < longest - s.length() ; k++) bi[index++] = false;
			for(int k = 0; k < s.length(); k++) bi[index++] = (s.charAt(k) == '1');
		}
		return bi;
	}

	/**
	 * @param booleanList
	 * @return String Text
	 */
	public static String deCompress(Boolean[] booleanList) {

		int k = 0;
		while(booleanList[k++]);
		k--;

		int[] compressData = new int[(booleanList.length - k*2)/k];
		int in = 0;
		String temp = "";
		for (int i = k*2; i < booleanList.length; i++) {
			temp = temp + ((booleanList[i]) ? "1":"0");			
			if(temp.length() == k) {				
				compressData[in++] = Integer.parseInt(temp,2);
				temp = "";
			}
		}

		Map<Integer,String> dictionary = new HashMap<Integer,String>();

		for (int i = 0; i < 26; i++)
			dictionary.put(i, (char)(i+(int)'a') + "");

		dictionary.put(26, "æ");
		dictionary.put(27, "ø");
		dictionary.put(28, "å");
		dictionary.put(29, " ");

		int i = dictionary.size();

		String w = ""  + (char)(compressData[0]+(int)'a');
		StringBuffer result = new StringBuffer(w);

		for (int j = 1; j < compressData.length; j++) {
			String entry;
			if (dictionary.containsKey(compressData[j])) entry = dictionary.get(compressData[j]);
			else if (compressData[j] == i) entry = w + w.charAt(0);
			else return null;

			result.append(entry);

			dictionary.put(i++, w + entry.charAt(0)); 
			w = entry;
		}
		return result.toString();
	}

}
