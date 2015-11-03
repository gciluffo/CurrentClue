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
	private boolean nameHere = false;
	
	public void setNameHere(boolean nameHere) {
		this.nameHere = nameHere;
	}


	public void draw(Graphics g, String room){
		
		size = 30;
		
		if(initial=='W'){
		g.setColor(Color.YELLOW);
		g.fillRect(column*size, row*size, size, size);
		g.setColor(Color.BLACK);
		g.drawRect(column*size, row*size, size, size);
		}
		else if(initial == 'X'){
			g.setColor(Color.RED);
			g.fillRect(column*size, row*size, size, size);
		}
			
		else if(isDoorway()){
			g.setColor(Color.GRAY);
			g.fillRect(column*size, row*size, size, size);
			
			g.setColor(Color.BLUE);
			
			switch (dir) {
			case UP:
				g.fillRect(column*size, row*size, size, size/8);
				break;
			case DOWN:
				g.fillRect(column*size, (row+1)*size - size/8, size, size/8);
				break;	
			case LEFT:
				g.fillRect(column*size, row*size, size/8, size);
				break;	
			case RIGHT:
				g.fillRect((column+1)*size - size/8, row*size, size/8, size);
				break;
			default:
				break;
			}
		}
		else {
			g.setColor(Color.GRAY);
			g.fillRect(column*size, row*size, size, size);
		}
		
		if(nameHere)
		{
			g.setColor(Color.BLUE);
			g.drawString(room.toUpperCase(), column*size, row*size);
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
