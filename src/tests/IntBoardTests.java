package tests;


import static org.junit.Assert.*;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

/**
 * 
 * @author Yash Sinha and Daniel Thorne
 * This class is a test class that has 6 tests to examine that the adjacencies of a particular board cell is 
 * being calculated properly and stored to the map properly. Another 6 tests examine whether the targets for 
 * a particular board cell are being calculated properly. A variable called board that is of type IntBoard is
 * made so that the tests can happen. The functions made are setUp, testAdjacency0_0, testAdjacency1_3,
 * testAdjacency3_0, testAdjacency1_1, testAdjacency2_2. testAdjacency3_3, testTargets1, testTargets2, 
 * testTargets3, testTargets4, testTargets5, and testTargets6.
 *
 */
public class IntBoardTests {

	
	private IntBoard board;
	/**
	 * This function initializes board and uses the calcAdjacencies to fill up the HashMap in order to 
	 * do the tests. 
	 */
	@Before
	public void setUp() {
		board = new IntBoard();
		board.calcAdjacencies();
	}

	/**
	 * This tests whether the adjacencies to the cell at (0,0) is being calculated and stored properly in the
	 * HashMap.
	 */
	@Test
	public void testAdjacency0_0() {
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	/**
	 * This tests whether the adjacencies to the cell at (1,3) is being calculated and stored properly in the 
	 * HashMap.
	 */
	@Test
	public void testAdjacency1_3() {
		BoardCell cell = board.getCell(1,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(3, testList.size());
	}
	
	/**
	 * This tests whether the adjacencies to the cell at (3,0) is being calculated and stored properly in the 
	 * HashMap.
	 */
	@Test
	public void testAdjacency3_0() {
		BoardCell cell = board.getCell(3,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertEquals(2, testList.size());
	}
	
	/**
	 * This tests whether the adjacencies to the cell at (1,1) is being calculated and stored properly in the 
	 * HashMap.
	 */
	@Test
	public void testAdjacency1_1() {
		BoardCell cell = board.getCell(1,1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(4, testList.size());
	}
	
	/**
	 * This tests whether the adjacencies to the cell at (2,2) is being calculated and stored properly in the 
	 * HashMap.
	 */
	@Test
	public void testAdjacency2_2() {
		BoardCell cell = board.getCell(2,2);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3,2)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(4, testList.size());
	}
	
	/**
	 * This tests whether the adjacencies to the cell at (3,3) is being calculated and stored properly in the 
	 * HashMap.
	 */
	@Test
	public void testAdjacency3_3() {
		BoardCell cell = board.getCell(3,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(2, testList.size());
	}
	
	/**f
	 * This tests whether the targets to the cell at (1,1) is being calculated properly.
	 */
	@Test
	public void testTargets1() {
		BoardCell cell = board.getCell(1,1);
		board.calcTargets(cell, 1);
		Set<BoardCell> testList = board.getTargets();
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(4,testList.size());
	}
	
	/**
	 * This tests whether the targets to the cell at (2,2) is being calculated properly.
	 */
	@Test
	public void testTargets2() {
		BoardCell cell = board.getCell(2,2);
		board.calcTargets(cell, 2);
		Set<BoardCell> testList = board.getTargets();
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertTrue(testList.contains(board.getCell(3, 3)));
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertEquals(6,testList.size());
	}
	
	/**
	 * This tests whether the targets to the cell at (2,0) is being calculated properly.
	 */
	@Test
	public void testTargets3() {
		BoardCell cell = board.getCell(2,0);
		board.calcTargets(cell, 3);
		Set<BoardCell> testList = board.getTargets();
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertEquals(7,testList.size());
	}
	
	/**
	 * This tests whether the targets to the cell at (0,2) is being calculated properly.
	 */
	@Test
	public void testTargets4() {
		BoardCell cell = board.getCell(0,2);
		board.calcTargets(cell, 4);
		Set<BoardCell> testList = board.getTargets();
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertTrue(testList.contains(board.getCell(3, 3)));
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertTrue(testList.contains(board.getCell(0, 0)));
		assertEquals(7,testList.size());
	}
	
	/**
	 * This tests whether the targets to the cell at (1,3) is being calculated properly.
	 */
	@Test
	public void testTargets5() {
		BoardCell cell = board.getCell(1,3);
		board.calcTargets(cell, 5);
		Set<BoardCell> testList = board.getTargets();
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(8,testList.size());
	}
	
	/**
	 * This tests whether the targets to the cell at (0,0) is being calculated properly.
	 */
	@Test
	public void testTargets6() {
		BoardCell cell = board.getCell(0,0);
		board.calcTargets(cell, 6);
		Set<BoardCell> testList = board.getTargets();
		assertTrue(testList.contains(board.getCell(3, 3)));
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertEquals(7,testList.size());
	}
}
