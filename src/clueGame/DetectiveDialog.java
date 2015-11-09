package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveDialog extends JDialog{
	
	private JComboBox<String> person, room, weapon;
	private JPanel personCheckBoxPanel, roomCheckBoxPanel, weaponCheckBoxPanel, personPanel, roomPanel, weaponPanel;
	
	public DetectiveDialog(Board board)
	{
		JLabel personBoxLabel = new JLabel("Person Guess");
		person = new JComboBox<String>();
		personCheckBoxPanel = new JPanel(new BorderLayout());
		personCheckBoxPanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		
		Player[] players = board.getPlayers();
		personCheckBoxPanel.setLayout(new GridLayout(players.length/2,players.length/2));
		
		for(Player p : players)
		{
			person.addItem(p.getPlayerName());
			personCheckBoxPanel.add(new JCheckBox(p.getPlayerName()));
		}
		
		personPanel = new JPanel(new BorderLayout());
		personPanel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		personPanel.add(person);
	
		
		room = new JComboBox<String>();
		roomCheckBoxPanel = new JPanel(new BorderLayout());
		roomCheckBoxPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		String[] roomNames = board.getRooms().values().toArray(new String[0]);
		roomCheckBoxPanel.setLayout(new GridLayout(roomNames.length/2, roomNames.length/2));
		
		for(String o : roomNames)
		{
			room.addItem(o);
			roomCheckBoxPanel.add(new JCheckBox(o));
		}
		
		roomPanel = new JPanel(new BorderLayout());
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		roomPanel.add(room);
		
		weapon = new JComboBox<String>();
		weaponCheckBoxPanel = new JPanel(new BorderLayout());
		weaponCheckBoxPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		String[] weapons = board.getWeapons();
		weaponCheckBoxPanel.setLayout(new GridLayout(weapons.length/2, weapons.length/2));
		for( String o : weapons )
		{
			weapon.addItem(o);
			weaponCheckBoxPanel.add(new JCheckBox(o));
		}
		
		weaponPanel = new JPanel(new BorderLayout());
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		weaponPanel.add(weapon);
		
		setTitle("Detective Notes");
		setSize(1000, 600);
		setLayout( new GridLayout(3,2));
		add(personCheckBoxPanel);
		add(personPanel);
		add(roomCheckBoxPanel);
		add(roomPanel);
		add(weaponCheckBoxPanel);
		add(weaponPanel);
		
	}
}
