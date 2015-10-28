package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {

	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards;
	private ArrayList<Card> seenCards;
	protected Set<BoardCell> visitedDoors;
	
	
	public Player() {
		myCards = new ArrayList<Card>();
		visitedDoors = new HashSet<BoardCell>();
	}

	public Card disproveSuggestion(Solution suggestion){
		
		
		ArrayList<Card> cardMatches = new ArrayList<Card>();
		for (int i = 0; i < myCards.size(); i++) {
			
			if(myCards.get(i).getCardName() == suggestion.person)
				cardMatches.add(myCards.get(i));
			if(myCards.get(i).getCardName() == suggestion.weapon)
				cardMatches.add(myCards.get(i));
			if(myCards.get(i).getCardName() == suggestion.room)
				cardMatches.add(myCards.get(i));
			
		}
		
		if(cardMatches.size() > 1){
			Random randNum = new Random();
			int randomCard = randNum.nextInt(cardMatches.size());
			return cardMatches.get(randomCard);
			
		}
		else if(cardMatches.size() == 0)
			return null;
		else
		return cardMatches.get(0);
	}
	
	
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
	
	public Set<BoardCell> getVisitedDoors(){
		
		return visitedDoors;
	}
}
