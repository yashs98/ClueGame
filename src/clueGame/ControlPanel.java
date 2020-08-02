package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


/**
 * 
 * @author Yash Sinha, Daniel Thorne, Calvin Mak
 * This class creates a window with panels like making an accusation, end the turn of a player, creating a grid to show whose turn it is, 
 * a panel to show the player's guess, shows the die roll, and showing the result of the guess made by the player. 
 *
 */
public class ControlPanel extends JPanel{
    
	//private static ControlPanel cPanel = new ControlPanel();
	private static JPanel turnPanel;
	private static JPanel Buffer;
	private static JPanel Buffer2;
	private static JPanel diePanel;
	private static JPanel guessPanel;
	private static JPanel nextPlayerButtonPanel;
	private static JPanel makeAccusationButton;
	private static JPanel guessResult;
	private static JButton dieRollButton;
	private static JButton nextPlayer;
	private static JButton makeAccusation;
	private static JTextField suggestDisplay;
	private static JTextField control;
	private static JTextField dieDisplay;
	private static JTextField displayResponse;
	private static JLabel nameLabel;
	private static GameDriver driver;
	private static Board board;
	private HumanAccuseWindow accuseWindow;
	public ControlPanel() {
		super();
		
		driver = GameDriver.getInstance();
		//this.setOpaque(true);
		
		//this.setBackground(new Color(93, 97, 104));
		setLayout(new GridLayout(2,5));
		
		//turnPanel
		turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(1,1));
		nameLabel = new JLabel("Whose turn?");
		control = new JTextField(8);
		control.setText(driver.currentPlayer().getPlayerName());
		turnPanel.add(nameLabel);
		turnPanel.add(control);
		
		//diePanel
		diePanel = new JPanel();
		diePanel.setLayout(new GridLayout(1,4));
		dieRollButton = new JButton("Roll Dice");
		Buffer = new JPanel();
		Buffer.setSize(0,30);
		Buffer2 = new JPanel();
		Buffer2.setSize(0,30);
		dieDisplay = new JTextField(10);
		dieDisplay.setHorizontalAlignment(JTextField.TRAILING);
		diePanel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		diePanel.add(dieRollButton);
		diePanel.add(Buffer);
		diePanel.add(Buffer2);
		diePanel.add(dieDisplay);
		
		//createSuggestionPanel
		guessPanel = new JPanel();
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		JTextField suggestDisplay = new JTextField(28);
		guessPanel.add(suggestDisplay);
		
		
		//nextPlayerButtonPanel
		nextPlayerButtonPanel = new JPanel();
		nextPlayer = new JButton("Next Player");
		nextPlayer.addActionListener(new nextPlayerListener());
		nextPlayerButtonPanel.add(nextPlayer);
		nextPlayerButtonPanel.setLayout(new GridLayout(1,1));
		
		//makeAccusationPanel
		makeAccusationButton = new JPanel();
		makeAccusation = new JButton("Make an Accusation");
		makeAccusation.addActionListener(new AllowHumanAccuse());
		makeAccusationButton.add(makeAccusation);
		makeAccusationButton.setLayout(new GridLayout(1,2));
		
		//guessResult Panel
		guessResult = new JPanel();
		displayResponse = new JTextField(28);
		guessResult.add(displayResponse);
		guessResult.setBorder(new TitledBorder(new EtchedBorder(), "Guess Response"));
		//turnPanel.setBackground(new Color(93, 97, 104));
		add(turnPanel, BorderLayout.WEST);
		add(nextPlayerButtonPanel, BorderLayout.CENTER);
		add(makeAccusationButton, BorderLayout.EAST);
		add(diePanel, BorderLayout.WEST);
		add(guessPanel, BorderLayout.CENTER);
		
