package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.Vector;

import javax.swing.JDialog;

/**
 * 
 * @author Yash Sinha, Daniel Thorne, Calvin Mak
 * This class is to create the human player.
 * 
 */

public class HumanPlayer extends Player{
    
    private static Board board;
    private DisproveSuggestionBox disproveSuggestion;
    private Card disproveCard;
    private GameDriver driver;
    //private ControlPanel cPanel;
	/**
	 * 
	 * @param playerName
	 * @param row
	 * @param column
	 * @param color
	 * This is a constructor for the human player. 
	 * 
	 */
	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
		//cPanel = ControlPanel.getInstance();
		board = Board.getInstance();
		driver = GameDriver.getInstance();
		
	}
	/**
	 * This helps set the location of the human player. 
	 */
    @Override
	public void setLocation(BoardCell cell) {
		row = cell.getRow();
		column = cell.getColumn();
	}
	
    public BoardCell currentCell() {
    	return board.getCellAt(row, column);
    }
    
    public Card disproveSuggestion(Solution suggestion) {

    	/*Vector<Card> person = getHand();
    	
    	for(Card hand: person) {
    	   
    		if(suggestion.person == hand || suggestion.room == hand || suggestion.weapon == hand) {
    			DisproveSuggestionBox box = new DisproveSuggestionBox();
    			box.setVisible(true);
    			return box.getDisproveCard();
    		}
    	}
    	
    		return null;
    	
		*/

    	JDialog panel = new JDialog();
    	disproveSuggestion = new DisproveSuggestionBox();
    	disproveSuggestion.setVisible(true);
    	panel = disproveSuggestion;
    	return disproveSuggestion.getDisproveCard();

	}
	
    private class DisproveSuggestion implements ActionListener{

    	@Override
    	public void actionPerformed(ActionEvent arg0) {
    		
    		
    	}
    	
    }
	

}


