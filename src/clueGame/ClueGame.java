package clueGame;

import javax.swing.JFrame;

public class ClueGame extends JFrame {

	public ClueGame(){
		
		Board board = new Board();
		setSize(700,800);
		board.initialize();
		add(board);
		
	}
	
	
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);

	}

}
