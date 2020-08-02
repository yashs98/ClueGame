package tests;


import java.util.Set;


import static org.junit.Assert.*;

//import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

/**
 * 
 * @author Yash Sinha and Daniel Thorne and Calvin Mak
 * This class tests the adjacencies and the targets functions to make sure they are being calculated and stored correctly. The doors are
 * also tested to see whether they are opened and exited in the right directions. A variable of type Board is created to test the functions
 * in the Board class. 
 *
 */
public class AdjandTargetTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueBoard.csv", "ClueRooms.txt", "peopleConfig.txt", "weaponConfig.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	//This test makes sure cells inside rooms have no adjacent cells
	@Test
	public void testInsideRooms() {
		Set<BoardCell> testList = board.getAdjList(2, 0);
		assertEquals(0, testList.size());
	    
		testList = board.getAdjList(5, 0);
		assertEquals(0, testList.size());
	
		testList = board.getAdjList(9, 0);
		assertEquals(0, testList.size());
	
		testList = board.getAdjList(11, 1);
		assertEquals(0, testList.size());
	
		testList = board.getAdjList(20, 0);
		assertEquals(0, testList.size());
	
		testList = board.getAdjList(5, 12);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(18, 10);
		assertEquals(0, testList.size());
	}
	
	//This cell test whether the door labeled LD has the proper adjacent cells
	@Test
	public void doorDirectionLD() {
		Set<BoardCell> testList = board.getAdjList(5, 1);
		BoardCell cell = board.getCellAt(5, 1);
		assertEquals(1, testList.size());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
	}
	
	//This cell test whether the door labeled DU has the proper adjacent cells
	@Test
	public void doorDirectionDU() {
		Set<BoardCell> testList = board.getAdjList(9, 2);
		BoardCell cell = board.getCellAt(9, 2);
		assertEquals(1, testList.size());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
	}
	
	//This cell test whether the door labeled DR has the proper adjacent cells
	@Test
	public void doorDirectionDR() {
		Set<BoardCell> testList = board.getAdjList(10, 5);
		BoardCell cell = board.getCellAt(10, 5);
		assertEquals(1, testList.size());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
	}
	
	//This cell test whether a walkway knows its not  door
	@Test
	public void doorDirectionW() {
		Set<BoardCell> testList = board.getAdjList(1, 2);
		BoardCell cell = board.getCellAt(1, 2);
		assertEquals(1, testList.size());
		assertEquals(DoorDirection.NONE, cell.getDoorDirection());
	}
	
	//This cell test whether the door labeled ML has the proper adjacent cells
	@Test
	public void doorDirectionML() {
		Set<BoardCell> testList = board.getAdjList(3, 10);
		BoardCell cell = board.getCellAt(3, 10);
		assertEquals(1, testList.size());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
	}
	
	//This cell test whether a room cell knows its not a door
	@Test
	public void doorDirectionM() {
		Set<BoardCell> testList = board.getAdjList(6, 15);
		BoardCell cell = board.getCellAt(6, 15);
		assertEquals(0, testList.size());
		assertEquals(DoorDirection.NONE, cell.getDoorDirection());
	}
	
	
	// This tests whether the cells in rooms has adjacencies
	@Test
	public void adjacencylistInsideRoom() {
		Set<BoardCell> testList = board.getAdjList(3, 0);
		BoardCell cell = board.getCellAt(2, 0);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(5, 0);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(9, 0);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(11, 1);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(20, 2);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(18, 11);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(5, 13);
		assertEquals(0, testList.size());
		
	}
	
	//This test makes sure doors only have one adjacent cell.
	@Test 
	public void roomExitAdjacencies() {
		Set<BoardCell> testList = board.getAdjList(5, 18);
	    assertEquals(1, testList.size());
	    assertTrue(testList.contains(board.getCellAt(6, 18)));
	    
	    testList = board.getAdjList(10, 17);
	    assertEquals(1, testList.size());
	    assertTrue(testList.contains(board.getCellAt(10, 16)));
	    
	    testList = board.getAdjList(16, 19);
	    assertEquals(1, testList.size());
	    assertTrue(testList.contains(board.getCellAt(15, 19)));
	    
		
		
	}
	
	//This test makes sure the walkways beside doors with correct direction list doors as an adjacent cell. 
	@Test
	public void adjacencyDoorBesideRooms() {
		Set<BoardCell> testList = board.getAdjList(6, 1);
		BoardCell cell = board.getCellAt(6, 1);

	    assertEquals(4, testList.size());
	    assertEquals(true, testList.contains(board.getCellAt(5,1)));
	    
	    testList = board.getAdjList(8, 2);
	    cell = board.getCellAt(8, 2);
	    assertEquals(3, testList.size());
	    assertEquals(true, testList.contains(board.getCellAt(9,2)));
	    
	    testList = board.getAdjList(10, 6);
	    cell = board.getCellAt(10,6);
	    assertEquals(4, testList.size());
	    assertEquals(true, testList.contains(board.getCellAt(10, 5)));
	    
	    testList = board.getAdjList(3, 9);
	    cell = board.getCellAt(3, 9);
	    assertEquals(3, testList.size());
	    assertEquals(true, testList.contains(board.getCellAt(3,10)));
	    
	    testList = board.getAdjList(10, 16);
	    cell = board.getCellAt(10, 16);
	    assertEquals(4, testList.size());
	    assertEquals(true, testList.contains(board.getCellAt(10, 17)));
		
		
	}
	
	//This test makes sure walkways list correct adjacencies
	@Test
	public void testWalkway() {
		Set<BoardCell> testList = board.getAdjList(5, 3);
		assertEquals(4, testList.size());
		
		testList = board.getAdjList(15, 0);
		assertEquals(1, testList.size());
		
		testList = board.getAdjList(15, 7);
		assertEquals(4, testList.size());
		
		testList = board.getAdjList(2, 16);
		assertEquals(3, testList.size());
		
		testList = board.getAdjList(15, 15);
		assertEquals(4, testList.size());
		
	}
	
	//This test makes sure targets are calculated properly
	@Test
	public void testTargets() {
		 board.calcTargets(20, 6, 2);
		 Set<BoardCell> testList = board.getTargets();
		 
		assertEquals(2, testList.size());
		
		
		board.calcTargets(12, 6, 2);
		
		testList = board.getTargets();
		assertEquals(4, testList.size());
		
		board.calcTargets(6, 12, 1);
		testList = board.getTargets();
		assertEquals(1, testList.size());
		
		
		board.calcTargets(3, 16, 3);
		testList = board.getTargets();
		assertEquals(9, testList.size());
		
		board.calcTargets(20, 15, 5);
		testList = board.getTargets();
		assertEquals(4, testList.size());
		
		
		
		
		
		
	}

}
