package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {

	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards;
	private ArrayList<Card> seenCards;
	
	public Card disproveSuggestion(Solution suggestion){
		return null;}
	
	public String getPlayerName(){
		return playerName;
		
	}
	
	public Color getPlayerColor(){
		return color;
		
	}
	
	public int getPlayerRow(){
		
		return row;
	}
	
	public int getPlayerColumn(){
		
		return column;
		
	}
	
}
