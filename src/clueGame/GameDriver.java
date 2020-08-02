package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * 
 * 
 * @author Yash Sinha, Daniel Thorne, Calvin Mak
 * Controls Gameplay including turn change, die rolls, and cards
 *
 */

public class GameDriver {

	private static Board board;
	private Player[] playerList;
	private int currentPlayerPoint;
	private static GameDriver driver = new GameDriver();
	private int currentRoll;
	
	private boolean rolled;
	private boolean moved;
	private boolean ended;
	
	/**
	 * This helps get the instance of GameDriver. 
	 * @return
	 */
	public static GameDriver getInstance() {
		return driver;
	}
	/**
	 * This is a constructor for GameDriver. 
	 */
	public GameDriver() {
		board = Board.getInstance();
		playerList = board.getPlayers();
		currentPlayerPoint = 0;
		currentRoll = 0;
		rolled = false; 
		moved = false; 
		ended = false;
	}
	
	/**
	 * Goes to the next player's turn
	 */
	public void NextPlayerTurn() {
		if(rolled && moved) {
			currentPlayerPoint = (currentPlayerPoint + 1) % playerList.length;
			rolled = false;
			moved = false;
		}
	}
	
	
	/**
	 * Rolls the dice and returns the value on the die
	 * @return roll
	 */
	public int RollDice() {
		int roll = 0;
		if(!rolled) {
			roll = new Random().nextInt(6) + 1;
			board.calcTargets(playerList[currentPlayerPoint].getRow(), playerList[currentPlayerPoint].getColumn(), roll);
			if(playerList[currentPlayerPoint] instanceof HumanPlayer) {
				board.repaint();
			}
			rolled = true;
			currentRoll = roll;
			return roll;
		}	
		return currentRoll;
	}
    /**
     * This function helps the human player move depending on the highlighted targets and whether the user
     * clicks on one of those highlighted targets. It also determines whether the human player has already 
     * moved or not. 
     * @param row
     * @param column
     * @return
     */
	public boolean MoveHumanPlayer(int row, int column) {
		if(!moved) {
			if(playerList[currentPlayerPoint] instanceof HumanPlayer) {
				if(board.getTargets().contains(board.getCellAt(row, column))) {
					playerList[currentPlayerPoint].setLocation(board.getCellAt(row, column));
					board.setTargets(null);
					moved = true;
					board.repaint();
					return true;
				}
				else {
					return false;
				}
			}
		}
	return true;
	}
	/**
	 * This function helps the computer players to move and decides whether they have already moved or not. 
	 * @return
	 */
	public boolean MoveComputerPlayer() {
		if(!moved) {
			if(playerList[currentPlayerPoint] instanceof ComputerPlayer) {
				ComputerPlayer computer = (ComputerPlayer) currentPlayer();
			    board.calcTargets(computer.getRow(), computer.getColumn(), driver.RollDice());
			    rolled = true;
				Set<BoardCell> target = board.getTargets();
				BoardCell newCell = computer.pickLocation(target);
			    computer.setLocation(newCell);
			    board.setTargets(null);
			    moved = true;
			    board.repaint();
			    System.out.println(board.getLegend().get(newCell.getInitial()));
			    if(newCell.getInitial() != 'W') {
			    	board.handleSuggestion(playerList[currentPlayerPoint] ,((ComputerPlayer) playerList[currentPlayerPoint]).createSuggestion(board.getLegend().get(newCell.getInitial())));
			    }
			    return true;
		}
		else {
			return false;
		}
		}
		return true;
	}

	public boolean isMoved() {
		return moved;
	}
	/**
	 * Getter for current player
	 * @return
	 */
	public Player currentPlayer() {
		return playerList[currentPlayerPoint];
	}
	
	/**
	 * 
	 * Getter for current Roll
	 * @return currentRoll
	 * 
	 */
	public int getRoll() {
		return currentRoll;
	}
	public void doAccusation() {
		Solution endingSolution = board.checkPlayerAccusation(playerList[currentPlayerPoint]);
		if (endingSolution != null) {
			JOptionPane.showMessageDialog(board,"The solution was " + endingSolution.person.getCardName() + " in the " + endingSolution.room.getCardName() + " with the " + endingSolution.weapon.getCardName() + ".", playerList[currentPlayerPoint].getPlayerName() + " won!", JOptionPane.OK_OPTION);
			ended = true;
		}
		
		
	}
	public boolean isEnded() {
		return ended;
	}
	
	public void removePlayer(Player player) {
		ended = true;
	}
	public void setEnded(boolean ended) {
		this.ended = ended;
	}
	
	
	

}
