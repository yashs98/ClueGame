package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.lang.reflect.Field;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * 
 * @author Yash Sinha and Daniel Thorne and Calvin Mak
 * This class creates and sets up the board for Clue. It has variables numRows, and numBolumns, which are 
 * the rows and columns of the board, it has a constant MAX_BOARD_SIZE, a 2D array of type BoardCell called 
 * board, a Map of character and String called legend, a Map of type BoardCell, a Set<BoardCell> called 
 * adjMatrix, a set of BoardCell called visited, a set of BoardCell called targets, two strings called 
 * boardConfigFile and roomConfigFile, and a static Board variable called theBoard. 
 */
public class Board extends JPanel{
	private int numRows;
	private int numColumns;
	private int numDoors;
	private int numPlayers;								//Used for JUnit tests.
	private int cellWidth;	
	private int cellHeight;	
	public static final int MAX_BOARD_SIZE=50;			//Initial Maximum Size
	public static final int MAX_PLAYERS = 6;
	private BoardCell[][] board;
	private Map<Character, String> legend;				//Legend file Character to name Config
	private Map<BoardCell, Set<BoardCell>> adjMatrix;	//Configures Adjacent Matrix
	//private static Set<Card> cardDeck;							//Setting up framework for a Card.
	private static Vector<Card> roomDeck;
	private static Vector<Card> weaponDeck;
	private static Vector<Card> playerDeck;
	private static Vector<Card> deck;
	private static Player[] players;
    private boolean isHighlighted;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private String peopleConfigFile;
	private String weaponConfigFile;
	private static Solution theSolution;
	private static Board theBoard = new Board();
	private Map<Character, BoardCell> writeRoomNames;
	private static GameDriver driver;
	//private ControlPanel cPanel = new ControlPanel();
	//private static ControlPanel cPanel;
	/**
	 * This function checks for bad configuration for using loadBoardConfig and loadRoomConfig to create 
	 * the board. 
	 * @throws BadConfigFormatException 
	 */

	public void initialize() {
		try {
			loadBoardConfig();
			loadRoomConfig();
			loadPeopleConfig();
			loadWeaponConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		setPlayerLists();
		calcAdjacencies();
		deal();
	}


	/**
	 * This is a default constructor that initializes rows, columns, the board variable and the legends map. 
	 */
	public Board() {
		numRows = 0;
		numColumns = 0;
		numPlayers = 0;
		numDoors = 0;						//Used in a test
		//cardDeck = new HashSet<Card>();
		roomDeck = new Vector<Card>();
		weaponDeck = new Vector<Card>();
		playerDeck = new Vector<Card>();
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		players = new Player[MAX_PLAYERS];
		legend = new HashMap <Character, String>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		deck = new Vector<Card>();
		writeRoomNames = new HashMap<Character, BoardCell>();
		
		repaint();
		
		
		
		
		
	}
	/**
	 * 
	 * @return
	 * This function returns the cell the player is in right now. 
	 */
	public static Board getInstance() {
		return theBoard;
	}
	
	/**
	 * 
	 * @throws BadConfigFormatException 
	 * This function creates and loads the rooms for the board. This function possibly needs the 
	 * BadConfigFormatException in case the rooms aren't constructed, due to inaccurate dimensions being added 
	 * for the board. This is done by storing the legends of the various cells of the rooms in a map of legends.
	 * @throws FileNotFoundException 
	 */
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader read = new FileReader(roomConfigFile);
		Scanner scan = new Scanner(read);
		
		
		while(scan.hasNext()) {
			String x = scan.nextLine();
			String l [] = x.split(", ");
			char label = l[0].charAt(0);
			String meaning = l[1];
			if (l[2].equals("Card")) {
				Card newRoom = new Card(l[1], CardType.ROOM);
				roomDeck.add(newRoom);
				//Adds to CardDeck the Rooms
				//cardDeck.add(new Card(l[1], CardType.ROOM));
				deck.add(newRoom);
				
			}
			
			else {
				if (!l[2].equals("Other")) {
					throw new BadConfigFormatException("Bad Legend Configuration.");
				}
			}
			legend.put(label, meaning);
			}
		
		}
		

		
	

	
	/**
	 * 
	 * @throws BadConfigFormatException
	 * This function creates and loads the Board. It also needs the BadConfigFormatException thrown in case
	 * the compiler reaches an issue with creating the board due to the dimensions given. It is done by storing
	 * information about the particular cell by using the boardCell's constructor in a 2D array. 
	 * @throws FileNotFoundException 
	 */
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader read = new FileReader(boardConfigFile);
		Scanner boardReader = new Scanner(read);
		
		
		int rowNum = 0;
		
	
		
