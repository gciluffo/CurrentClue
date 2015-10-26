package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Player;

public class GameSetupTests {

	private static Board board;
	
	
	@BeforeClass
	public static void setUp() 
	{
		board = new Board();
		board.initialize();
	}
	
	
	//Check to make sure that the players info are loaded in correctly
	@Test
	public void playerFormat() {
		
		Player [] players = board.getPlayers();
		
		assertEquals(players.length, 6);
		
		assertEquals(players[0].getPlayerName(), "player1");
		//Check to make sure name is loaded correctly
		assertEquals(players[0].getPlayerColor(), Color.black);
		//checking for right color
		BoardCell cell = board.getCellAt(players[0].getPlayerRow(), players[0].getPlayerColumn());
		assertEquals(cell.getInitial(), 'W');
		assertTrue(cell.isDoorway());
		//Check the player location
		
		
		assertEquals(players[1].getPlayerName(), "Barry");
		//Check to make sure name is loaded correctly
		assertEquals(players[1].getPlayerColor(), Color.blue);
		//checking for right color
		cell = board.getCellAt(players[1].getPlayerRow(), players[1].getPlayerColumn());
		assertEquals(cell.getInitial(), 'W');
		assertTrue(cell.isDoorway());
		//Check the player location
		
		
		assertEquals(players[2].getPlayerName(), "Yolonda");
		//Check to make sure name is loaded correctly
		assertEquals(players[2].getPlayerColor(), Color.yellow);
		//checking for right color
		cell = board.getCellAt(players[2].getPlayerRow(), players[2].getPlayerColumn());
		assertEquals(cell.getInitial(), 'W');
		assertTrue(cell.isDoorway());
		//Check the player location
		
		
		assertEquals(players[3].getPlayerName(), "Greyson");
		//Check to make sure name is loaded correctly
		assertEquals(players[3].getPlayerColor(), Color.green);
		//checking for right color
		cell = board.getCellAt(players[3].getPlayerRow(), players[3].getPlayerColumn());
		assertEquals(cell.getInitial(), 'W');
		assertTrue(cell.isDoorway());
		//Check the player location
		
		
		assertEquals(players[4].getPlayerName(), "Rosa");
		//Check to make sure name is loaded correctly
		assertEquals(players[4].getPlayerColor(), Color.red);
		//checking for right color
		cell = board.getCellAt(players[4].getPlayerRow(), players[4].getPlayerColumn());
		assertEquals(cell.getInitial(), 'W');
		assertTrue(cell.isDoorway());
		//Check the player location
		
		
		assertEquals(players[5].getPlayerName(), "Plank");
		//Check to make sure name is loaded correctly
		assertEquals(players[5].getPlayerColor(), Color.magenta);
		//checking for right color
		cell = board.getCellAt(players[5].getPlayerRow(), players[5].getPlayerColumn());
		assertEquals(cell.getInitial(), 'W');
		assertTrue(cell.isDoorway());
		//Check the player location
	
	}
	
	
	@Test
	public void testCardInput(){
		
		
		
		
		
	}
	
	

}
