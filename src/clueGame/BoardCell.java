package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;

/**
 * 
 * @author Yash Sinha and Daniel Thorne and Calvin Mak
 *
 *The class BoardCell is the individual cell of the board. It only has two variables that represent the row 
 *and the column of the cell. The class also contains a constructor. 
 */
public class BoardCell {
	
	private int row;
	private int column;
	private DoorDirection direction;
	private boolean isDoor;
	
	private char initial;
	public static final int CELL_WIDTH = 32;
	public static final int CELL_HEIGHT = 32;
	private static Board board;
	private static GameDriver driver;
	
	/**
	 * 
	 * @param row
	 * @param column
	 * 
	 * This a constructor for BoardCell where a row and a column of a cell is inputed. 
	 */
	public BoardCell(int row, int column, char initial, DoorDirection dir) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.direction = dir;
		driver = new GameDriver();
		if(direction != DoorDirection.NONE && direction != DoorDirection.NAME) {
			this.isDoor = true;
		}
		else {
			this.isDoor = false;
		}
		board = Board.getInstance();
		driver = GameDriver.getInstance();
		
	}
	
	/**
	 * 
	 * @return
	 * This function checks whether there is a door for the player to go in. 
	 */
	public boolean isDoorway() {
		return isDoor;
	}
	
	/**
	 * 
	 * @return
	 * 
	 * This function gets the direction of the door. 
	 */
	public DoorDirection getDoorDirection() {
		return direction;
	}
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}

	/**
	 * 
	 * @return
	 * 
	 * This function gets the initial direction of the players. 
	 */
	public char getInitial() {
		return initial;
	}
    /**
     * This function returns the row of the board cell.
     * @return
     */
	public int getRow() {
		// TODO Auto-generated method stub
		return row;
	}
    /**
     * This function returns the column of the cell.
     * @return
     */
	public int getColumn() {
		// TODO Auto-generated method stub
		return column;
	}
	/**
	 * This function helps color all the different board cells, the players, and names of the rooms. 
	 * @param g
	 */
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(column*CELL_WIDTH,row*CELL_HEIGHT,CELL_WIDTH, CELL_HEIGHT);
		//g.fillRect(row, column, 1, 1);
		if(initial == 'W') {
			
				g.setColor(Color.yellow);
				g.fillRect(column*CELL_WIDTH,row*CELL_HEIGHT,CELL_WIDTH, CELL_HEIGHT);
				g.setColor(Color.black);
				g.drawRect(column*CELL_WIDTH,row*CELL_HEIGHT,CELL_WIDTH, CELL_HEIGHT);
			
			
		}
		else {
			g.setColor(Color.gray);
			g.fillRect(column*CELL_WIDTH,row*CELL_HEIGHT,CELL_WIDTH, CELL_HEIGHT);
			if(direction != DoorDirection.NONE) {
				if(direction == DoorDirection.UP) {
					g.setColor(Color.blue);
					g.fillRect(column*CELL_WIDTH,row*CELL_HEIGHT,CELL_WIDTH, CELL_HEIGHT/4);
				}
				else if(direction == DoorDirection.RIGHT) {
					g.setColor(Color.blue);
					g.fillRect(column*CELL_WIDTH+(CELL_HEIGHT*3/4),row*CELL_HEIGHT,CELL_WIDTH/4, CELL_HEIGHT);
					
				}
				else if(direction == DoorDirection.DOWN) {
					g.setColor(Color.blue);
					g.fillRect(column*CELL_WIDTH,(row*CELL_HEIGHT)+(CELL_HEIGHT*3/4),CELL_WIDTH, CELL_HEIGHT/4);
				}
				else if(direction == DoorDirection.LEFT) {
					g.setColor(Color.blue);
					g.fillRect(column*CELL_WIDTH,row*CELL_HEIGHT,CELL_WIDTH/4, CELL_HEIGHT);
				}
				else if(direction == DoorDirection.NAME) {
					g.setColor(Color.BLACK);
					g.drawString(board.getLegend().get(initial), column*CELL_WIDTH, row*CELL_HEIGHT);
				}
			}
		}
	}
	
	
	
	

	
	
	
	
    /**
     * This function helps highlight the target boardcells for the human player. 
     * @param g
     */
	public void drawTarget(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(column*CELL_WIDTH,row*CELL_HEIGHT,CELL_WIDTH, CELL_HEIGHT);
	}

}
