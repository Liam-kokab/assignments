import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LZW_CompressNotRunnable {

	
	/**
	 * @param text
	 * @return boolean[] 
	 */
	public Integer[] compress(String text) {
		text = text.toLowerCase();
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
		
		return result.toArray(new Integer[result.size()]);
	}
	
	/**
	 * purpose of this method is to report back file size of compress data
	 * @param text
	 * @param fileNum 
	 * @return long
	 */
	public long compressFileSizeTest(String text, int fileNum) {
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
		HouseKeeping hk = new HouseKeeping();
		hk.writeBooleans(bi,fileNum + "tempBoolFile.mz");
		return hk.FileSize(fileNum + "tempBoolFile.mz");		
	}

	/**
	 * @param int[]
	 * @return String Text
	 */
	public String deCompress(int[] compressData) {
		
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
	/**
	 * @param Integer[]
	 * @return String Text
	 */
	public String deCompress(Integer[] compressData) {
		int[] b = new int[compressData.length];		
		for (int i = 0; i < b.length; i++) b[i] = compressData[i];
		return deCompress(b);
	}

}
