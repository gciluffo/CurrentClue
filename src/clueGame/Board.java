package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;


public class Board extends JPanel
{
	private int numRows = 0;
	private int numColumns = 0;
	public static int BOARD_SIZE;
	private BoardCell board[][];
	private Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String boardConfigFile;
	private String roomConfigFile;
	private Player players[];
	private ArrayList<Card> deck;
	private static Solution answer;

	
	@Override
	public void paintComponent(Graphics g)
	{
		 super.paintComponent(g);
		 
		 for (int i = 0; i < numRows; i++)
		 {
			 for (int j = 0; j < numColumns; j++) 
			 {
				 
				 board[i][j].draw(g, this);
				
			 }
		 }
	}
	
	
	
	
	public Board()
	{
		boardConfigFile = "ClueBoardCSV.csv";
		roomConfigFile = "ClueBoardLegend.txt";
		rooms = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, LinkedList<BoardCell>>();
		targets = new HashSet<BoardCell>();
	}

	public Board(String boardFile, String boardLegend)
	{
		boardConfigFile = boardFile;
		roomConfigFile = boardLegend;
		rooms = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, LinkedList<BoardCell>>();
		targets = new HashSet<BoardCell>();
	}

	public void initialize()
	{
		try 
		{
			this.loadRoomConfig();
			this.loadBoardConfig();
			this.calcAdjacencies();
			loadPlayerFiles();
			loadCards();

		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found at all!");
		} 
		catch (BadConfigFormatException e) 
		{
			System.out.println(e.getMessage());
		}

	}

	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException
	{
		Reader file = new FileReader(roomConfigFile);
		Scanner in = new Scanner(file);

		String line = "";
		String separator = ", ";
		String [] description;

		while(in.hasNextLine())
		{
			line = in.nextLine();
			description = line.split(separator);
			if(description.length != 3)
			{
				throw new BadConfigFormatException("Room config file");
			}
			rooms.put(description[0].charAt(0), description[1]);
		}

		try 
		{
			file.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		in.close();
	}

	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException
	{
		Reader tempFile = new FileReader(boardConfigFile);
		Scanner tempIn = new Scanner(tempFile);

		String line = "";
		String separator = ",";
		String [] tempCells;

		while(tempIn.hasNextLine())
		{
			numRows++;
			if(numColumns == 0)
			{
				line = tempIn.nextLine();
				tempCells = line.split(separator);
				numColumns = tempCells.length;
			}
			tempIn.nextLine();
		}
		numRows++;


		try 
		{
			tempFile.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		tempIn.close();


		board = new BoardCell[numRows][numColumns];

		Reader file = new FileReader(boardConfigFile);
		Scanner in = new Scanner(file);
		int rowNum = 0;
		int colNum = 0;
		String[] cells;

		while(in.hasNextLine())
		{
			line = in.nextLine();
			cells = line.split(separator);
			for(String s : cells)
			{
				if(s.length() > 2)
				{
					throw new BadConfigFormatException("Separators in board config file");
				}
				else if(!rooms.containsKey(s.charAt(0)))
				{
					throw new BadConfigFormatException("Invalid room initial " + s.charAt(0) + " in board config file");
				}
				else if (cells.length != numColumns)
				{
					throw new BadConfigFormatException("Invalid row length in board config file");
				}
				board[rowNum][colNum] = new BoardCell(rowNum, colNum, s.charAt(0));
				if(s.length() > 1)
				{
					switch(s.charAt(1))
					{
					case 'u':
					case 'U':
						board[rowNum][colNum].setDoorDirection(DoorDirection.UP);
						break;
					case 'd':
					case 'D':
						board[rowNum][colNum].setDoorDirection(DoorDirection.DOWN);
						break;
					case 'r':
					case 'R':
						board[rowNum][colNum].setDoorDirection(DoorDirection.RIGHT);
						break;
					case 'l':
					case 'L':
						board[rowNum][colNum].setDoorDirection(DoorDirection.LEFT);
						break;
					case 'n':
					case 'N':
						board[rowNum][colNum].setDoorDirection(DoorDirection.NONE);
						break;
					case 'h':
					case 'H':
						board[rowNum][colNum].setNameHere(true);
						break;
					default:
						throw new BadConfigFormatException("Invalid door direction in board config file");
					}
				}
				colNum++;
			}
			rowNum++;
			colNum = 0;
		}
		try 
		{
			file.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		in.close();

	}

	public void calcAdjacencies()
	{
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				LinkedList<BoardCell> adj = new LinkedList<BoardCell>();
				//If a cell is a room it should have no adjcencies
				if(board[i][j].isRoom() && !board[i][j].isDoorway())
				{
					adjMatrix.put(board[i][j], adj);
				}
				else
				{
					//Check if immediately adjacent cells are valid
					if(i > 0) //Up
					{
						//Cell must be a walkway or a doorway
						if(board[i-1][j].isWalkway() || (board[i-1][j].isDoorway() && board[i-1][j].getDoorDirection() == DoorDirection.DOWN))
						{
							adj.add(board[i-1][j]);
						}
					}
					if(i < board.length - 1) //Down
					{
						if(board[i+1][j].isWalkway() || (board[i+1][j].isDoorway() && board[i+1][j].getDoorDirection() == DoorDirection.UP))
						{
							adj.add(board[i+1][j]);
						}
					}
					if(j > 0) //Left
					{
						if(board[i][j-1].isWalkway() || (board[i][j-1].isDoorway() && board[i][j-1].getDoorDirection() == DoorDirection.RIGHT))
						{
							adj.add(board[i][j-1]);
						}
					}
					if(j < board[i].length - 1) //Right
					{
						if(board[i][j+1].isWalkway() || (board[i][j+1].isDoorway() && board[i][j+1].getDoorDirection() == DoorDirection.LEFT))
						{
							adj.add(board[i][j+1]);
						}
					}

					adjMatrix.put(board[i][j], adj);
				}

			}
		}
	}

	public void calcTargets(int row, int column, int pathLength)
	{
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();

		BoardCell startCell = getCellAt(row, column);

		visited.add(startCell);
		this.findAllTargets(startCell, pathLength);
//		for(BoardCell b : targets)
//		{
//			System.out.println(b);
//		}
//		System.out.println();
	}

	public void findAllTargets(BoardCell startCell, int numSteps)
	{
		int r = startCell.getRow();
		int c = startCell.getColumn();

		LinkedList<BoardCell> adj = new LinkedList<BoardCell>(this.getAdjList(r, c));
		for(BoardCell b : visited)
		{
			adj.remove(b);
		}
		for(BoardCell bc : adj)
		{
			visited.add(bc);
			if(numSteps == 1 || bc.isDoorway())
			{
				targets.add(bc);
			}
			else
			{
				findAllTargets(bc, numSteps - 1);
			}
			visited.remove(bc);
		}
	}





	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}


