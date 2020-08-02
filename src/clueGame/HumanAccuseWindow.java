package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HumanAccuseWindow extends JDialog{
	
	private JComboBox<String> accusePerson;
	private JComboBox<String> accuseWeapon;
	private JComboBox<String> accuseRoom; 
	Vector<Card> people;
	Vector<Card> weapons;
	Vector<Card> rooms;
	private static Board board;
	private JButton submit;
	private JButton cancel;
	private static GameDriver driver;
	Card possiblePlayerCard;
	Card possibleWeaponCard;
	Card possibleRoomCard;
   

	
	
	public HumanAccuseWindow() {
		
		setTitle("Make a Guess");
		setLayout(new GridLayout(2,1));
		setSize(450, 200);
		accusePerson = new JComboBox<String>();
		accuseWeapon = new JComboBox<String>();
		board = Board.getInstance();
		driver = GameDriver.getInstance();
		accuseRoom = new JComboBox<String>();
		JLabel labelPerson = new JLabel("Person");
		JLabel labelRoom = new JLabel("Room");
		JLabel labelWeapon = new JLabel("Weapon");
		JPanel accusePersonPanel = new JPanel();
		JPanel accuseWeaponPanel = new JPanel();
		JPanel accuseRoomPanel = new JPanel();
		JPanel accusationPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		accuseRoomPanel.setLayout(new GridLayout(3,5));
		accusePersonPanel.setLayout(new GridLayout(3,5));
		accuseWeaponPanel.setLayout(new GridLayout(3,5));
		accusePersonPanel.add(labelPerson);
		accuseRoomPanel.add(labelRoom);
		accuseWeaponPanel.add(labelWeapon);
		people = board.getPlayerDeck();
		weapons = board.getWeaponDeck();
		rooms = board.getRoomDeck();
	
		accusePersonPanel.add(allowAccusePlayer());
		accuseWeaponPanel.add(allowAccuseWeapon());
		accuseRoomPanel.add(allowAccuseRoom());
		
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		buttonsPanel.add(submit);
		buttonsPanel.add(cancel);
		cancel.addActionListener(new CancelAccuse());
		submit.addActionListener(new SubmitAccuse());
		accusationPanel.setLayout(new GridLayout(0,3));
		accusationPanel.add(accusePersonPanel);
		accusationPanel.add(accuseWeaponPanel);
		accusationPanel.add(accuseRoomPanel);
		add(accusationPanel);
		add(buttonsPanel);
		
		
		
	}
	
	private JComboBox<String> allowAccusePlayer(){
		Vector<Card> playerCards =  board.getPlayerDeck();
		for(Card card: playerCards) {
			accusePerson.addItem(card.getCardName());
		}
		
		return accusePerson;
	}
	
	private JComboBox<String> allowAccuseWeapon(){
		 Vector<Card> weaponCards =  board.getWeaponDeck();
		for(Card card: weaponCards) {
			accuseWeapon.addItem(card.getCardName());
		}
		return accuseWeapon;
	}
	
	private JComboBox<String> allowAccuseRoom(){
		 Vector<Card> roomCards =  board.getRoomDeck();
		 for(Card card: roomCards) {
			 accuseRoom.addItem(card.getCardName());
		 }
		return accuseRoom;
	}
	
	private class CancelAccuse implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			
		}
		
		
	}
	
	private class SubmitAccuse implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String possiblePerson = (String) accusePerson.getSelectedItem();
			String possibleWeapon = (String) accuseWeapon.getSelectedItem();
			String possibleRoom = (String) accuseRoom.getSelectedItem();
			HumanPlayer human = (HumanPlayer) driver.currentPlayer();
			for(Card cardPerson: people) {
				if(cardPerson.getCardName().equals(possiblePerson)) {
					possiblePlayerCard = cardPerson;
				}
			}
			
			for(Card cardRoom: rooms) {
				if(cardRoom.getCardName().equals(possibleRoom)) {
					possibleRoomCard = cardRoom;
				}
			}
			
			for(Card cardWeapon: weapons) {
				if(cardWeapon.getCardName().equals(possibleWeapon)) {
					possibleWeaponCard = cardWeapon;
				}
			}
			
			Solution possibleSolution = new Solution(possiblePlayerCard, possibleRoomCard, possibleWeaponCard);
			
		if(board.checkAccusation(possibleSolution) == false) {
			JOptionPane.showMessageDialog(board, "You are out!", "You got it wrong!", JOptionPane.OK_OPTION);
			driver.removePlayer(board.getHumanPlayer());
			dispose();
		}
		else {
			JOptionPane.showMessageDialog(board, "You have solved the mystery!", "You won!", JOptionPane.OK_OPTION);
			JOptionPane.showMessageDialog(board,"The solution was " + possiblePlayerCard.getCardName() + " in the " + possibleRoomCard.getCardName() + " with the " + possibleWeaponCard.getCardName() + ".", human.getPlayerName() + " won!", JOptionPane.OK_OPTION);
			driver.setEnded(true);
			dispose();
		
			
		}
			
			
			
		}
		
	}
	

}
