package clueTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameActionTests {
	
	private static Board board;
	private static Card barryCard;
	private static Card greysonCard;
	private static Card bucketCard;
	private static Card hammerCard;
	private static Card kitchenCard;
	private static Card bedroomCard;
	
	@BeforeClass
	public static void setUp() {
		board = new Board();
		board.initialize();
		barryCard = new Card("Barry", CardType.PERSON);
		greysonCard = new Card("Greyson", CardType.PERSON);
		bucketCard = new Card("Bucket", CardType.WEAPON);
		hammerCard = new Card("Hammer", CardType.WEAPON);
		kitchenCard = new Card("Kitchen", CardType.ROOM);
		bedroomCard = new Card("Bedroom", CardType.ROOM);
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
	
	@Test
	public void testDisproveSuggestion()
	{
		Player [] players = board.getPlayers();
		players[0].addMyCards(barryCard);
		players[0].addMyCards(bucketCard);
		players[0].addMyCards(kitchenCard);
		players[0].addMyCards(greysonCard);
		players[0].addMyCards(hammerCard);
		players[0].addMyCards(bedroomCard);
		
		Solution suggestion = new Solution();
		
		// test for person card
		suggestion.person = barryCard.getCardName();
		suggestion.weapon = null;
		suggestion.room = null;
		
		assertEquals(barryCard.getCardName(),
				players[0].disproveSuggestion(suggestion).getCardName());
		
		// test for weapon card
		suggestion.person = null;
		suggestion.weapon = bucketCard.getCardName();
		suggestion.room = null;
		assertEquals(bucketCard.getCardName(),
				players[0].disproveSuggestion(suggestion).getCardName());
		
		//test for room card
		suggestion.person = null;
		suggestion.weapon = null;
		suggestion.room = kitchenCard.getCardName();
		assertEquals(kitchenCard.getCardName(),
				players[0].disproveSuggestion(suggestion).getCardName());
		
		// test when player can not disprove suggestion
		suggestion.person = null;
		suggestion.weapon = null;
		suggestion.room = null;
		assertTrue(players[0].disproveSuggestion(suggestion) == null );
		
		// test when player has multiple cards that can disprove suggestion
		suggestion.person = barryCard.getCardName();
		suggestion.weapon = hammerCard.getCardName();
		suggestion.room = kitchenCard.getCardName();
		
		int person = 0;
		int weapon = 0;
		int room = 0;
		for( int i = 0; i < 100; i++ )
		{
			Card selected = players[0].disproveSuggestion(suggestion);
			if (selected == barryCard)
				person++;
			else if (selected == hammerCard)
				weapon++;
			else if (selected == kitchenCard)
				room++;
			else
				fail("Invalid card selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, person + weapon + room);
		// Ensure each target was selected more than once
		assertTrue(person > 10);
		assertTrue(weapon > 10);
		assertTrue(room > 10);
		
		// set up array of players with different cards.
		// by default, players[0] is human player. players[1-5] are computers.
		players[0].getMyCards().clear();
		players[0].addMyCards(barryCard);
		players[1].addMyCards(bucketCard);
		players[2].addMyCards(kitchenCard);
		players[3].addMyCards(greysonCard);
		players[4].addMyCards(hammerCard);
		players[5].addMyCards(bedroomCard);
		
		// make suggestion no one can disprove
		suggestion.person = null;
		suggestion.weapon = null;
		suggestion.room = null;
		
		assertTrue(board.handleSuggestion(suggestion, 
				players[0].getPlayerName(), new BoardCell(8,0,'k')) == null);
		
		// make suggestion only one person can disprove. 
		// This person should be checked last
				suggestion.person = barryCard.getCardName();
				suggestion.weapon = null;
				suggestion.room = null;
				
		assertTrue(board.handleSuggestion(suggestion, 
				players[1].getPlayerName(), new BoardCell(8,0,'k')) == barryCard);
		
		// make suggestion only one person can disprove. 
		// But the person who can disprove it made the suggestion
				suggestion.person = barryCard.getCardName();
				suggestion.weapon = null;
				suggestion.room = null;
						
		assertTrue(board.handleSuggestion(suggestion, 
				players[0].getPlayerName(), new BoardCell(8,0,'k')) == null);
		
		// make suggestion 2 people can disprove. 
		// But the person who disproves it comes first in ordering of players
				suggestion.person = barryCard.getCardName();
				suggestion.weapon = bucketCard.getCardName();
				suggestion.room = null;
						
		assertTrue(board.handleSuggestion(suggestion, 
				players[3].getPlayerName(), new BoardCell(8,0,'k')) == barryCard);
		
	}
}