	public void loadPlayerFiles(){

		@SuppressWarnings("resource")
		FileReader reader = null;
		try {
			reader = new FileReader("players.txt");
			Scanner s = new Scanner(reader).useDelimiter(", ");
			players = new Player[6];
			for( int i = 0; i < 6; i++ )
			{				
				if( i == 0 )
					players[i] = new HumanPlayer();
				else
					players[i] = new ComputerPlayer();
			}
			int count = 0;
			while (s.hasNext()) {
				players[count].setPlayerName(s.next());
				players[count].setColor(convertColor(s.next()));
				players[count].setRow(s.nextInt());
				String temp = s.nextLine();
				temp = temp.replace(", ", "");
				players[count].setColumn(Integer.parseInt(temp));
				count++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void loadCards(){

		@SuppressWarnings("resource")
		FileReader reader = null;
		try {
			reader = new FileReader("Cards.txt");
			Scanner s = new Scanner(reader).useDelimiter(", ");
			int count = 0;
			deck = new ArrayList<Card>();
			while (s.hasNext()) {
				deck.add(new Card());
				deck.get(count).setCardName(s.next());
				String temp = s.nextLine();
				temp = temp.replace(", ", "");
				deck.get(count).setCardType(CardType.fromString(temp));
				count++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void selectAnswer()
	{
		answer = new Solution();
		// get random player
		int playerIndex = (int) (Math.floor(Math.random() * 6));
		if(deck.get(playerIndex).getCardType() == CardType.PERSON){
			answer.setPerson(deck.get(playerIndex).getCardName());
			deck.remove(playerIndex);
		}
		
		// get random weapon
		int weaponIndex = (int) (Math.floor(Math.random() * 6) + 5);
		if(deck.get(weaponIndex).getCardType() == CardType.WEAPON){
			answer.setPerson(deck.get(weaponIndex).getCardName());
			deck.remove(weaponIndex);
		}
		
		
		// get random room
		int roomIndex = (int) (Math.floor(Math.random() * 9) + 10);
		if(deck.get(roomIndex).getCardType() == CardType.ROOM){
			answer.setPerson(deck.get(roomIndex).getCardName());
			deck.remove(roomIndex);
		}
	}
	
	public void dealCards(){
		
		selectAnswer();
		
		Collections.shuffle(deck);
		
		while(deck.size() != 0){
			
			for(int i=0; i < players.length; i++){
				
				if(deck.size() != 0){
				players[i].addMyCards(deck.get(0));
				deck.remove(0);
				}
				else
					break;
				
			}
			
			
		}

	}
	
	public Card handleSuggestion(Solution suggestion, String accusingPlayer, BoardCell clicked) {
		
		int pos = 0;
		for (int i = 0; i < players.length; i++) {
			if(players[i].getPlayerName() == accusingPlayer)
				pos = i;
			}
		
		
		for (int i = (pos + 1) % players.length; i < players.length - 1 + (pos + 1) % players.length; i++) {
			
			if(players[i%players.length].disproveSuggestion(suggestion) != null)
				return players[i%players.length].disproveSuggestion(suggestion);
			
			
		}
		
		
		
		
		return null;}
	
	public boolean checkAccusation(Solution accusation){
		if( accusation.equals(answer) )
			return true;
		else 
			return false;
	}


	public ArrayList<Card> getDeck(){

		return deck;

	}

	public Player [] getPlayers(){
		return players;

	}
	public BoardCell getCellAt(int row, int column)
	{
		return board[row][column];
	}

	public int getNumRows() 
	{
		return numRows;
	}

	public int getNumColumns() 
	{
		return numColumns;
	}

	public Map<Character, String> getRooms() 
	{
		return rooms;
	}

	public Map<BoardCell, LinkedList<BoardCell>> getAdjMatrix() 
	{
		return adjMatrix;
	}

	public Set<BoardCell> getTargets() 
	{
		return targets;
	}

	public LinkedList<BoardCell> getAdjList(int row, int col) 
	{
		return adjMatrix.get(getCellAt(row, col));
	}

	public Solution getSolution() {
		return answer;
	}



}
