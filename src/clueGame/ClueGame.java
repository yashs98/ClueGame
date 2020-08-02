package clueGame;

import java.util.Set;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * 
 * @author Yash Sinha, Daniel Thorne, Calvin Mak
 * This class creates the main window for clue where the board, players, controls, etc will be shown. It has a tab called "File" that allows
 * the user to exit the game or to bring up the detective notes. It uses a static board variable to help draw the board. JMenuBar helps create
 * the "File" tab. The detectiveNotes variable helps create the detective notes dialog window. 
 * 
 */
public class ClueGame extends JFrame{
	
	private static Board board;
	private JMenuBar menuBar;
	private DetectiveNotes detectiveNotes;
    private ClueGame game;
    private static GameDriver driver;
    //private static ControlPanel controlPanel;
	/**
	 * This is the constructor that helps combine all the panels and helps set size of the main window. It also contains the main method
	 * to run the entire java project. 
	 */
	public ClueGame() {
		setSize(new Dimension(1090, 860));
		setTitle("Clue Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = Board.getInstance();
		board.setConfigFiles("ClueBoard.csv", "ClueRooms.txt", "peopleConfig.txt", "weaponConfig.txt");
		board.initialize();
		add(board, BorderLayout.CENTER);
		driver = GameDriver.getInstance();
		ControlPanel controlPanel = new ControlPanel();
		add(controlPanel, BorderLayout.SOUTH);

		JMenuBar menuBar = new JMenuBar();
        HumanMovementListener humanMovement = new HumanMovementListener();
		
		
		Player player = driver.currentPlayer();
		
		
		
        PlayerCardDisplay playerCardDisplay = new PlayerCardDisplay();
        add(playerCardDisplay, BorderLayout.EAST);
        
		JMenuItem closeItem;
		JMenuItem notesItem;
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		notesItem = fileMenu.add("Notes");
		closeItem = fileMenu.add("Exit");
	    menuBar.add(fileMenu);
	    notesItem.addActionListener(new ButtonListener());
	    closeItem.addActionListener(new ExitListener());	
	    board.addMouseListener(humanMovement);
	    
	}
	
	/**
	 * 
	 * @author Yash Sinha, Daniel Thorne, Calvin Mak
	 * This class helps bring up the detective notes dialog window when the option "Notes" is clicked under the "Files" tab.
	 *
	 */
	private class ButtonListener implements ActionListener {
		/**
		 * This function helps bring up the detective notes dialog window when the option "Notes" is clicked under the "Files" tab. 
		 */
		public void actionPerformed(ActionEvent e) {
			detectiveNotes = new DetectiveNotes();
			detectiveNotes.setVisible(true);
		}
	}
	
	/**
	 * 
	 * @author Yash Sinha, Daniel Thorne, Calvin Mak
	 * This class helps exit out of the game when the option "Exit" is clicked under the "Files" tab.
	 *
	 */
	private class ExitListener implements ActionListener{
        /**
         * This function  helps exit out of the game when the option "Exit" is clicked under the "Files" tab.
         */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			
		}
		
	}
	/**
	 * 
	 * @author Yash Sinha, Daniel Thorne, Calvin Mak
	 * This class implements the MouseListener to help the user click on the grid and move the player 
	 * when it's the human player's turn. It also popups an error window when the user clicks on a grid 
	 * that is not one of the highlighted targets. 
	 *
	 */
	private class HumanMovementListener implements MouseListener{
        /**
         * This function is called when a mouse is clicked on the board. It help the user click on the
         * grid and move the playe when it's the human player's turn. It also popups an error window 
         * when the user clicks on a grid that is not one of the highlighted targets. 
         */
		@Override
		public void mouseClicked(MouseEvent e) {
			boolean result = false;
				int column = e.getX()/32;
				int row = e.getY()/32;
				Set<BoardCell> target = board.getTargets();
					
				if((e.MOUSE_CLICKED >= column || e.MOUSE_CLICKED<=column*32) && (e.MOUSE_CLICKED >= row || e.MOUSE_CLICKED<=row*32) && target.contains(board.getCellAt(row, column)) && board.getCellAt(row, column).isDoorway() ==true) {
				
					result = driver.MoveHumanPlayer(row,column);
					AutomaticSuggestionPanel suggestionPanel = new AutomaticSuggestionPanel();
					suggestionPanel.setVisible(true);
					
				}
				else if((e.MOUSE_CLICKED >= column || e.MOUSE_CLICKED<=column*32) && (e.MOUSE_CLICKED >= row || e.MOUSE_CLICKED<=row*32) && target.contains(board.getCellAt(row, column))) {
			    	
			    		result = driver.MoveHumanPlayer(row,column);
					
			    	
			    	
				}
				else  {
					JOptionPane.showMessageDialog(game, "You can't move to that location!","ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
		
	}
    /**
     * This is the main method to start the game, and to bring up the main game window. 
     * @param args
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClueGame clueGame = new ClueGame();
		JOptionPane.showMessageDialog(clueGame, "You are " + board.getHumanPlayer().getPlayerName() + ", press Next Player to begin play.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		clueGame.setVisible(true);
		
		
		while(true) {
			System.out.println("");
			if(driver.isEnded()) {
				System.exit(0);
			}
			
		}
		
            
	}
}
