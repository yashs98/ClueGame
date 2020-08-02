package clueGame;

import clueGame.Player;

import java.awt.Component;
import java.awt.GridLayout;

import java.io.FileNotFoundException;

import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author Yash Sinha, Daniel Thorne, Calvin Mak
 * This class helps create a custom window for the human player to keep track of which weapons, players, and rooms have been disproven, and 
 * keep track of which room, player, and weapon could be the solution. It has 3 different combo boxes for the player, weapon, and room. It has 
 * checkboxes for each player, room, and weapon. A variable of type Board is created to help initialize those variables.  
 *
 */
public class DetectiveNotes extends JDialog{
	
	private JComboBox<String> personGuess;
	private JComboBox<String> roomGuess;
	private JComboBox<String> weaponGuess;
	private JPanel PeopleCheckBoxes;
	private JPanel RoomCheckBoxes;
	private JPanel WeaponCheckBoxes;
	private JPanel PeopleDropDown;
	private JPanel RoomDropDown;
	private JPanel WeaponDropDown;
	private JLabel Room;
	private JLabel Weapons;
	private JLabel People;
	private JLabel RoomGuess;
	private JLabel WeaponsGuess;
	private JLabel PeopleGuess;
	private Vector<JCheckBox> playerBoxes;
	private Vector<JCheckBox> roomBoxes;
	private Vector<JCheckBox> weaponBoxes;
    private Board theBoard;
	
	/**
	 * This is the constructor to help add different panels.
	 */
	public DetectiveNotes() {
		
		//NullPointerExceptions
		personGuess = new JComboBox<String>();
		roomGuess = new JComboBox<String>();
		weaponGuess = new JComboBox<String>();
		
		theBoard = Board.getInstance();
		theBoard.setConfigFiles("ClueBoard.csv", "ClueRooms.txt", "peopleConfig.txt", "weaponConfig.txt");

		setTitle("Detective Notes");
		setLayout(new GridLayout(3,2));
		setSize(800,500);
		JPanel PeopleCheckBoxes = new JPanel();
		
		//Temporarily Hardcoded layout sizes, will fix during Refactor
		//Constructors
		JPanel RoomCheckBoxes = new JPanel();
		JPanel WeaponCheckBoxes = new JPanel();
		JPanel PeopleDropDown = new JPanel();
		JPanel RoomDropDown = new JPanel();
		JPanel WeaponDropDown = new JPanel();
		JLabel Room = new JLabel("Room");
		JLabel Weapons = new JLabel("Weapon");
		JLabel People = new JLabel("People");
		JLabel RoomGuess = new JLabel("Room Guesses");
		JLabel WeaponsGuess = new JLabel("Weapon Guesses");
		JLabel PeopleGuess = new JLabel("People Guesses");
		
		//Set GridLayouts
		PeopleCheckBoxes.setLayout(new GridLayout(3,2));
		RoomCheckBoxes.setLayout(new GridLayout(6,2));
		WeaponCheckBoxes.setLayout(new GridLayout(3,2));
		PeopleDropDown.setLayout(new GridLayout(2,1));
		RoomDropDown.setLayout(new GridLayout(2,1));
		WeaponDropDown.setLayout(new GridLayout(2,1));
		playerBoxes = peopleCheckMarks();
		roomBoxes = roomCheckMarks();
		weaponBoxes = weaponCheckMarks();
		
		//During Refactoring, will make a separate method
		for (JCheckBox player: playerBoxes) {
			PeopleCheckBoxes.add(player);
		}
		for (JCheckBox roomz: roomBoxes) {
			RoomCheckBoxes.add(roomz);
		}
		for (JCheckBox weaponz: weaponBoxes) {
			WeaponCheckBoxes.add(weaponz);
		}
		PeopleCheckBoxes.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		RoomCheckBoxes.setBorder(new TitledBorder(new EtchedBorder(), "Room"));
		WeaponCheckBoxes.setBorder(new TitledBorder(new EtchedBorder(), "Weapon"));
		PeopleDropDown.add(PeopleGuess);
		PeopleDropDown.add(addPersonGuessBox());
		RoomDropDown.add(RoomGuess);
		RoomDropDown.add(addRoomGuessBox());
		WeaponDropDown.add(WeaponsGuess);
		WeaponDropDown.add(addWeaponGuessBox());
		add(PeopleCheckBoxes);	
		add(PeopleDropDown);
		add(RoomCheckBoxes);
		add(RoomDropDown);
		add(WeaponCheckBoxes);
		add(WeaponDropDown);
	   
		
	}
	
	/**
	 * This helps create People Checkmark boxes for each player. 
	 * @return
	 */
	private Vector<JCheckBox> peopleCheckMarks() {
		Vector<JCheckBox> personBox = new Vector<JCheckBox>();
		Vector<Card> PeopleDeck = theBoard.getPlayerDeck();
		for (Card person: PeopleDeck) {
			JCheckBox newOption = new JCheckBox(person.getCardName());
			personBox.add(newOption);
		}
		return personBox;
		
	}
	
	/**
	 * This helps create Room Checkmark boxes for each room. 
	 * @return
	 */
	private Vector<JCheckBox> roomCheckMarks() {

		Vector<JCheckBox> roomBox = new Vector<JCheckBox>();
		Vector<Card> RoomDeck = theBoard.getRoomDeck();
		for (Card room: RoomDeck) {
			JCheckBox newOption = new JCheckBox(room.getCardName());
			roomBox.add(newOption);
		}
		return roomBox;

		
	}
	
	/**
	 * This helps create Weapon Checkmark boxes for each weapon. 
	 * @return
	 */
	private Vector<JCheckBox> weaponCheckMarks() {

		Vector<JCheckBox> weaponBox = new Vector<JCheckBox>();
		Vector<Card> WeaponDeck = theBoard.getWeaponDeck();
		for (Card weapon: WeaponDeck) {
			JCheckBox newOption = new JCheckBox(weapon.getCardName());
			weaponBox.add(newOption);
		}
		return weaponBox;

		
	}	
	
	
	/**
	 * This helps create a drop down box with all the player names. 
	 * @return
	 */
	private JComboBox<String> addPersonGuessBox()  {
		
		Vector<Card> PeopleDeck = theBoard.getPlayerDeck();
		
		for(Card players: PeopleDeck) {
			personGuess.addItem(players.getCardName());
		}
		
		return personGuess;		
	}
	/**
	 * This helps create a drop down box with all the room names. 
	 * @return
	 */
	private JComboBox<String> addRoomGuessBox()  {

		Vector<Card> RoomDeck = theBoard.getRoomDeck();
		
		for(Card room: RoomDeck) {
			roomGuess.addItem(room.getCardName());
		}
		
		return roomGuess;		
	}
	/**
	 * This helps create a drop down box with all the weapon names. 
	 * @return
	 */
	private JComboBox<String> addWeaponGuessBox()  {

		Vector<Card> WeaponDeck = theBoard.getWeaponDeck();
		
		for(Card weapon: WeaponDeck) {
			weaponGuess.addItem(weapon.getCardName());
		}
		
		return weaponGuess;		
	}
	

}
