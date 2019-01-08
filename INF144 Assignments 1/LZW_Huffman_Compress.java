import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LZW_Huffman_Compress {
	private static int fileNum = 0;

	public static void main(String[] args) {
		HouseKeeping hk = new HouseKeeping();
		String input = "Folktale.txt";

		ZerothOrderNotRunnable ZO = new ZerothOrderNotRunnable();
		FirstOrderNotRunnable FO = new FirstOrderNotRunnable();
		SecondOrderNotRunnable SO = new SecondOrderNotRunnable();
		ThirdOrderNotRunnable TO = new ThirdOrderNotRunnable();

		LZW_CompressNotRunnable LZW = new LZW_CompressNotRunnable();

		double[] Folktale = runTest("Folktale.txt");
		System.out.println("Folktale.txt");
		if(Folktale != null) System.out.println("Original size: " + Folktale[0] + "\t\t LZW size: "+ Folktale[1] + "\t\t LZW Huffman size: "+ Folktale[2] + "\n\n");
		
		
		double[] sizeZ  = new double[3];
		double[] sizeF  = new double[3];
		double[] sizeS  = new double[3];
		double[] sizeT  = new double[3];
		double [] size;
		
		int a = 100;
		
		for (int i = 0; i < a; i++) {
			hk.WreiteToFileOneLine(ZO.getText(), "ZerothOrderTest.txt");
			size =  runTest("ZerothOrderTest.txt");		
			sizeZ[0]  = sizeZ[0] + size[0];
			sizeZ[1]  = sizeZ[1] + size[1];
			sizeZ[2]  = sizeZ[2] + size[2];
			
			hk.WreiteToFileOneLine(FO.getText(), "FirstOrderTest.txt");
			size = runTest("FirstOrderTest.txt");		
			sizeF[0]  = sizeF[0] + size[0];
			sizeF[1]  = sizeF[1] + size[1];
			sizeF[2]  = sizeF[2] + size[2];
			
			hk.WreiteToFileOneLine(SO.getText(), "SecondOrderTest.txt");
			size = runTest("SecondOrderTest.txt");	
			sizeS[0]  = sizeS[0] + size[0];
			sizeS[1]  = sizeS[1] + size[1];
			sizeS[2]  = sizeS[2] + size[2];
			
			hk.WreiteToFileOneLine(TO.getText(), "ThirdOrderTest.txt");
			size = runTest("ThirdOrderTest.txt");	
			sizeT[0]  = sizeT[0] + size[0];
			sizeT[1]  = sizeT[1] + size[1];
			sizeT[2]  = sizeT[2] + size[2];
		}
		
		System.out.println("ZerothOrder: ");
		System.out.println("Original size: " + sizeZ[0]/a + "\t\t LZW size: "+ sizeZ[1]/a + "\t\t LZW Huffman size: "+ sizeZ[2]/a + "\n\n");
		
		System.out.println("FirstOrder: ");
		System.out.println("Original size: " + sizeF[0]/a + "\t\t LZW size: "+ sizeF[1]/a + "\t\t LZW Huffman size: "+ sizeF[2]/a + "\n\n");
		
		System.out.println("SecondOrder: ");
		System.out.println("Original size: " + sizeS[0]/a + "\t\t LZW size: "+ sizeS[1]/a + "\t\t LZW Huffman size: "+ sizeS[2]/a + "\n\n");
		
		System.out.println("ThirdOrder: ");
		System.out.println("Original size: " + sizeT[0]/a + "\t\t LZW size: "+ sizeT[1]/a + "\t\t LZW Huffman size: "+ sizeT[2]/a + "\n\n");
		
		
		

	}
	/**
	 * 
	 * @param data
	 * @return long{OriginalFileSize, CompressLZWFileSize, CompressLZMHuffmanFileSize}
	 */
	public static double[] runTest(String inputFileName){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		HouseKeeping hk = new HouseKeeping();		
		String data = hk.ReadFile(inputFileName).toLowerCase();
		
		double[] size  = new double[3];
		
		//getting original fileSize
		size[0] = hk.FileSize(inputFileName);
		if(size[0] < 0) return null;
		
		//getting file size for LZW compress
		LZW_CompressNotRunnable LZW = new LZW_CompressNotRunnable();
		size[1] = LZW.compressFileSizeTest(data ,fileNum);
		if(size[1] < 0) return null;
				
		Integer[] conNum = LZW.compress(data);
		int max = 0;
		for (int i = 0; i < conNum.length; i++) if(conNum[i] > max) max = conNum[i];

		int[] Freqs = new int[max+1];
		for (int i = 0; i < conNum.length; i++) Freqs[conNum[i]]++;
		
		Huffman HM = new Huffman();
		HuffmanTree tree = HM.buildTree(Freqs);
		
		Map<String, Integer> codeDeCompress = new HashMap<String, Integer>();
		Map<Integer, String> codeCompress = new HashMap<Integer, String>();
		
		
		codeBuilder(codeDeCompress, codeCompress, tree, new StringBuffer());
		
		ArrayList<Boolean> boolList = new ArrayList<>();
		for (int i = 0; i < conNum.length; i++) 
			for(char c : codeCompress.get(conNum[i]).toCharArray()) boolList.add(c == '1');
				
		hk.writeBooleans(boolList.toArray(new Boolean[boolList.size()]), fileNum + "temp.mz");
		
		//getting FileSize for LZW Huffman compress
		size[2] = hk.FileSize(fileNum + "temp.mz");
		if(size[2] < 0) return null;
		
		//NO need to deCompress
		
//		Boolean[] inputBool = hk.readBooleans(fileNum +"temp.mz");
//		
//		String temp = "";
//		ArrayList<Integer> deCom = new ArrayList<>();
//		for (int i = 0; i < inputBool.length; i++) {
//			temp = temp + ((inputBool[i]) ? '1' : '0');
//			if(codeDeCompress.containsKey(temp)) {
//				deCom.add(codeDeCompress.get(temp));
//				temp = "";
//			}
//		}
		
		fileNum++;
		return size;
	}
	
	
	public static void codeBuilder(Map<String, Integer> codeDeCompress, Map<Integer, String> codeCompress, HuffmanTree tree, StringBuffer prefix){
		
		if (tree instanceof HuffmanLeaf) {
			HuffmanLeaf leaf = (HuffmanLeaf)tree;
			codeDeCompress.put(prefix.toString(), leaf.value);
			codeCompress.put(leaf.value, prefix.toString());
		} else if (tree instanceof HuffmanNode) {
			HuffmanNode node = (HuffmanNode)tree;

			//left
			prefix.append('0');
			codeBuilder(codeDeCompress, codeCompress, node.left, prefix);
			prefix.deleteCharAt(prefix.length()-1);

			//right
			prefix.append('1');
			codeBuilder(codeDeCompress, codeCompress, node.right, prefix);
			prefix.deleteCharAt(prefix.length()-1);
		}
	}
}


