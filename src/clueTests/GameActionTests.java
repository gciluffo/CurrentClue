package clueTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

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
		
	}
}
