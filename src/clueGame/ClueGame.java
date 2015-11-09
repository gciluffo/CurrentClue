package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGame extends JFrame {
	private Board board;
	private DetectiveDialog dialog;
	public ClueGame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		board = new Board();
		board.initialize();
		setSize(board.getCellAt(0, 0).getSize()*board.getNumRows(), 
				board.getCellAt(0, 0).getSize()*board.getNumColumns() 
				+ board.getCellAt(0, 0).getSize()*7/10
				+ menuBar.getPreferredSize().height);
		add(board);
		
	}
	
	
	class MenuItemListener implements ActionListener
	{
		private String itemName;
		
		MenuItemListener(String itemName)
		{
			this.itemName = itemName;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (itemName) {
			case "Exit":
				System.exit(0);
				break;
			case "Detective Notes":
				dialog = new DetectiveDialog(board);
				dialog.setVisible(true);
				break;
			default:
				break;
			}
			
		}
		
	}
	
	
	
	
	private JMenuItem createFileExitItem()
	{
		JMenuItem item = new JMenuItem("Exit");
		item.addActionListener(new MenuItemListener(item.getText()));
		return item;
	}
	
	private JMenuItem createFileDetNotesItem()
	{
		JMenuItem item = new JMenuItem("Detective Notes");
		item.addActionListener(new MenuItemListener(item.getText()));
		return item;
	}
	
	private JMenu createFileMenu()
	{
		JMenu menu = new JMenu("File");
		menu.add(createFileDetNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}
	
	
	
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);

	}

}
