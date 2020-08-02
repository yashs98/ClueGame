package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

/**
 * 
 * @author Daniel Thorne and Yash Sinha
 * 
 * these are a series of tests to see whether the code loads the board correctly from the csv and txt files.
 *
 */

public class MyFileInitTests {
	//Constant values used to test if the board and legend are loaded properly
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLS = 24;
	public static final int NUM_DOORS = 14;
	public static final int LEGEND_LENGTH = 11;
	
	private static Board board;
	
	/**
	 * This method sets up the board class before the tests run.
	 */
	@BeforeClass
	public static void before() {
		board = Board.getInstance();
		board.setConfigFiles("ClueBoard.csv", "ClueRooms.txt", "peopleConfig.txt",  "weaponConfig.txt");
		board.initialize();
	}
	
	/**
	 * This method makes sure the board is the correct dimensions.
	 */
	@Test
	public void testSize() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLS, board.getNumColumns());
	}
	
	/**
	 * This method makes sure the legend has been put into a map properly with correct keys and values.
	 */
	@Test
	public void testLegend() {
		Map<Character, String> testLegend = board.getLegend();
		assertEquals(testLegend.size(), LEGEND_LENGTH);
		assertEquals("Dining room", testLegend.get('D'));
		assertEquals("Study", testLegend.get('S'));
		assertEquals("Walkway", testLegend.get('W'));
		assertEquals("Closet", testLegend.get('C'));
		assertEquals("Music room", testLegend.get('M'));
		assertEquals("Kitchen", testLegend.get('K'));
		assertEquals("Home theater", testLegend.get('T'));
	}
	
	/**
	 * This method runns all tests involving doors. Tt checks that there is one in each direction,
	 * that there are the proper amount, and that doors know they are doors and non-doors know they arent doors.
	 */
	@Test
	public void testDoors() {
		//Tests a door in each of the four directions
		BoardCell doorCell = board.getCellAt(9, 2);
		assertTrue(doorCell.isDoorway());
		assertEquals(DoorDirection.UP, doorCell.getDoorDirection());
		doorCell = board.getCellAt(10, 5);
		assertTrue(doorCell.isDoorway());
		assertEquals(DoorDirection.RIGHT, doorCell.getDoorDirection());
		doorCell = board.getCellAt(6, 12);
		assertTrue(doorCell.isDoorway());
		assertEquals(DoorDirection.DOWN, doorCell.getDoorDirection());
		doorCell = board.getCellAt(10, 17);
		assertTrue(doorCell.isDoorway());
		assertEquals(DoorDirection.LEFT, doorCell.getDoorDirection());
		
		//Tests that certain cells are doors and certain cells aren't.
		doorCell = board.getCellAt(20, 15);
		assertFalse(doorCell.isDoorway());
		doorCell = board.getCellAt(19, 16);
		assertTrue(doorCell.isDoorway());
		doorCell = board.getCellAt(8, 12);
		assertFalse(doorCell.isDoorway());
		doorCell = board.getCellAt(6, 12);
		assertTrue(doorCell.isDoorway());
		
		//Counts all doors and makes sure there are the correct abount loaded in.
		int numDoors = 0;
		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				BoardCell cell = board.getCellAt(i, j);
				if(cell.isDoorway()) {
					numDoors++;
					
				}
			}
		}
		System.out.println(Integer.toString(numDoors));
		assertEquals(numDoors, NUM_DOORS);
	}
	
	/**
	 * This method simply tests that specifically picked test cells have the correct initial.
	 */
	@Test
	public void testInitails() {
		BoardCell testCell = board.getCellAt(2, 2);
		assertEquals('L', testCell.getInitial());
		testCell = board.getCellAt(16, 8);
		assertEquals('W', testCell.getInitial());
		testCell = board.getCellAt(19, 3);
		assertEquals('K', testCell.getInitial());
		testCell = board.getCellAt(3, 19);
		assertEquals('S', testCell.getInitial());
		testCell = board.getCellAt(13, 13);
		assertEquals('C', testCell.getInitial());
		testCell = board.getCellAt(18, 16);
		assertEquals('B', testCell.getInitial());
		testCell = board.getCellAt(17, 9);
		assertEquals('H', testCell.getInitial());
		testCell = board.getCellAt(8, 3);
		assertEquals('D', testCell.getInitial());
		testCell = board.getCellAt(0, 2);
		assertEquals('L', testCell.getInitial());
	}
}
