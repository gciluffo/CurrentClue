package clueGame;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class InitialzationTests 
{
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLS = 21;
	
	private static Board board;

	@BeforeClass
	public static void setUp() 
	{
		board = new Board();
		board.initialize();
	}

	@Test
	public void testRooms() 
	{
		Map<Character, String> rooms = board.getRooms();
		assertEquals(NUM_ROOMS, rooms.size());
		//First room in file
		assertEquals("Observatory", rooms.get('O'));
		//Last room in file
		assertEquals("Dining Room", rooms.get('D'));
		//A few random rooms
		assertEquals("Storage Room", rooms.get('S'));
		assertEquals("Study", rooms.get('T'));
		assertEquals("Library", rooms.get('I'));
	}
	
	@Test
	public void testBoardDimesions()
	{
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLS, board.getNumColumns());
	}
	
	@Test
	public void FourDoorDirections()
	{
		// Test one each RIGHT/LEFT/UP/DOWN
		BoardCell room = board.getCellAt(18, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(6, 17);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(4, 15);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(9, 11);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getCellAt(10, 18);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(8, 8);
		assertFalse(cell.isDoorway());	
	}
	
	@Test
	public void testNumberOfDoorways()
	{
		int numDoors = 0;
		int totalCells = board.getNumColumns() * board.getNumRows();
		assertEquals(441, totalCells);
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		assertEquals(16, numDoors);
	}
	
	@Test
	public void testRoomInitials()
	{
		assertEquals('T', board.getCellAt(0, 0).getInitial());
		assertEquals('S', board.getCellAt(18, 1).getInitial());
		assertEquals('D', board.getCellAt(17, 10).getInitial());
		assertEquals('B', board.getCellAt(19, 19).getInitial());
		assertEquals('A', board.getCellAt(12, 12).getInitial());
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException 
	{
		Board board = new Board("ClueLayoutBadColumns.csv", "ClueLegend.txt");
		board.loadRoomConfig();
		board.loadBoardConfig();
	}
	
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException 
	{
		Board board = new Board("ClueLayoutBadRoom.csv", "ClueLegend.txt");
		board.loadRoomConfig();
		board.loadBoardConfig();
	}
	
	// Test that an exception is thrown for a bad room config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException 
	{
		Board board = new Board("ClueLayout.csv", "ClueLegendBadFormat.txt");
		board.loadRoomConfig();
	}

}
