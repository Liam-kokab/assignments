import java.util.PriorityQueue;

public class Huffman{
	public HuffmanTree buildTree(int[] Freqs) {
		PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
		
		for (int i = 0; i < Freqs.length; i++)
			if (Freqs[i] > 0)
				trees.offer(new HuffmanLeaf(Freqs[i], i));

		while (trees.size() > 1) {
			HuffmanTree a = trees.poll();
			HuffmanTree b = trees.poll();

			trees.offer(new HuffmanNode(a, b));
		}
		return trees.poll();
	}
}

abstract class HuffmanTree implements Comparable<HuffmanTree> {
	int freq;
	public HuffmanTree(int freq) { 
		this.freq = freq; 
		}

	public int compareTo(HuffmanTree tree) {
		return freq - tree.freq;
	}
}

class HuffmanLeaf extends HuffmanTree {
	int value;
	public HuffmanLeaf(int freq, int value) {
		super(freq);
		this.value = value;
	}
}

class HuffmanNode extends HuffmanTree {
	HuffmanTree left, right; // subtrees
	public HuffmanNode(HuffmanTree left, HuffmanTree right) {
		super(left.freq + right.freq);
		this.left = left;
		this.right = right;
	}
}
