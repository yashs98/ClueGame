package clueGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author Yash Sinha, Daniel Thorne, Calvin Mak
 * This class helps create a panel that shows the cards that are on the human player's deck. It utilizes a board
 * variable to get the player's hand and to actually show the cards and a humanHand contains a vector of 
 * the player's cards.
 *
 */
public class PlayerCardDisplay extends JPanel{
	private Board board;
	private Vector<Card>humanHand;
	
	/**
	 * This is a constructor to help initialize variables and add textboxes and titles. 
	 */
	public PlayerCardDisplay() {
		board = Board.getInstance();				//Because its a Singleton
		
		//Loads in config files
		board.setConfigFiles("ClueBoard.csv", "ClueRooms.txt", "peopleConfig.txt", "weaponConfig.txt");
		//Generates Player hand
		humanHand = (Vector<Card>) board.getHumanPlayer().getHand().clone();
		JPanel finalPanel = new JPanel();
		finalPanel.setLayout(new GridLayout(3,0));
		JPanel showPlayerCard = showPeopleCard();
		JPanel showRoomCard = showRoomCards();
		JPanel showWeaponCard = showWeaponCards();
		
		finalPanel.setBorder(new TitledBorder(new EtchedBorder(), "My Cards "));
		finalPanel.add(showPlayerCard);
		finalPanel.add(showRoomCard);
		finalPanel.add(showWeaponCard);
		
		add(finalPanel);
		
		
		
	}
	
	/**
	 * This function helps show the human player's weapon card. 
	 * @return
	 */
	private JPanel showWeaponCards() {
	
		
		JPanel panel = new JPanel();
		JTextField displayWeapon = new JTextField(20);
		Font bigFont = displayWeapon.getFont().deriveFont(Font.PLAIN, 15f);
		String weaponList = "";
		displayWeapon.setFont(bigFont);
		for(Card card: humanHand) {
			if(card.getCardType()==CardType.WEAPON) {
				if(weaponList.isEmpty()) {
					weaponList += card.getCardName();
				}
				else {
					weaponList += ", " + card.getCardName();
				}
			}
		}
		displayWeapon.setText(weaponList);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons "));
		panel.add(displayWeapon);
		return panel;
		
	}
	
	/**
	 * This function helps show the human player's room card. 
	 * @return
	 */
	private JPanel showRoomCards() {
		
		
		JPanel panel = new JPanel();
		JTextField displayRoom = new JTextField(20);
		Font bigFont = displayRoom.getFont().deriveFont(Font.PLAIN, 15f);
		
		String roomList = "";
		displayRoom.setFont(bigFont);
		
		for(Card card: humanHand) {
			if(card.getCardType()==CardType.ROOM) {
				if(roomList.isEmpty()) {
					roomList += card.getCardName();
				}
				else {
					roomList += ", " + card.getCardName();
				}
			}
			
		}
		
		displayRoom.setText(roomList);
		setLayout(new GridLayout(3,0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		panel.add(displayRoom);
		
		return panel;
	}
	
	/**
	 * This function helps show the human player's player card. 
	 * @return
	 */
	private JPanel showPeopleCard() {
		
		JPanel panel = new JPanel();
		JTextField displayPeople = new JTextField(20);
		Font bigFont = displayPeople.getFont().deriveFont(Font.PLAIN, 15f);
		String peopleList = "";
		displayPeople.setFont(bigFont);
		for(Card card: humanHand) {
			if(card.getCardType()==CardType.PERSON) {
				if(peopleList.isEmpty()) {
					peopleList += card.getCardName();
				}
				else {
					peopleList += ", " + card.getCardName();
				}
			}
		}
		displayPeople.setText(peopleList);
		setLayout(new GridLayout(3,0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		panel.add(displayPeople);
		return panel;
	}

}
