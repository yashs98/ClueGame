package experiment;

/**
 * 
 * @author Yash Sinha and Daniel Thorne
 *
 *The class BoardCell is the individual cell of the board. It only has two variables that represent the row 
 *and the column of the cell. The class also contains a constructor. 
 */
public class BoardCell {
	
	private int row;
	private int column;
	/**
	 * 
	 * @param row
	 * @param column
	 * 
	 * This a constructor for BoardCell where a row and a column of a cell is inputted. 
	 */
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

}
