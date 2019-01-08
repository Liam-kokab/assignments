import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * Amaz(e)ing meeting
 * @author Liam.K
 */
public class Amazeing {
	private static boolean[][] maze, shotWay;
	private static int[][] path, Coordinate;
	private static int enter, exit, counter;

	public static void main(String[] args) {
		String fileName;
		try { fileName = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			fileName = "simple_maze_20x30.txt"; }
		maze = readFile(new File(fileName)); // Testing only."" 
		if(maze == null) return;

		init(maze);

		moveDown(0, enter, 0);
		Coordinate = new int[2][path[maze.length-1][exit]];
		counter = path[maze.length-1][exit];
		findShorterstWay(maze.length-1, exit);

		int a = Coordinate[0].length/2;
		//This is a X,y coordinate,  with (x=1 , y=1) being in the bottom left of the file
		System.out.println("Meeting coordinate:\tX: " + (Coordinate[1][a]+1) + ", Y: " +
				+ (maze.length - Coordinate[0][a]));
		System.out.println("Meeting coordinate(java style):\t[" + Coordinate[1][a] +"][" + Coordinate[0][a] + "]");

		System.out.println("Shortest way is marked with + and meeting spot with X");
		print(Coordinate[1][a], Coordinate[0][a]);

	}
	
	/**
	 * Goes from end point to start point using shortest way and marks it
	 * @param i
	 * @param j
	 */
	private static void findShorterstWay(int i, int j){
		Coordinate[0][--counter] = i;
		Coordinate[1][counter] = j;

		shotWay[i][j] = true;
		if(path[i][j] == 1) return;

		if(path[i-1][j] != 0 && path[i][j] > path[i-1][j]) findShorterstWay(i-1, j);
		else if(path[i][j+1] != 0 && path[i][j] > path[i][j+1]) findShorterstWay(i, j+1);
		else if(path[i][j-1] != 0 && path[i][j] > path[i][j-1]) findShorterstWay(i, j-1);
		else if(path[i+1][j] != 0 && path[i][j] > path[i+1][j]) findShorterstWay(i+1, j);

	}
	
	/**
	 * Marks all of the accessible part of the maze
	 * @param i
	 * @param j
	 * @param c distance to start point
	 */
	private static void moveDown(int i, int j, int c){
		path[i][j] = ++c;
		if(maze.length-1 == i){exit = j; return; }

		if((maze[i+1][j]) && (c+1 < path[i+1][j] || path[i+1][j] == 0)) moveDown(i+1, j, c);
		if((maze[i][j+1]) && (c+1 < path[i][j+1] || path[i][j+1] == 0)) moveDown(i, j+1, c);
		if((maze[i][j-1]) && (c+1 < path[i][j-1] || path[i][j-1] == 0)) moveDown(i, j-1, c);
		if((i > 0 && maze[i-1][j]) && (c+1 < path[i-1][j] || path[i-1][j] == 0)) moveDown(i-1, j, c);
	}

	private static void init(boolean[][] maze) {
		shotWay = new boolean[maze.length][maze[0].length];
		path = new int[maze.length][maze[0].length];
		for (int i = 0; i < maze[0].length; i++)
			if(maze[0][i]) {enter= i; break;} 			
	}

	private static boolean[][] readFile(File file) {
		try {
			Scanner read = new Scanner(file);
			int lineCount = 1;
			String s = read.nextLine();

			while(read.hasNextLine()) if(read.nextLine().length() > 0) lineCount++;		

			boolean[][] maze = new boolean[lineCount][s.length()];

			read = new Scanner(file);
			for (int i = 0; i < lineCount; i++) {
				s = read.nextLine();
				for (int j = 0; j < s.length() ; j++) {
					if(s.charAt(j) == '1') maze[i][j] = true;
				}
			}
			read.close();
			return maze;
		} catch (FileNotFoundException e) {System.out.println(e.getMessage());}
		return null;
	}

	/**
	 * @param x of meeting spot
	 * @param y of meeting spot
	 */
	private static void print(int x, int y){
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				if(j == x && i == y) System.out.print(" X ");
				else if(shotWay[i][j])System.out.print(" + ");
				else if(maze[i][j]) System.out.print("   ");
				else System.out.print("███");
			}System.out.println();
		}
	}
}
