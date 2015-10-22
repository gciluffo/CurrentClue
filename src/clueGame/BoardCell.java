package clueGame;

public class BoardCell 
{
	private int row;
	private int column;
	private char initial;
	private DoorDirection dir;
	
	public BoardCell(int r, int c, char i)
	{
		row = r;
		column = c;
		initial = i;
		dir = DoorDirection.NONE;
	}
	
	public boolean isWalkway()
	{
		//If the cell is a walkway or a closet return true
		if(initial == 'w' || initial == 'W')
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isRoom()
	{
		//If the cell is a walkway or a closet return false
		if(initial == 'w' || initial == 'W')
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	@Override
	public String toString() 
	{
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", dir=" + dir + "]";
	}

	public void setDoorDirection(DoorDirection dir) 
	{
		this.dir = dir;
	}

	public boolean isDoorway()
	{
		if(dir != DoorDirection.NONE)
		{
			return true;
		}
		
		return false;
	}

	public DoorDirection getDoorDirection() 
	{
		return this.dir;
	}

	public char getInitial() 
	{
		return initial;
	}

	public int getRow() 
	{
		return row;
	}

	public int getColumn() 
	{
		return column;
	}
}
