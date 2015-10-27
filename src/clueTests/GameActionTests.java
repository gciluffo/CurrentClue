package clueTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class GameActionTests {
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = new Board();
		board.initialize();
	}
	
	
	/* Test accusation
	 * 
	set solution and then ensure the solution is correct.
	
	The accusation is correct if it contains the correct person,
	weapon and room
	
	The accusation is not correct if the room is wrong, 
	or if the person is wrong, if the weapon is wrong,
	or if all three are wrong.	
	*
	*/
	@Test
	public void testAccusation()
	{
		board.dealCards();
		
		// test actual solution
		Solution test = board.getSolution();
		
		assertTrue(board.checkAccusation(test));
		
		// test random accusation
		test = new Solution();
		test.setPerson("Jon");
		test.setRoom("Bedroom");
		test.setWeapon("Gun");
		
		assertFalse(board.checkAccusation(test));
	}
	
	/* Test selecting target location
	 * This test should ensure that the computer players 
	 * choose a valid target randomly
	 * 
	 * 
	 */
	@Test
	public void testTargetLocation()
	{
		ComputerPlayer player = new ComputerPlayer();
		
		// Test priority of rooms over walkways
		board.calcTargets(7, 0, 2);
		int kitchen = 0;
		for( int i = 0; i < 20; i++ )
		{
			// our player class keeps track of visited doors, clear this set 
			// just for this test scenario
			player.getVisitedDoors().clear();
			BoardCell selected = player.pickLocation(board.getTargets());
			if( selected == board.getCellAt(8, 0))
				kitchen++;
			else
				fail("Room Priority Unsuccessful.");
		}
		assertTrue(kitchen == 20);
		
		// Test random location with no rooms in target.
		// Pick a location with no rooms in target, four targets
		board.calcTargets(11, 7, 2);
		int loc_9_7Tot = 0;
		int loc_11_5Tot = 0;
		int loc_12_6Tot = 0;
		int loc_13_7Tot = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) 
		{
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(9, 7))
				loc_9_7Tot++;
			else if (selected == board.getCellAt(11, 5))
				loc_11_5Tot++;
			else if (selected == board.getCellAt(12, 6))
				loc_12_6Tot++;
			else if (selected == board.getCellAt(13, 7))
				loc_13_7Tot++;
			else
				fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, loc_9_7Tot + loc_11_5Tot + loc_12_6Tot + loc_13_7Tot);
		// Ensure each target was selected more than once
		assertTrue(loc_9_7Tot > 10);
		assertTrue(loc_11_5Tot > 10);
		assertTrue(loc_12_6Tot > 10);
		assertTrue(loc_13_7Tot > 10);
		
		
		
		
		
	}
}