		add(guessResult, BorderLayout.EAST);
		dieRollButton.addActionListener(new RollDiceListener());
		board = Board.getInstance();
		
		
	}
	
	/*public static ControlPanel getInstance() {
		return cPanel;
	}*/
	
	public static void setSuggestDisplay(String display) {
		suggestDisplay.setText(display);
	}

	public void setGuessResponse(String name) {
		displayResponse.setText(name);
	}
	/**
	 * Updates the die value
	 * @return
	 */
	public void updateDiePanel() {
		diePanel.removeAll();
		dieRollButton.setText("Roll Dice");
		dieDisplay.setText(Integer.toString(driver.RollDice()));
		diePanel.add(dieRollButton);
		diePanel.add(Buffer);
		diePanel.add(Buffer2);
		diePanel.add(dieDisplay);
		diePanel.repaint();
		return;
	}
	
	/**
	 * Updates the die value
	 * @return
	 */
	public void updateDie() {
		diePanel.removeAll();
		dieRollButton.setText("Roll Dice");
		dieDisplay.setText(Integer.toString(driver.getRoll()));
		diePanel.add(dieRollButton);
		diePanel.add(Buffer);
		diePanel.add(Buffer2);
		diePanel.add(dieDisplay);
		diePanel.repaint();
		return;
	}
	
	/**
	 * This function creates a button that lets the player make an accusation.

	 * @return
	 */
	
	public void clearDiePanel() {
		diePanel.removeAll();
		dieRollButton.setText("Roll Dice");
		dieDisplay.setText("");
		diePanel.add(dieRollButton);
		diePanel.add(Buffer);
		diePanel.add(Buffer2);
		diePanel.add(dieDisplay);
		return;
	}
	
	/**
	 * Update PlayerTurn
	 * @return
	 */
	public void updatePlayerTurn() {
		turnPanel.remove(control);
		control.setText(driver.currentPlayer().getPlayerName());
		turnPanel.add(control);
		turnPanel.repaint();
	}
	
	/**
	 * 
	 * Updates the Guess Box
	 * @param guess
	 */
	public void updateGuessBox(Solution guess) {
		suggestDisplay.setText(guess.getPerson() + ", " + guess.getRoom() + ", " + guess.getWeapon());
		return;
	}
	
	
	/**
	 * 
	 * Updates the Guess Response Box
	 * @param response
	 */
	public void updateGuessRBox(String response) {
		displayResponse.setText(response);
	}
	/**
	 * 
	 * This class implements ActionListener to help roll the dice. 
	 *
	 */
	private class RollDiceListener implements ActionListener{
        /**
         * This function helps roll the die and helps update the panel that displays the die number.
         */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(driver.currentPlayer() instanceof HumanPlayer) {
				updateDiePanel();
			};
			//System.out.println("Working");
		}
		
	}
	
	/**
	 * 
	 * @author Yash Sinha, Daniel Thorne, Calvin Mak
	 * This class implements ActionListener to help change the player's turn when the next player button is 
	 * pressed. 
	 *
	 */
	private class nextPlayerListener implements ActionListener {
        
		/**
		 * This function helps update the player's turn when the next player button is pressed. 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			driver.NextPlayerTurn();
			updatePlayerTurn();
			clearDiePanel();
		   if(driver.currentPlayer() instanceof ComputerPlayer) {
			   driver.doAccusation();
			   driver.MoveComputerPlayer();
			   updateDie();
		   }
		}
		
	}
	private class AllowHumanAccuse implements ActionListener{
        
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(driver.currentPlayer() instanceof HumanPlayer) {
				accuseWindow = new HumanAccuseWindow();
				accuseWindow.setVisible(true);
			}
			
		}
		
	}
	
	/**
	 * This is the main method that helps instantiate and show the window with the panels. 
	 * @param args
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setOpaque(true);
		Graphics2D g2D = (Graphics2D) g;
		int width = getWidth();
		int height = getHeight();
		Color ForeColor = new Color(93, 97, 104);
		Color BColor = Color.black;
		GradientPaint gP = new GradientPaint(0,0, ForeColor, 0, height, BColor);
		g2D.setPaint(gP);
		g2D.fillRect(0, 0, width, height);
	}

}
