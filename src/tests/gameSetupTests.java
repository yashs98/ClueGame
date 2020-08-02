package tests;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import static org.junit.Assert.*;

import java.util.Vector;

/**
 * 
 * @author Yash Sinha, Daniel Thorne, Calvin Mak
 * This class tests the code to see whether the players are being generated, the deck of cards are being generated, and whether they are being 
 * dealt to the players as per the rule. The class contains a variable for how many player cards are in the deck, there is a constant for the 
 * maximum deck size of the game, there is a static variable of type Board to help test functions in the Board class. 
 *
 */
public class gameSetupTests {
    private int playerDeckSize = 6;
	public static final int DECK_SIZE = 21;
	private static Board board;
	
	/**
	 * This function sets up the variables necessary to be able to test the functions needed, and it helps bring in files that helps load the 
	 * board, players, rooms, and weapons. 
	 */
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueBoard.csv", "ClueRooms.txt", "peopleConfig.txt", "weaponConfig.txt");
		board.initialize();
		
		
	}
	
	/**
	 * This function tests whether the players are being loaded properly on the locations specified, based on the file peopleConfig.txt.
	 */
	@Test
	public void loadPeople() {
		Vector<Card> testSet = board.getPlayerDeck();
		assertEquals(playerDeckSize, testSet.size());
		Player[] testPlayers = board.getPlayers();
		
		assertEquals(testPlayers[0].getPlayerName(), "A");
		assertEquals(testPlayers[0].getRow(), 0);
		assertEquals(testPlayers[0].getColumn(), 3);
		assertEquals(testPlayers[0].getColor(), board.convertColor("red"));
		
		assertEquals(testPlayers[2].getPlayerName(), "C");
		assertEquals(testPlayers[2].getRow(), 20);
		assertEquals(testPlayers[2].getColumn(), 7);
		assertEquals(testPlayers[2].getColor(), board.convertColor("green"));
		
		assertEquals(testPlayers[5].getPlayerName(), "F");
		assertEquals(testPlayers[5].getRow(), 15);
		assertEquals(testPlayers[5].getColumn(), 23);
		assertEquals(testPlayers[5].getColor(), board.convertColor("orange"));
	}
	
	/**
	 * This function tests whether the the deck of cards are being generated properly and has appropriate amount of room, player, and weapon
	 * cards.
	 */
	@Test
	public void createDeckOfCards() {
		Vector<Card> testDeck = board.getDeck();
		assertEquals(testDeck.size(), DECK_SIZE);
		int peopleCounter = 0; 
		int weaponCounter = 0;
		int roomCounter = 0;
		boolean hasD = false;
		boolean hasAttackDog = false;
		boolean hasKitchen = false;
		for(Card card: testDeck) {
			if(card.getCardType() == CardType.PERSON) {
				peopleCounter++;
			}
			else if(card.getCardType() == CardType.WEAPON) {
				weaponCounter++;
			}
			else if(card.getCardType() == CardType.ROOM) {
				roomCounter++;
			}
			if(card.getCardName().equals("D")) {
				hasD = true;
			}
			if(card.getCardName().equals("Attack Dog")) {
				hasAttackDog = true;
			}
			if(card.getCardName().equals("Kitchen")) {
				hasKitchen = true;
			}
		}
        assertEquals(peopleCounter, 6);
        assertEquals(weaponCounter, 6);
        assertEquals(roomCounter, 9);
        assertTrue(hasD);
        assertTrue(hasAttackDog);
        assertTrue(hasKitchen);
	}
	
	

    /**
     * This function Tests whether the players are being dealt cards as per the rule of Clue. 
     */
	@Test
	public void dealCards() {
		
		Player[] Players = board.getPlayers();
		//int numPlayers = board.getNumPlayers();
		Vector<Card> Deck = board.getDeck();
		
		//Checks all cards are dealt
		//Compares size of deck with total Card count of all the decks
		
		assertEquals(board.sumOfDecks(),board.getDeck().size());
		
		//Tests for players' Card Count
		//Compares one player deck size to another. They should have the same number, +- 1 Card.
		assertTrue(board.compareDeckCount(Players[0],Players[1]));
		assertTrue(board.compareDeckCount(Players[0],Players[2]));
		
		//Check that a player has the correct number of cards.
		assertTrue(board.checkDeckCount(Players[0]));
		assertTrue(board.checkDeckCount(Players[1]));
		
		//Checks for duplicates
		//Takes a card from the deck and loops through each player counting the amount of times the card has been found.
		assertEquals(board.checkCard(Deck.get(0)),1);
		assertEquals(board.checkCard(Deck.get(5)),1);
		assertEquals(board.checkCard(Deck.get(8)),1);
	}
	
	

}
