package clueGame;

import java.util.LinkedList;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class AdjandTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = new Board();
		board.initialize();
		//board.calcAdjacencies();
	}

	//Make sure a player cannot move around inside a room
	@Test
	public void testAdjacenciesInsideRooms()
	{
		LinkedList<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		testList = board.getAdjList(1, 1);
		assertEquals(0, testList.size());
		testList = board.getAdjList(11, 16);
		assertEquals(0, testList.size());
		testList = board.getAdjList(20, 10);
		assertEquals(0, testList.size());
		testList = board.getAdjList(17, 4);
		assertEquals(0, testList.size());
	}

	//These tests are white on the spreadsheet
	@Test
	public void testAdjacencyDoor()
	{ 
		LinkedList<BoardCell> testList = board.getAdjList(6, 17);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(7, 17))); 
		
		testList = board.getAdjList(9, 11);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 11)));
		assertTrue(testList.contains(board.getCellAt(9, 10)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are orange in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		LinkedList<BoardCell> testList = board.getAdjList(18, 5);
		assertTrue(testList.contains(board.getCellAt(18, 4)));
		assertTrue(testList.contains(board.getCellAt(18, 6)));
		assertTrue(testList.contains(board.getCellAt(17, 5)));
		assertTrue(testList.contains(board.getCellAt(19, 5)));
		assertEquals(4, testList.size());
		
		testList = board.getAdjList(15, 17);
		assertTrue(testList.contains(board.getCellAt(14, 17)));
		assertTrue(testList.contains(board.getCellAt(15, 18)));
		assertTrue(testList.contains(board.getCellAt(15, 16)));
		assertTrue(testList.contains(board.getCellAt(16, 17)));
		assertEquals(4, testList.size());
		
		testList = board.getAdjList(4, 14);
		assertTrue(testList.contains(board.getCellAt(4, 15)));
		assertTrue(testList.contains(board.getCellAt(3, 14)));
		assertTrue(testList.contains(board.getCellAt(5, 14)));
		assertEquals(3, testList.size());
		
		testList = board.getAdjList(7, 0);
		assertTrue(testList.contains(board.getCellAt(8, 0)));
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(7, 1)));
		assertEquals(3, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE and BROWN on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test location with only walkways adjacent (light purple)
		LinkedList<BoardCell> testList = board.getAdjList(7, 20);
		assertTrue(testList.contains(board.getCellAt(6, 20)));
		assertTrue(testList.contains(board.getCellAt(8, 20)));
		assertTrue(testList.contains(board.getCellAt(7, 19)));
		assertEquals(3, testList.size());
		
		// Test on left edge of board (brown)
		testList = board.getAdjList(16, 0);
		assertTrue(testList.contains(board.getCellAt(16, 1)));
		assertEquals(1, testList.size());

		// Test on top edge of board (brown)
		testList = board.getAdjList(0, 6);
		assertTrue(testList.contains(board.getCellAt(1, 6)));
		assertEquals(1, testList.size());

		// Test top right corner (brown)
		testList = board.getAdjList(0,20);
		assertTrue(testList.contains(board.getCellAt(0, 19)));
		assertTrue(testList.contains(board.getCellAt(1, 20)));
		assertEquals(2, testList.size());
		
		// Test on bottom edge of board (brown)
		testList = board.getAdjList(20, 5);
		assertTrue(testList.contains(board.getCellAt(19, 5)));
		assertTrue(testList.contains(board.getCellAt(20, 6)));
		assertEquals(2, testList.size());
		
		// Test cells adjacent room but not doorway
		testList = board.getAdjList(6, 2);
		assertTrue(testList.contains(board.getCellAt(6, 1)));
		assertTrue(testList.contains(board.getCellAt(6, 3)));
		assertTrue(testList.contains(board.getCellAt(7, 2)));
		assertEquals(3, testList.size());

		testList = board.getAdjList(18, 14);
		assertTrue(testList.contains(board.getCellAt(17, 14)));
		assertTrue(testList.contains(board.getCellAt(19, 14)));
		assertEquals(2, testList.size());
	}
	
	//Test target list at various distances for DARK BLUE cells
	@Test
	public void testTargets() {
		board.calcTargets(2, 14, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 14)));
		assertTrue(targets.contains(board.getCellAt(3, 14)));
		assertTrue(targets.contains(board.getCellAt(2, 15)));
		
		board.calcTargets(7, 14, 2);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 14)));
		assertTrue(targets.contains(board.getCellAt(6, 15)));	
		assertTrue(targets.contains(board.getCellAt(7, 16)));
		assertTrue(targets.contains(board.getCellAt(8, 15)));
		assertTrue(targets.contains(board.getCellAt(9, 14)));
		assertTrue(targets.contains(board.getCellAt(8, 13)));
		assertTrue(targets.contains(board.getCellAt(7, 12)));
		
		board.calcTargets(15, 12, 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 15)));
		assertTrue(targets.contains(board.getCellAt(15, 9)));	
		assertTrue(targets.contains(board.getCellAt(14, 14)));
		assertTrue(targets.contains(board.getCellAt(16, 14)));
		assertTrue(targets.contains(board.getCellAt(16, 10)));
		assertTrue(targets.contains(board.getCellAt(16, 12)));
		assertTrue(targets.contains(board.getCellAt(15, 13)));
		assertTrue(targets.contains(board.getCellAt(15, 11)));
		assertTrue(targets.contains(board.getCellAt(14, 10)));
		
		
		board.calcTargets(18, 14, 5);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 14)));
		assertTrue(targets.contains(board.getCellAt(15, 16)));	
		assertTrue(targets.contains(board.getCellAt(15, 12)));
		assertTrue(targets.contains(board.getCellAt(16, 11)));
		assertTrue(targets.contains(board.getCellAt(16, 13)));
		assertTrue(targets.contains(board.getCellAt(15, 14)));
		
	}
	
	
	// Test getting into a room
	// These are DARK BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		board.calcTargets(7, 7, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 8)));
		
		board.calcTargets(6, 2, 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 4)));
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		board.calcTargets(14, 5, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 6)));
		assertTrue(targets.contains(board.getCellAt(15, 6)));
		assertTrue(targets.contains(board.getCellAt(14, 7)));
		
		board.calcTargets(4, 15, 1);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 14)));

	}
}