package clueGame;



	
	

	import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;

	import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

	public class GUI extends JFrame {
		private JTextField name;

		public GUI()
		{
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("Clue");
			setSize(600, 200);
			JPanel panel = createNamePanel();
			add(panel, BorderLayout.NORTH);
			panel = createButtonPanel();
			add(panel, BorderLayout.EAST);
			panel = createGuessPanel();
		}

		
		 private JPanel createNamePanel() {
			 	JPanel panel = new JPanel(new BorderLayout());
			 	name = new JTextField(20);
			 	JLabel nameLabel = new JLabel("Whose turn?");
			 	panel.add(name);
			 	nameLabel.setLabelFor(name);
				panel.add(nameLabel, BorderLayout.NORTH);
				
				return panel;
		}
		 
		private JPanel createButtonPanel() {
			JButton nextPlayer = new JButton("Next player");
			nextPlayer.setBounds(20,5,20,5);
			JButton makeAccusation = new JButton("Make an accusation");
			JPanel panel = new JPanel(new GridLayout(4,4,4,4));
			panel.add(nextPlayer);
			panel.add(makeAccusation);
			return panel;
		}
		
		
		 private JPanel createGuessPanel() {
			 
			 	JPanel panel = new JPanel();
			 	JLabel nameLabel = new JLabel("Guess");
				name = new JTextField(20);
				panel.setLayout(new GridLayout(1,2));
				panel.add(nameLabel);
				panel.add(name);
				panel.setBorder(new TitledBorder (new EtchedBorder(), "Who are you?"));
				return panel;
		}
		
		public static void main(String[] args) {
			GUI gui = new GUI();	
			gui.setVisible(true);
		}


	}


