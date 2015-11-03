package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell 
{
	private int row;
	private int column;
	private char initial;
	private DoorDirection dir;
	private int size;
	
	public void draw(Graphics g, int numRow, int numCol){
		
		size = 30;
		
		if(initial=='W'){
		g.setColor(Color.YELLOW);
		g.drawRect(row*size, column*size, size, size);
		}
		else if(isDoorway()){
			g.setColor(Color.BLUE);
			
			switch (dir) {
			case UP:
				
				
				g.drawRect(row*size, column*size, size/8, size);
				
				
				break;
			case DOWN:
				
				break;
				
			case LEFT:
				break;
					
			case RIGHT:
				
				

			default:
				break;
			}
		
		}
		
	}
	
	
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