		while(boardReader.hasNextLine()) {
			String line = boardReader.nextLine();
			
			String[] row = line.split(",");
			
			if(numColumns != row.length && numColumns != 0) {
				throw new BadConfigFormatException("INCORRECT COLUMN AMOUNT");
			}
			numColumns = row.length;
			cellHeight = getHeight()/numColumns;
			cellWidth = getWidth()/row.length;
			
			
		  
			for(int i = 0; i < row.length; i++) { 
				if(!legend.containsKey(row[i].charAt(0))) {
				
					if(row[i].length() == 1) {
						board[rowNum][i] = new BoardCell(rowNum, i, row[i].charAt(0), DoorDirection.NONE);
					}
					else if(row[i].length() == 2) {
					
						if(row[i].charAt(1) == 'N') {
							board[rowNum][i] = new BoardCell(rowNum, i, row[i].charAt(0), DoorDirection.NAME);
						}
						else if(row[i].charAt(1) == 'D') {
							board[rowNum][i] = new BoardCell(rowNum, i, row[i].charAt(0), DoorDirection.DOWN);
							numDoors++;
						}
						else if(row[i].charAt(1) == 'L') {
							board[rowNum][i] = new BoardCell(rowNum, i, row[i].charAt(0), DoorDirection.LEFT);
							numDoors++;
						}
						else if(row[i].charAt(1) == 'U') {
							board[rowNum][i] = new BoardCell(rowNum, i, row[i].charAt(0), DoorDirection.UP);
							numDoors++;
						}
						else if(row[i].charAt(1) == 'R') {
							board[rowNum][i] = new BoardCell(rowNum, i, row[i].charAt(0), DoorDirection.RIGHT);
							numDoors++;
						}
					}
					else {
						throw new BadConfigFormatException("Can't determine the door direction");
					}
				}
				
				else {
					throw new BadConfigFormatException("NOT CONTAINED IN LEGEND");
				}
				
				Set<BoardCell> set = new HashSet<BoardCell>();
				adjMatrix.put(board[rowNum][i], set);
			}
		  
		  
			
		  
		
			numRows++;
			rowNum++;
			
		}
		boardReader.close();
		
		
		

		
	}
	
	/**
	 * 
	 * @throws BadConfigFormatException
	 * @throws FileNotFoundException
	 * 
	 * This function helps create players by loading in a file that has the the player's name, the player's color, and the player's starting 
	 * coordinates with rows and then columns being specified. 
	 */
	public void loadPeopleConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader reader = new FileReader(peopleConfigFile);
		Scanner peopleReader = new Scanner(reader);
		int row;
		int col;
		String color;
		String name;
		boolean madePlayer = false;
		while(peopleReader.hasNextLine()) {
			String line = peopleReader.nextLine();
			String[] items = line.split(", ");
			name = items[0];
			color = items[1];
			row = Integer.parseInt(items[2]);
			col = Integer.parseInt(items[3]);
			if(!madePlayer) {
				Player human = new HumanPlayer(name, row, col, convertColor(color));
				players[numPlayers] = human;
				numPlayers++;
				madePlayer = true;
				
			}
			else {
				Player comp = new ComputerPlayer(name, row, col, convertColor(color));
				players[numPlayers] = comp;
				numPlayers++;
			}
			Card playerCard = new Card(name, CardType.PERSON);
			
			playerDeck.add(playerCard);
			deck.add(playerCard);
			
		}
		
	}
	
	/**
	 * 
	 * @throws BadConfigFormatException
	 * @throws FileNotFoundException
	 * This function helps create the weapons by loading in a file with different weapon names and storing them in a deck of cards. 
	 */
	public void loadWeaponConfig() throws BadConfigFormatException,FileNotFoundException {
		FileReader reading = new FileReader(weaponConfigFile);
		Scanner weaponReader = new Scanner(reading);
		
		while(weaponReader.hasNextLine()) {
			Card weaponCard = new Card(weaponReader.nextLine(), CardType.WEAPON);
			deck.add(weaponCard);
			weaponDeck.add(weaponCard);
			
			
		}
		
		
	}
	/**
	 * This function calculates the adjacent board cells of the player. 
	 */
	
	public void calcWalkwayAdjacencies(int currentI, int currentJ, int newI, int newJ, DoorDirection dir) {
		if(newI>=0 && newJ>=0 && newI<numRows && newJ<numColumns) {
			if(legend.get(board[newI][newJ].getInitial()).equals("Walkway")) {
				adjMatrix.get(board[currentI][currentJ]).add(board[newI][newJ]);
			}
			else if(board[newI][newJ].getDoorDirection() == dir) {
				adjMatrix.get(board[currentI][currentJ]).add(board[newI][newJ]);
			}
		}
	}
	
	public void calcAdjacencies() {
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				//If the cell is a walk way
				if(legend.get(board[i][j].getInitial()).equals("Walkway")) {
					calcWalkwayAdjacencies(i, j, i-1, j, DoorDirection.DOWN);
					calcWalkwayAdjacencies(i, j, i+1, j, DoorDirection.UP);
					calcWalkwayAdjacencies(i, j, i, j-1, DoorDirection.RIGHT);
					calcWalkwayAdjacencies(i, j, i, j+1, DoorDirection.LEFT);
				}
				//if the cell is a door way, add the cell in the direction the door is pointing.
				if(board[i][j].isDoorway()) {
					if(board[i][j].getDoorDirection() == DoorDirection.UP) {
						adjMatrix.get(board[i][j]).add(board[i-1][j]);
					}
					if(board[i][j].getDoorDirection() == DoorDirection.DOWN) {
						adjMatrix.get(board[i][j]).add(board[i+1][j]);
					}
					if(board[i][j].getDoorDirection() == DoorDirection.LEFT) {
						adjMatrix.get(board[i][j]).add(board[i][j-1]);
					}
					if(board[i][j].getDoorDirection() == DoorDirection.RIGHT) {
						adjMatrix.get(board[i][j]).add(board[i][j+1]);
					}
				}
			}
		}
	}
	/**
	 * 
	 * @param cell
	 * @param pathlength
	 * 
	 * This function stores the targeted board cells of the player in a set, while also stores
	 * the board cells already visited. This is done by utilizing the findTargets functions. 
	 */
	public void calcTargets(int row, int col, int pathlength) {
		BoardCell cell = getCellAt(row, col);
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(cell);
		findTargets(cell, pathlength);
	}
	
	/**
	 * 
	 * @param cell
	 * @param pathLength
	 * This function finds the targeted board cells of the player in a set, while also making sure the 
	 * player isn't able to go back to board cells already visited. This is done by calculating board cells 
	 * in a particular length from the player.
	 * 
	 */
	private void findTargets(BoardCell cell, int pathLength) {
		for(BoardCell adjCell: getAdjList(cell.getRow(), cell.getColumn())) {
			if(!visited.contains(adjCell)) {
				visited.add(adjCell);
				if(pathLength == 1 || adjCell.isDoorway()) {
					targets.add(adjCell);
				}
				else {
					findTargets(adjCell, pathLength-1);
				}
				visited.remove(adjCell);
			}
		}
	}
    
	/**
	 * 
	 * @param cell
	 * @return
	 * This function returns a set of adjacency lists for a particular board cell. 
	 */
	public Set<BoardCell> getAdjList(int i, int j) {
		BoardCell cell = getCellAt(i, j);
		return adjMatrix.get(cell);
	}
    /**
     * 
     * @param boardFile	
     * @param roomFile
     * 
     * This function sets the configuration of the clue board and the rooms in the board. 
     */
	public void setConfigFiles(String boardFile, String roomFile, String peopleFile, String weaponFile) {
		boardConfigFile = boardFile;
		roomConfigFile = roomFile;
		peopleConfigFile = peopleFile;
		weaponConfigFile = weaponFile;
	}
    
	public void setPlayerLists() {
		for(Player player: players) {
			player.setUnSeenPeople((Vector<Card>) playerDeck.clone());
			player.setUnSeenRooms((Vector<Card>) roomDeck.clone());
			player.setUnSeenWeapons((Vector<Card>) weaponDeck.clone());
			if(player instanceof ComputerPlayer) {
			((ComputerPlayer) player).setRoomOptions((Vector<Card>) roomDeck.clone());
			}
		}
		
	}
	
	//Getter Functions
	
    /**
     * 
     * @return
     * This function returns the number of rows of the board. 
     */
	public int getNumRows() {
		return numRows;
	}
    
	/**
	 * 
	 * @return
	 * 
	 * This function returns the number of the columns of the board. 
	 */
	public int getNumColumns() {
		return numColumns;
	}
	
	/**
	 * 
	 * @return
	 * 
	 * This function returns a map that contains the legends/labels of the board. 
	 */
	public Map<Character, String> getLegend() {
		return legend;
	}
    
	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 * This function returns a particular board cell with the row and the column specified. 
	 */
	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}

     /**
      * 
      * @return targets
      * This function returns a set full of target board cells of a player. 
      */
	public Set<BoardCell> getTargets() {
		// TODO Auto-generated method stub
		return targets;
	}
	
	

    /**
     * 
     * @return playerDeck
     * This function returns the set called playerDeck. 
     */
	public Vector<Card> getPlayerDeck() {
		return playerDeck;
	}
	
	/**
	 * 
	 * @return roomDeck
	 * This is a getter for roomDeck.
	 */
	public Vector<Card> getRoomDeck() {
		return roomDeck;
	}

    
	/**
	 * 
	 * @return weaponDeck
	 * This is a getter for weaponDeck.
	 */
	public Vector<Card> getWeaponDeck() {
		return weaponDeck;
	}
	
	/**
	 * 
	 * @return players
	 * This is a getter for players.
	 */
	public Player[] getPlayers() {
		return players;
	}

    /**
     * 
     * @return numPlayers
     * This is a getter for numPlayers.
     */
	public int getNumPlayers() {
		return numPlayers;
	}
	
	/**
	 * 
	 */
	public void selectAnswer() {
		
	}
	
	/**
	 * Goes around in a circle. Players that have the cards suggested show them to disprove a suggestion.
	 * @return
	 */
	public Card handleSuggestion(Player accuser, Solution suggestion) {
		//finds where the accusing player is in order and sets a start point
		int startPoint = 0;
		for(int i = 0; i < players.length; i++) {
			if(players[i] == accuser) {
				startPoint = i+1;
			}
		}
		//this loops through all players up to (but not including) the accusing player and checks for disproving cards.
		int i = startPoint%players.length;
		do {
			Card disprover = players[i%players.length].disproveSuggestion(suggestion);
			if(disprover!=null) {
				JOptionPane.showMessageDialog(theBoard,players[i%players.length].getPlayerName() + " disproved The Suggestion", "Disproved suggestion", JOptionPane.OK_OPTION);
				//cPanel.updateGuessRBox("False");
				return disprover;
			}
			i++;
		}while(i < startPoint%players.length+players.length-1);
		JOptionPane.showMessageDialog(theBoard,"No one disproved The Suggestion", "Disproved suggestion", JOptionPane.OK_OPTION);
		//cPanel.updateGuessRBox("True");
		return null;
	}
	
	public boolean checkAccusation(Solution accusation) {
		if(accusation.getPerson() == theSolution.getPerson() && accusation.getRoom() == theSolution.getRoom() && accusation.getWeapon() == theSolution.getWeapon()) {
			return true;
		}
		else {
			return false;
		}
	}
    
	
    /**
     * 
     * @param strColor
     * @return color
     * This function helps convert the colors specified in one of the csv files. 
     */
	public Color convertColor(String strColor) {
		Color color;
		try {
		    Field field = Class.forName("java.awt.Color").getField(strColor.trim());
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = null; // Not defined
		}
		return color;
	}
	
	/**
	 * This function helps deal the cards to the players as per the rule of Clue. 
	 */
	public void deal() {
		Vector<Card> dealtPeople = (Vector<Card>) playerDeck.clone();			//Clones the decks to retain the original deck before starting the game.
		Vector<Card> dealtRooms = (Vector<Card>) roomDeck.clone();
		Vector<Card> dealtWeapons = (Vector<Card>) weaponDeck.clone();
		Vector<Card> dealtDeck = (Vector<Card>) deck.clone();
		Random randNum = new Random();

		//Card solutions
		Card peopleSol = dealtPeople.remove(randNum.nextInt(dealtPeople.size()));
		dealtDeck.remove(peopleSol);
		
		Card roomSol = dealtRooms.remove(randNum.nextInt(dealtRooms.size()));
		dealtDeck.remove(roomSol);
		
		Card weaponSol = dealtWeapons.remove(randNum.nextInt(dealtWeapons.size()));
		dealtDeck.remove(weaponSol);
		
		theSolution = new Solution(peopleSol, roomSol, weaponSol);
		System.out.println(theSolution.getPerson().getCardName());
		System.out.println(theSolution.getRoom().getCardName());
		System.out.println(theSolution.getWeapon().getCardName());
		//Deals the player's hand.
		
		int handSize = dealtDeck.size()/players.length;
		
		for(Player currentPlayer: players) {
		   	
			for(int i = 0; i < handSize; i++) {
				
				Card currentCard = dealtDeck.remove(randNum.nextInt(dealtDeck.size()));
				currentPlayer.addCard(currentCard);
			
			}
			
			

		}		

	}

    /**
     * 
     * 
     * @return deck
     * This is a getter for deck.
     */
	public Vector<Card> getDeck() {
		return deck;
	}
	
	
	/**
	 * 
	 * @return sum
	 * For JUnit Testing.
	 * Adds up all the dealt cards all the players have and the cards in the solution
	 */
	public int sumOfDecks() {
		int sum = 0;
		for(int i = 0; i < players.length; i++) {
			sum += players[i].getHand().size();
		}
		return sum + 3;
	}
	
	/**
	 * 
	 * @param player
	 * @param player2
	 * @return boolean
	 * Compares two player deck sizes. They should be equal or off by 1. Return true/false.
	 */
	public boolean compareDeckCount(Player player, Player player2) {
		
		if(player.getHand().size() == player2.getHand().size() || Math.abs(player.getHand().size() - player2.getHand().size()) == 1) {
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * 
	 * @param player
	 * @return boolean
	 * Checks that the player has the appropriate number of cards.
	 */
	public boolean checkDeckCount(Player player) {
		if ((deck.size()/numPlayers) - player.getHand().size() == 0 || Math.abs((deck.size()/numPlayers) - player.getHand().size()) == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param card
	 * @return count
	 * Loops through players counting number of identical cards
	 * 
	 */
	public int checkCard(Card card) {
		int count = 0;
		for(Player player: players) {
			count += Collections.frequency(player.getHand(), card);
		}
		return count;
	}

	/**
	*
	*	JUnit Tests
	*	gameActionTests
	*
	*/
	public boolean roomCheck(Card card, Player player) {
		/*if (card.getCardType() == CardType.ROOM && card.getCardName() == legend.get(board[player.getRow()][player.getColumn()])) {
			return true;
		}*/
		return false;
	}
	
	public boolean peopleCheck() {
		
		return false;
	}
	public boolean weaponCheck() {
		
		return false;
	}
	
	public boolean isSolutionPeopleCard(Card accusePerson) {
		if(accusePerson == theSolution.getPerson()) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean isSolutionWeaponCard(Card accuseWeapon) {
		if(accuseWeapon == theSolution.getWeapon()) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	public boolean isSolutionRoomCard(Card accuseRoom) {
		if(accuseRoom == theSolution.getRoom()) {
			return true;
		}
		else {
		return false;
		}
	}
	
	//for Testing
	public void setSolution(Solution sol) {
		theSolution = sol;
		
	}
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
	@Override
	/**
	 * This helps draw the board, and the players. 
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0;i<numRows; i++) {
			for(int j=0; j<numColumns; j++) {
			       
				   board[i][j].draw(g);
		           
			   
			}
		}
        
		for(Player player: players) {
			player.draw(g);
		}
		
		if(targets != null) {
			for(BoardCell cell: targets) {
				cell.drawTarget(g);
			}
			isHighlighted = true;
		}
		else {
			isHighlighted = false;
		}
	}
	
	
	
	public boolean isHighlighted() {
		return isHighlighted;
	}


	/**
	 * This is a getter for the Human Player
	 * @return
	 */
	public Player getHumanPlayer() {
		for(Player player: players) {
			if(player instanceof HumanPlayer) {
				return player;
			}
		}
		return null;
	}


	public void setTargets(Set<BoardCell> targets) {
		this.targets = targets;
	}
	
	public Solution checkPlayerAccusation (Player player) {
		Solution checker = ((ComputerPlayer) player).makeAccusation();
		if(checker != null) {
			if (checker.person == theSolution.person && checker.room == theSolution.room && checker.weapon == theSolution.weapon) {
				return theSolution;
			}
		}
		return null;
	}
	
	
}

