package clueGame;



	
	

	import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextField;

	import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

	public class GUIControlPanel extends JFrame {
		private JTextField name;
		private JTextField die;
		private JTextField guessedName;
		private JTextField guessResult;

		public GUIControlPanel()
		{
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("Clue");
			setSize(800, 200);
			setLayout(new GridLayout(3,2));
			JPanel panel = createNamePanel();
			add(panel, BorderLayout.WEST);
			panel = createButtonPanel();
			add(panel, BorderLayout.EAST);
			panel = createDicePanel();
			add(panel, BorderLayout.SOUTH);
			panel = createGuessPanel();
			add(panel, BorderLayout.NORTH);
			panel = createGuessResponsePanel();
			add(panel, BorderLayout.CENTER);
			
			
			
		}

		
		 private JPanel createNamePanel() {
			 	JPanel panel = new JPanel(new BorderLayout());
			 	name = new JTextField(20);
			 	JLabel nameLabel = new JLabel("Whose turn?");
			 	panel.add(name);
			 	nameLabel.setLabelFor(name);
			 	nameLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameLabel.getMinimumSize().height));
		        add(nameLabel);
				panel.add(nameLabel, BorderLayout.NORTH);
				
				return panel;
		}
		 
		private JPanel createButtonPanel() {
			JButton nextPlayer = new JButton("Next player");
			//nextPlayer.setBounds(20,5,20,5);
			JButton makeAccusation = new JButton("Make an accusation");
			JPanel panel = new JPanel(new GridLayout(1,2));
			panel.add(nextPlayer);
			panel.add(makeAccusation);
			return panel;
		}
		
		
		 private JPanel createGuessPanel() {
			 JPanel panel = new JPanel(new BorderLayout());
			 	guessedName = new JTextField(20);
			 	JLabel guessedLabel = new JLabel("Guess");
			 	panel.add(guessedName);
			 	guessedLabel.setLabelFor(guessedName);
			 	guessedLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, guessedLabel.getMinimumSize().height));
		        add(guessedLabel);
		        panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
				panel.add(guessedLabel, BorderLayout.NORTH);
				
				return panel;
			 	
		}
		 
		 private JPanel createDicePanel() {
			 	JPanel panel = new JPanel(new BorderLayout());
			 	die = new JTextField(1);
			 	die.setEditable(false);
			 	JLabel nameLabel = new JLabel("Roll  ");
			 	panel.add(die);
			 	nameLabel.setLabelFor(die);
				panel.add(nameLabel, BorderLayout.WEST);
				panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
				
				return panel;
		}
		 
		 
		 private JPanel createGuessResponsePanel() {
			 JPanel panel = new JPanel(new BorderLayout());
			 	guessResult = new JTextField(20);
			 	JLabel guessedResultLabel = new JLabel("Response  ");
			 	panel.add(guessResult);
			 	guessedResultLabel.setLabelFor(guessResult);
			 	guessedResultLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, guessedResultLabel.getMinimumSize().height));
		        add(guessedResultLabel);
		        panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
				panel.add(guessedResultLabel, BorderLayout.WEST);
				
				return panel;
			 	
		}
		
		public static void main(String[] args) {
			GUIControlPanel gui = new GUIControlPanel();	
			gui.setVisible(true);
		}


	}


