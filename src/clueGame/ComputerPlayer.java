package clueGame;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	public BoardCell pickLocation(Set<BoardCell> targets){
		
	
		
		Random random = new Random();
		int randomLocation = random.nextInt(targets.size());
		
		for(BoardCell x: targets) {
			
			if(x.isDoorway() && !visitedDoors.contains(x)){
				visitedDoors.clear();
				visitedDoors.add(x);
				return x;
			}
		}
		
		BoardCell[] A = targets.toArray(new BoardCell[targets.size()]);
		
		
		return A[randomLocation];
	}
	
	public void makeAccusation(){}
	public void makeSuggestion(Board board, BoardCell location){}

}
