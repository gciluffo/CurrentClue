import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard 
{
	private Map <BoardCell, LinkedList<BoardCell>> adjacentCells;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	
	public IntBoard(int numRows, int numCols)
	{
		adjacentCells = new HashMap<BoardCell, LinkedList<BoardCell>>();
		grid = new BoardCell[numRows][numCols];
		//Populate the grid with BoardCells
		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				grid[i][j] = new BoardCell(i, j);
			}
		}
		
		//Calculate adjacencies for all BoardCells in grid
		this.calcAdjacencies();
		
	}
	
	public void calcAdjacencies()
	{
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[i].length; j++)
			{
				LinkedList<BoardCell> adj = new LinkedList<BoardCell>();
				//Check if immediately adjacent cells are valid
				if(i > 0) //Up
				{
					adj.add(grid[i-1][j]);
				}
				if(i < grid.length - 1) //Down
				{
					adj.add(grid[i+1][j]);
				}
				if(j > 0) //Left
				{
					adj.add(grid[i][j-1]);
				}
				if(j < grid[i].length - 1) //Right
				{
					adj.add(grid[i][j+1]);
				}
				
				adjacentCells.put(grid[i][j], adj);
			}
		}
	}
	
	public void calcTargets(BoardCell startCell, int pathLength)
	{
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		
		visited.add(startCell);
		this.findAllTargets(startCell, pathLength);
		for(BoardCell b : targets)
		{
			//if(visited.contains(b))
			//{
				//targets.remove(b);
			//}
			//else
			//{
				System.out.println(b);
			//}
		}
		
	}
	
	public void findAllTargets(BoardCell startCell, int numSteps)
	{
		//Set<BoardCell> tar = new HashSet<BoardCell>();
		LinkedList<BoardCell> adj = new LinkedList<BoardCell>(this.getAdjList(startCell));
		for(BoardCell b : visited)
		{
			adj.remove(b);
		}
		for(BoardCell bc : adj)
		{
			visited.add(bc);
			if(numSteps == 1)
			{
				targets.add(bc);
			}
			else
			{
				findAllTargets(bc, numSteps - 1);
			}
			visited.remove(bc);
		}
		
		//return tar;
	}
	
	public Set<BoardCell> getTargets()
	{
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell cell)
	{
		return adjacentCells.get(cell);
	}
	
	public BoardCell getCell(int row, int column)
	{
		return grid[row][column];
	}
}
