package clueGame;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AutomaticSuggestionPanel extends JDialog{
	private JComboBox<String> accusePlayer;
	private JComboBox<String> accuseWeapon;
	private static Board board;
	private JButton submitButton;
	private JButton cancelButton;
	private JLabel currentRoom;
	private static GameDriver driver;
	private Vector<Card> players;
	private Vector<Card> weapons;
	private Vector<Card> rooms;
	private Card possiblePlayerCardTwo;
	private Card possibleWeaponCardTwo;
	private Card possibleRoomCardTwo;
	private Map <Character, String> legend;
	private HumanPlayer human;
	private ControlPanel control;
	public AutomaticSuggestionPanel() {
		setTitle("Make a Guess");
		setLayout(new GridLayout(5,1));
		setSize(350, 300);
		accusePlayer = new JComboBox<String>();
		accuseWeapon = new JComboBox<String>();
		board = Board.getInstance();
		driver = GameDriver.getInstance();
		JPanel accusePlayer = new JPanel();
		JPanel accuseWeapon = new JPanel();
		JPanel accuseRoom = new JPanel();
		JLabel playerLabel = new JLabel("Person: ");
		Font font = new Font("TimesRoman", Font.PLAIN, 20);
		
		playerLabel.setFont(font);
		JLabel weaponLabel = new JLabel("Weapon: ");
		weaponLabel.setFont(font);
		JLabel roomLabel = new JLabel("Room: ");
		roomLabel.setFont(font);
		accusePlayer.add(playerLabel);
		accusePlayer.add(allowAccusePlayer());
		accuseWeapon.add(weaponLabel);
		accuseWeapon.add(allowAccuseWeapon());
		accuseRoom.add(roomLabel);
		human = (HumanPlayer) driver.currentPlayer();
		
		control = new ControlPanel();
		legend = board.getLegend();
		
		String current = legend.get(human.currentCell().getInitial());
	    
	    currentRoom = new JLabel(current);
	    currentRoom.setFont(font);
		accuseRoom.add(currentRoom);
		players = board.getPlayerDeck();
		weapons = board.getWeaponDeck();
		rooms = board.getRoomDeck();
		
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");
		submitButton.addActionListener(new SubmitAccusation());
		cancelButton.addActionListener(new CancelAccusation());
		
		add(accusePlayer);
		add(accuseRoom);
		add(accuseWeapon);
		add(submitButton);
		add(cancelButton);
	}
	
	private JComboBox<String> allowAccusePlayer(){
		
		Vector<Card> playerCards =  board.getPlayerDeck();
		for(Card card: playerCards) {
			accusePlayer.addItem(card.getCardName());
		}
		accusePlayer.setSize(getMaximumSize());
		
		return accusePlayer;
	}
	
	private JComboBox<String> allowAccuseWeapon(){
		 Vector<Card> weaponCards =  board.getWeaponDeck();
		for(Card card: weaponCards) {
			accuseWeapon.addItem(card.getCardName());
			
		}
		accuseWeapon.setSize(getMaximumSize());
		return accuseWeapon;
	}
	
	
	
	private class CancelAccusation implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			
		}
		
		
	}
	
	private class SubmitAccusation implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String possiblePerson = (String) accusePlayer.getSelectedItem();
			String possibleWeapon = (String) accuseWeapon.getSelectedItem();
			String possibleRoom = currentRoom.getText();
			
			for(Card cardPerson: players) {
				if(cardPerson.getCardName().equals(possiblePerson)) {
					possiblePlayerCardTwo = cardPerson;
				}
			}
			
			for(Card cardRoom: rooms) {
				if(cardRoom.getCardName().equals(possibleRoom)) {
					possibleRoomCardTwo = cardRoom;
				}
			}
			
			for(Card cardWeapon: weapons) {
				if(cardWeapon.getCardName().equals(possibleWeapon)) {
					possibleWeaponCardTwo = cardWeapon;
				}
			}
			
			
			
			Solution possibleSolution = new Solution(possiblePlayerCardTwo, possibleRoomCardTwo, possibleWeaponCardTwo);
			
			Card card = board.handleSuggestion(board.getHumanPlayer(), possibleSolution);
			
			control.setGuessResponse(card.getCardName());
			dispose();
		
		
			
			
			
		}
		
	}
	

}
