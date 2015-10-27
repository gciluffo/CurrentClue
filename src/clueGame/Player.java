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
	
	
	
	public Player() {
		myCards = new ArrayList<Card>();
	}

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
	
	public ArrayList<Card> getMyCards(){
		
		return myCards;
		
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void addMyCards(Card acard){
		
		myCards.add(acard);
		
	}
}
