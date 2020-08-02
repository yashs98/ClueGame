package experiment;



import java.util.HashMap;
import java.util.HashSet;


import experiment.BoardCell;
/**
 * 
 * @author Yash Sinha and Daniel Thorne
 * 
 * This class creates a 4x4 board, and calculates the adjacencies of the cell and the targets of the cell.
 * The class contains final integer BOARD_WIDTH and BOARD_HEIGHT and both are assigned 4. A variable called board
 * is called, which is the entire 4x4 board. It is a 2D array. A map of type HashMap is made called adjMap
 * which will contain a board cell with its adjacencies. A set of type HashSet called targets was made
 * which contains the targets of the starting cell with a certain pathlength. The class contains its default 
 * constructor, calcAdjacencies, getAdjList, getTargets, calcTargets, and getCell. 
 * 
 *
 */

public class IntBoard {
	public static final int BOARD_WIDTH = 4;
	public static final int BOARD_HEIGHT = 4;
	private BoardCell[][] board;
	private HashMap <BoardCell,HashSet<BoardCell>> adjMap;
	private HashSet<BoardCell> targets;
	private HashSet<BoardCell> visited;
	
	/**
	 * This is a constructor that initializes the HashMap, and creates a new board, and creates a set of type
	 * HashSet that contains the adjacencies of the starting cell.  
	 */
	public IntBoard() {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		board = new BoardCell[BOARD_HEIGHT][BOARD_WIDTH];
		adjMap = new HashMap<BoardCell,HashSet<BoardCell>>();
		
		for(int i = 0; i < BOARD_HEIGHT; i++) {
			for(int j = 0; j < BOARD_WIDTH; j++) {
				board[i][j] = new BoardCell(i,j);

				
				

				HashSet<BoardCell> set = new HashSet<BoardCell>();

				adjMap.put(board[i][j], set);
			}
		}
	}
    /**
     * This function calculates the adjacencies of each cell and puts them in a map with the corresponding cell.
     */
	public void calcAdjacencies(){
		for(int i = 0; i < BOARD_HEIGHT; i++) {
			for(int j = 0; j < BOARD_WIDTH; j++) {
				if(i - 1 >= 0) {
					adjMap.get(board[i][j]).add(board[i-1][j]);
				}
				if(i + 1 < BOARD_HEIGHT) {
					adjMap.get(board[i][j]).add(board[i+1][j]);
				}
				if(j - 1 >= 0) {
					adjMap.get(board[i][j]).add(board[i][j-1]);
				}
				if(j + 1 < BOARD_WIDTH) {
					adjMap.get(board[i][j]).add(board[i][j+1]);
				}
			}
		}
	}
	/**
	 * 
	 * @param cell
	 * @return 
	 * 
	 * This function gets the adjacencies of a particular board cell given and returns the set. 
	 */
	public HashSet<BoardCell> getAdjList(BoardCell cell){
		return adjMap.get(cell);
	}
	

	
		
			
    /**
     * 
     * @return
     * 
     * returns the targets of a cell.
     */
	public HashSet<BoardCell> getTargets(){

		return targets;
	}
	
	/**
	 * 
	 * @param startCell
	 * @param pathLength
	 * 
	 * calculates the targets of the starting cell with a specific length from the starting cell. 
	 */
	public void calcTargets(BoardCell startCell, int pathLength){
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		findTargets(startCell, pathLength);
	}
	
	/**
	 * 
	 * @param cell
	 * @param pathLength
	 * 
	 * Recursively finds all the viable target cells to move to given a starting cell and path length.
	 */
	public void findTargets(BoardCell cell, int pathLength) {
		for(BoardCell adjCell: getAdjList(cell)) {
			if(!visited.contains(adjCell)) {
				visited.add(adjCell);
				if(pathLength == 1) {
					targets.add(adjCell);
				}
				else {
					findTargets(adjCell, pathLength-1);
				}
				visited.remove(adjCell);
			}
		}
	}
	/**
	 * 
	 * @param r
	 * @param c
	 * @return
	 * 
	 * This function returns the cell with a specific row and column inputed. 
	 */
	public BoardCell getCell(int r, int c) {
		return board[r][c];
	}
}
