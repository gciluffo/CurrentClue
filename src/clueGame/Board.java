package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board 
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
		for(BoardCell b : targets)
		{
			System.out.println(b);
		}
		System.out.println();
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
	
}
