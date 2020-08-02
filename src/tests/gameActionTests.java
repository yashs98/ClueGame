package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.DoorDirection;

import clueGame.Solution;

import clueGame.Player;
import clueGame.Solution;

/**
 * 
 * @author Yash Sinha and Daniel Thorne and Calvin Mak
 * Tests for game actions
 * 
 */

public class gameActionTests {
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueBoard.csv", "ClueRooms.txt", "peopleConfig.txt", "weaponConfig.txt");
		board.initialize();
	}
	
	@Test
	public void selectTargetComputer() {
		ComputerPlayer testPlayer = new ComputerPlayer("test", 0, 0, board.convertColor("orange"));
		Set<BoardCell> testSet = new HashSet<BoardCell>();
		//adds 5 different board cells as targets
		testSet.add(new BoardCell(1, 0, 'w', DoorDirection.NONE));
		testSet.add(new BoardCell(2, 0, 'w', DoorDirection.NONE));
		testSet.add(new BoardCell(3, 0, 'w', DoorDirection.NONE));
		testSet.add(new BoardCell(4, 0, 'w', DoorDirection.NONE));
		testSet.add(new BoardCell(5, 0, 'w', DoorDirection.NONE));
		//sets up booleans to check randomness
		boolean visited_1 = false;
		boolean visited_2 = false;
		boolean visited_3 = false;
		boolean visited_4 = false;
		boolean visited_5 = false;
		boolean visited_room = false;
		//checks randomness of no room visited
		for(int i = 0; i<100; i++ ) {
			BoardCell returner = testPlayer.pickLocation(testSet);
			if(returner.getRow() == 1) {
				visited_1 = true;
			}
			else if(returner.getRow() == 2) {
				visited_2 = true;
			}
			else if(returner.getRow() == 3) {
				visited_3 = true;
			}
			else if(returner.getRow() == 4) {
				visited_4 = true;
			}
			else if(returner.getRow() == 5) {
				visited_5 = true;
			}
		}
		//verifies randomness
		assertTrue(visited_1);
		assertTrue(visited_2);
		assertTrue(visited_3);
		assertTrue(visited_4);
		assertTrue(visited_5);
		//adds room target and makes sure it selects that cell
		BoardCell room = new BoardCell(6, 0, 'k', DoorDirection.UP);
		testSet.add(room);
		assertEquals(testPlayer.pickLocation(testSet),room);
		//checks again that selection is random since the last room was visited
		for(int i = 0; i<100; i++ ) {
			BoardCell returner = testPlayer.pickLocation(testSet);
			if(returner.getRow() == 1) {
				visited_1 = true;
			}
			else if(returner.getRow() == 2) {
				visited_2 = true;
			}
			else if(returner.getRow() == 3) {
				visited_3 = true;
			}
			else if(returner.getRow() == 4) {
				visited_4 = true;
			}
			else if(returner.getRow() == 5) {
				visited_5 = true;
			}
			else if(returner.getRow() == 6) {
				visited_room = true;
			}
		}
		//verifies randomness
		assertTrue(visited_1);
		assertTrue(visited_2);
		assertTrue(visited_3);
		assertTrue(visited_4);
		assertTrue(visited_5);
		assertTrue(visited_room);
	}
	
	
	@Test
	public void checkAccusation() {
		
	    Card tempPeopleSolutionCard = new Card("person", CardType.PERSON);
	    Card tempWeaponSolutionCard = new Card("weapon", CardType.WEAPON);
	    Card tempRoomSolutionCard = new Card("room", CardType.ROOM);
	    
	    Solution tempSolution = new Solution(tempPeopleSolutionCard, tempRoomSolutionCard, tempWeaponSolutionCard);
	    board.setSolution(tempSolution);
	    
	    Card wrongPerson = new Card("wrongperson", CardType.PERSON);
	    Card wrongWeapon = new Card("wrongweapon", CardType.WEAPON);
	    Card wrongRoom = new Card("wrongroom", CardType.ROOM);
	   
		// Checks to see that checkAccusation returns true when all three cards are the right solutions.
		assertEquals(board.checkAccusation(tempSolution),true);
		
		
		
		Solution wrongAccusation = new Solution(wrongPerson, tempRoomSolutionCard, tempWeaponSolutionCard);
		
		//Checks to see that checkAccusation returns false when the wrong person is accused.
		assertEquals(board.isSolutionPeopleCard(wrongPerson), false);
		assertTrue(board.isSolutionRoomCard(tempRoomSolutionCard));
		assertTrue(board.isSolutionWeaponCard(tempWeaponSolutionCard));
		assertEquals(board.checkAccusation(wrongAccusation), false);
		
		
		Solution wrongAccusation2 = new Solution(tempPeopleSolutionCard, tempRoomSolutionCard, wrongWeapon);
		
		
		//Checks to see that checkAccusation returns false when the wrong weapon is accused.
	    assertTrue(board.isSolutionPeopleCard(tempPeopleSolutionCard));
		assertTrue(board.isSolutionRoomCard(tempRoomSolutionCard));
		assertEquals(board.isSolutionWeaponCard(wrongWeapon), false);
		assertEquals(board.checkAccusation(wrongAccusation2), false);
		
        Solution wrongAccusation3 = new Solution(tempPeopleSolutionCard, wrongRoom, tempWeaponSolutionCard);
		
		
		//Checks to see that checkAccusation returns false when the wrong room is accused.
	    assertTrue(board.isSolutionPeopleCard(tempPeopleSolutionCard));
		assertEquals(board.isSolutionRoomCard(wrongRoom), false);
		assertTrue(board.isSolutionWeaponCard(tempWeaponSolutionCard));
		assertEquals(board.checkAccusation(wrongAccusation3), false);
	}
	
	@Test
	public void disproveSuggestion() {
		Player testPlayer = new ComputerPlayer("test", 0, 0, board.convertColor("orange"));
		//cards in the hand of the test player
		Card testPeopleCard = new Card("person", CardType.PERSON);
		Card testRoomCard = new Card("room", CardType.ROOM);
		Card testWeaponCard = new Card("weapon", CardType.WEAPON);
		//extra cards to test in the solution
		Card testPeopleCard2 = new Card("person2", CardType.PERSON);
		Card testRoomCard2 = new Card("room2", CardType.ROOM);
		Card testWeaponCard2 = new Card("weapon2", CardType.WEAPON);
		//adds three cards to the player hand
		testPlayer.addCard(testPeopleCard);
		testPlayer.addCard(testRoomCard);
		testPlayer.addCard(testWeaponCard);
		//variables to test randomness
		boolean peopleCheck = false;
		boolean roomCheck = false;
		boolean weaponCheck = false;
		//testing randomness when multiple cards to disprove
		for(int i = 0; i < 100; i++) {
			Card testResult = testPlayer.disproveSuggestion(new Solution(testPeopleCard, testRoomCard, testWeaponCard));
			if(testResult == testPeopleCard) {
				peopleCheck = true;
			}
			else if(testResult == testRoomCard) {
				roomCheck = true;
			}
			else if(testResult == testWeaponCard) {
				weaponCheck = true;
			}
		}
		//verifies randomness
		assertTrue(peopleCheck);
		assertTrue(roomCheck);
		assertTrue(weaponCheck);
		
		//tests that method returns correct card when only one option exists
		assertEquals(testPeopleCard, testPlayer.disproveSuggestion(new Solution(testPeopleCard, testRoomCard2, testWeaponCard2)));
		assertEquals(testRoomCard, testPlayer.disproveSuggestion(new Solution(testPeopleCard2, testRoomCard, testWeaponCard2)));
		assertEquals(testWeaponCard, testPlayer.disproveSuggestion(new Solution(testPeopleCard2, testRoomCard2, testWeaponCard)));
		
		//tests that null is returned when no card can disprove solution
		assertEquals(null, testPlayer.disproveSuggestion(new Solution(testPeopleCard2, testRoomCard2, testWeaponCard2)));
	}
	
	@Test
	public void handleSuggestion() {
		HumanPlayer human = new HumanPlayer("test1", 0, 0, board.convertColor("orange"));
		ComputerPlayer computer = new ComputerPlayer("test2", 0, 0, board.convertColor("pink"));
		ComputerPlayer computer2 = new ComputerPlayer("test3", 0, 0, board.convertColor("pink"));
		Player[] testPlayers = {human, computer, computer2};
		board.setPlayers(testPlayers);
		Card solCard1 = new Card("1", CardType.PERSON);
		Card solCard2 = new Card("2", CardType.ROOM);
		Card solCard3 = new Card("3", CardType.WEAPON);
		Card miscCard1 = new Card("4", CardType.PERSON);
		Card miscCard2 = new Card("5", CardType.ROOM);
		Card miscCard3 = new Card("6", CardType.WEAPON);
		Card miscCard4 = new Card("7", CardType.PERSON);
		Card miscCard5 = new Card("8", CardType.ROOM);
		Card miscCard6 = new Card("9", CardType.WEAPON);
		human.addCard(miscCard1);
		human.addCard(miscCard2);
		human.addCard(miscCard3);
		computer.addCard(miscCard4);
		computer.addCard(miscCard5);
		computer.addCard(miscCard6);
		//test to return null when no one has cards for solution
		assertEquals(board.handleSuggestion(human, new Solution(solCard1, solCard2, solCard3)), null);
		
		HumanPlayer human2 = new HumanPlayer("test1", 0, 0, board.convertColor("orange"));
		ComputerPlayer computer12 = new ComputerPlayer("test2", 0, 0, board.convertColor("pink"));
		ComputerPlayer computer22 = new ComputerPlayer("test3", 0, 0, board.convertColor("pink"));
		Player[] testPlayers2 = {human2, computer12, computer22};
		board.setPlayers(testPlayers2);
		human2.addCard(solCard1);
		human2.addCard(miscCard2);
		human2.addCard(miscCard3);
		computer12.addCard(miscCard4);
		computer12.addCard(miscCard5);
		computer12.addCard(miscCard6);
		//makes sure human player can disprove and still returns null if human suggested 
		assertEquals(board.handleSuggestion(human2, new Solution(solCard1, solCard2, solCard3)), null);
		//makes sure human can disprove
		assertEquals(board.handleSuggestion(computer22, new Solution(solCard1, solCard2, solCard3)), solCard1);
		
		HumanPlayer human4 = new HumanPlayer("test1", 0, 0, board.convertColor("orange"));
		ComputerPlayer computer14 = new ComputerPlayer("test2", 0, 0, board.convertColor("pink"));
		ComputerPlayer computer24 = new ComputerPlayer("test3", 0, 0, board.convertColor("pink"));
		Player[] testPlayers4 = {human4, computer14, computer24};
		board.setPlayers(testPlayers4);
		human4.addCard(miscCard1);
		human4.addCard(miscCard2);
		human4.addCard(miscCard3);
		computer14.addCard(miscCard4);
		computer14.addCard(miscCard5);
		computer14.addCard(solCard3);
		// tests that computer wont disprove own suggestion (somewhat unneccisary)
		assertEquals(board.handleSuggestion(computer14, new Solution(solCard1, solCard2, solCard3)), null);
		
		HumanPlayer human3 = new HumanPlayer("test1", 0, 0, board.convertColor("orange"));
		ComputerPlayer computer13 = new ComputerPlayer("test2", 0, 0, board.convertColor("pink"));
		ComputerPlayer computer23 = new ComputerPlayer("test3", 0, 0, board.convertColor("pink"));
		Player[] testPlayers3 = {human3, computer13, computer23};
		board.setPlayers(testPlayers3);
		human3.addCard(solCard1);
		human3.addCard(miscCard2);
		human3.addCard(miscCard3);
		computer13.addCard(miscCard4);
		computer13.addCard(miscCard5);
		computer13.addCard(solCard3);
		//last two tests check correct order of players to disprove even when a player has a card to disprove
		assertEquals(board.handleSuggestion(computer23, new Solution(solCard1, solCard2, solCard3)), solCard1);
		computer23.addCard(miscCard4);
		computer23.addCard(miscCard5);
		computer23.addCard(solCard2);
		assertEquals(board.handleSuggestion(computer13, new Solution(solCard1, solCard2, solCard3)), solCard2);
	}
	
	@Test
	public void createSuggestion() {

		ComputerPlayer testPlayer = new ComputerPlayer("test", 0, 0, board.convertColor("orange"));
		//create two rooms to accuse from
		BoardCell testRoom = new BoardCell(0, 0, 'K', DoorDirection.UP);
		BoardCell testRoom2 = new BoardCell(0, 0, 'B', DoorDirection.DOWN);
		//make all cards unseen
		testPlayer.setUnSeenPeople(board.getPlayerDeck());
		testPlayer.setUnSeenRooms(board.getRoomDeck());
		testPlayer.setUnSeenWeapons(board.getWeaponDeck());
		testPlayer.setRoomOptions(board.getRoomDeck());
		
		//checks for random player selection
		Boolean seenA = false; 
		Boolean seenB = false;
		Boolean seenC = false;
		Boolean seenD = false;
		Boolean seenE = false;
		Boolean seenF = false;
		
		//checks for random weapon selection
		Boolean seenHammer = false;
		Boolean seenPipe = false;
		Boolean seenDog = false;
		Boolean seenFists = false;
		Boolean seenPoison = false;
		Boolean seenMace = false;
		
		for(int i = 0; i < 100; i++) {
			Solution testSolution = testPlayer.createSuggestion(board.getLegend().get(testRoom.getInitial()));
			//test correct room
			assertEquals(testSolution.getRoom().getCardName(), "Kitchen");
			//test randomness of people, also sees all cards except one for next test
			if(testSolution.getPerson().getCardName().equals("A")) {
				seenA = true;
				testPlayer.seeCard((testSolution.getPerson()));
			}
			else if(testSolution.getPerson().getCardName().equals("B")) {
				seenB = true;
				testPlayer.seeCard((testSolution.getPerson()));
			}
			else if(testSolution.getPerson().getCardName().equals("C")) {
				seenC = true;
				testPlayer.seeCard((testSolution.getPerson()));
			}
			else if(testSolution.getPerson().getCardName().equals("D")) {
				seenD = true;
				testPlayer.seeCard((testSolution.getPerson()));
			}
			else if(testSolution.getPerson().getCardName().equals("E")) {
				seenE = true;
				testPlayer.seeCard((testSolution.getPerson()));
			}
			else if(testSolution.getPerson().getCardName().equals("F")) {
				seenF= true;
			}
			//test randomness of weapons, also sees all cards except one for next test
			if(testSolution.getWeapon().getCardName().equals("Hammer")) {
				seenHammer = true;
				testPlayer.seeCard((testSolution.getWeapon()));
			}
			else if(testSolution.getWeapon().getCardName().equals("Pipe")) {
				seenPipe = true;
				testPlayer.seeCard((testSolution.getWeapon()));
			}
			else if(testSolution.getWeapon().getCardName().equals("Attack Dog")) {
				seenDog = true;
				testPlayer.seeCard((testSolution.getWeapon()));
			}
			else if(testSolution.getWeapon().getCardName().equals("Bare Fists")) {
				seenFists = true;
				testPlayer.seeCard((testSolution.getWeapon()));
			}
			else if(testSolution.getWeapon().getCardName().equals("Poison")) {
				seenPoison = true;
				testPlayer.seeCard((testSolution.getWeapon()));
			}
			else if(testSolution.getWeapon().getCardName().equals("Mace")) {
				seenMace = true;
			}
		}
		assertTrue(seenA);
		assertTrue(seenB);
		assertTrue(seenC);
		assertTrue(seenD);
		assertTrue(seenE);
		assertTrue(seenF);
		
		assertTrue(seenHammer);
		assertTrue(seenPipe);
		assertTrue(seenDog);
		assertTrue(seenFists);
		assertTrue(seenPoison);
		assertTrue(seenMace);
		//check that correct cards are chosen when only one card of each type is unseen
		Solution testSolution2 = testPlayer.createSuggestion(board.getLegend().get(testRoom2.getInitial()));
		assertEquals(testSolution2.getRoom().getCardName(), "Ballroom");
		assertEquals(testSolution2.getPerson().getCardName(), "F");
		assertEquals(testSolution2.getWeapon().getCardName(), "Mace");
	}

}
