import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PianoMan extends JFrame implements KeyListener, ActionListener {
	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	private static final int NUMBER_OF_LINES = 10;
	private static final int NUMBER_OF_NOTES = 10;
	private static final int NOTE_TIME = 500;
	
	static File file;
	static Scanner scn;
	static PrintWriter pw;
	
	private Sequencer sequencer = null;
	public int keyCode;

	private int octave = 5;
	private int code;
	private boolean keyPressed = false;
	private boolean writing = true;
	private int currentLength = 0;
	private int currentLines = 0;

	public JTextArea sheetMusic = new JTextArea();
	public JButton recordButton = new JButton("Record");
	public JButton playButton = new JButton("Play");
	public JButton resetButton = new JButton("Reset");
	public JButton setButton = new JButton("Set");
	public JLabel result = new JLabel("Piano Man");
	public JLabel octaveLabel = new JLabel("Octive:", SwingConstants.CENTER);
	public JTextField octaveInput = new JTextField(9);
	public JPanel buttonSetup = new JPanel();
	public JPanel octaveLayout2 = new JPanel();
	public JOptionPane messageLabel = new JOptionPane();
	
	
	public int noteRef = 0;
	public boolean isRecording = false;
	public int[] recording = new int[100];
	public int[] saveList = new int[100]; 


	// Load and save functions
	public JMenu fileMenu = new JMenu("File");
	public JMenuItem save = new JMenuItem("Save");
	public JMenuItem Load = new JMenuItem("Load");
	public JMenuBar bar = new JMenuBar();

	public static void main(String[] args) {
		PianoMan gui = new PianoMan();
		gui.setVisible(true);
	}

	public PianoMan() {
		setTitle("Piano Man");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		for (int i = 0; i < 100; i++) {
			recording[i] = 0;
		} // MORE GUI SETUP

		buttonSetup.setLayout(new GridLayout(2, 3));

		buttonSetup.add(octaveLabel);
		buttonSetup.add(octaveInput);
		buttonSetup.add(setButton);

		buttonSetup.add(recordButton);
		buttonSetup.add(playButton);
		buttonSetup.add(resetButton);
		fileMenu.add(Load);
		fileMenu.add(save);
		bar.add(fileMenu);
		setJMenuBar(bar);

		setButton.addActionListener(this);
		playButton.addActionListener(this);
		recordButton.addActionListener(this);
		resetButton.addActionListener(this);
		save.addActionListener(this);
		Load.addActionListener(this);

		add(result, BorderLayout.NORTH);
		add(buttonSetup, BorderLayout.SOUTH);
		add(sheetMusic, BorderLayout.CENTER);

		sheetMusic.setEditable(false);
		sheetMusic.addKeyListener(this);
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error creating sequencer.");
			System.out.println("Error creating sequencer.");
			System.exit(0);
		}
		sheetMusic.requestFocus();
	}

	// code to string 
	public String codeToNote(int code) {
		String note = "";

		if (code >= 1 && code <= 127) {
			if (code % 12 == 0) {
				note = "C";
			} else if (code % 12 == 1) {
				note = "C#";
			} else if (code % 12 == 2) {
				note = "D";
			} else if (code % 12 == 3) {
				note = "D#";
			} else if (code % 12 == 4) {
				note = "E";
			} else if (code % 12 == 5) {
				note = "F";
			} else if (code % 12 == 6) {
				note = "F#";
			} else if (code % 12 == 7) {
				note = "G";
			} else if (code % 12 == 8) {
				note = "G#";
			} else if (code % 12 == 9) {
				note = "A";
			} else if (code % 12 == 10) {
				note = "A#";
			} else if (code % 12 == 11) {
				note = "B";
			}
		} else
			note = "error";

		return note;

		// TODO this should return a string value for the note that is played
	}
	private void playNote(int note) throws Exception {
		Sequence sequence = new Sequence(Sequence.PPQ, 4);
		Track track = sequence.createTrack();

		ShortMessage sm1 = new ShortMessage(144, 1, note, 100);
		MidiEvent noteOn = new MidiEvent(sm1, 1);
		track.add(noteOn);

		ShortMessage sm2 = new ShortMessage(128, 1, note, 100);
		MidiEvent noteOff = new MidiEvent(sm2, 16);
		track.add(noteOff);

		sequencer.setSequence(sequence);
		sequencer.start();
		waitForNote();
	}
	private void waitForNote() {
		long start = System.currentTimeMillis();
		while ((System.currentTimeMillis() - start) < NOTE_TIME) {
		}
		return;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		keyPressed(e);
		if (keyCode == 65 || keyCode == 83 || keyCode == 68 || keyCode == 70 || keyCode == 71 || keyCode == 72
				|| keyCode == 74 || keyCode == 75 || keyCode == 76 || keyCode == 59 || keyCode == 222
				|| keyCode == 10) {
			// a
			if (keyCode == 65) {
				code = 0 + octave * 12;
				result.setText("Note: C Key Pressed: a     Code: " + keyCode);
			}
			// s
			else if (keyCode == 83) {
				code = 1 + octave * 12;
				result.setText("Note: C# Key Pressed: s     Code: " + keyCode);
			}
			// d
			else if (keyCode == 68) {
				code = 2 + octave * 12;
				result.setText("Note: D Key Pressed: d     Code: " + keyCode);
			}
			// f
			else if (keyCode == 70) {
				code = 3 + octave * 12;
				result.setText("Note: D# Key Pressed: f     Code: " + keyCode);
			}
			// g
			else if (keyCode == 71) {
				code = 4 + octave * 12;
				result.setText("Note: E	Key Pressed: g     Code: " + keyCode);
			}
			// h
			else if (keyCode == 72) {
				code = 5 + octave * 12;
				result.setText("Note: F	Key Pressed: h     Code: " + keyCode);
			}
			// j
			else if (keyCode == 74) {
				code = 6 + octave * 12;
				result.setText("Note: F# Key Pressed: j    Code: " + keyCode);
			}
			// k
			else if (keyCode == 75) {
				code = 7 + octave * 12;
				result.setText("Note: G	Key Pressed: k    Code: " + keyCode);
			}
			// l
			else if (keyCode == 76) {
				code = 8 + octave * 12;
				result.setText("Note: G# Key Pressed: l    Code: " + keyCode);
			}
			// ;
			else if (keyCode == 59) {
				code = 9 + octave * 12;
				result.setText("Note: A Key Pressed: ;    Code: " + keyCode);
			}
			// '
			else if (keyCode == 222) {
				code = 10 + octave * 12;
				result.setText("Note: A# Key Pressed: '    Code: " + keyCode);
			}
			// (enter)
			else if (keyCode == 10) {
				code = 11 + octave * 12;
				result.setText("Note: B	Key Pressed: (Enter)  Code: " + keyCode);
			}
			// checks length to create new line or not 
			try {
				if (currentLength >= NUMBER_OF_NOTES) {

					sheetMusic.append("\n");
					currentLines++;
					currentLength = 0;

				}

				if (currentLines < NUMBER_OF_LINES) {
					if (isRecording) {
						recording[noteRef] = code;
						noteRef++;
					}
					currentLength++;
					playNote(code);
					sheetMusic.append(codeToNote(code));

				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		if (e.getKeyCode() != 0) {
			keyCode = e.getKeyCode();
		}
		System.out.println(keyCode);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyPressed = false;
	}
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals("Save"))
		{
			String name = JOptionPane.showInputDialog(null, "What would you like to name the file?", null, 
							JOptionPane.QUESTION_MESSAGE); 
		
		//make sure it is in the txt file
		if(!name.endsWith(".txt"))
		{
			name = name + ".txt"; 
		}
		
		File mySave = new File(name); 
		int choice = JOptionPane.YES_OPTION; 
		if (mySave.exists())
		{
			choice = JOptionPane.showConfirmDialog(null, "This file already exist. Overwrite?", null, 
							JOptionPane.YES_NO_OPTION); 
		}
		
		//checks if user selects yes 
		
		if(choice == JOptionPane.YES_OPTION)
		{
			//this attempts to save as a list of int values of keys saving octaves 
			
			try
			{
				PrintWriter pw = new PrintWriter(mySave); 
				for(int i = 0; i < saveList.length; i++)
				{
					pw.write(Integer.toString(saveList[i]));
					pw.println();
				}
				
				pw.flush();
				pw.close();
			}
			catch (Exception x)
			{
				System.out.println("Could not Save.");
			}
		}
	}
		//Load
	else if(actionCommand.equals("Load"))
		{
					String name = JOptionPane.showInputDialog(null, "What file would you like to load?", null, 
									JOptionPane.QUESTION_MESSAGE); 
				
				//make sure it is in the txt file
			if(!name.endsWith(".txt"))
			{
					name = name + ".txt"; 
			}
				
			File myLoad = new File(name); 
			int choice = JOptionPane.YES_OPTION; 
			if (!myLoad.exists())
			{
				choice = JOptionPane.showConfirmDialog(null, "This file doesn't exist.", null, JOptionPane.CLOSED_OPTION); 
			}
					
				
				//checks if user selects yes 
				
			if(choice == JOptionPane.YES_OPTION)
				{
				//this attempts to save as a list of int values of keys saving octaves 
				
				try
				{
					sheetMusic.setText("");
					int i = 0; 
					Scanner scan = new Scanner(myLoad); 
					while(scan.hasNextLine())
					{
						saveList[i] = Integer.parseInt(scan.nextLine()); 
						i++; 
					}
					currentLength = 0; 
					currentLines = 0; 
					for(int j = 0; j < saveList.length; j++)
					{
						recording[j] = saveList[j]; 
					
						if(currentLength >= NUMBER_OF_NOTES)
						{
							sheetMusic.append("\n");
							currentLines++; 
							currentLength = 0; 
						}
						if(currentLines < NUMBER_OF_LINES)
						{
							if(saveList[j] != 0)
							{
								sheetMusic.append(codeToNote(saveList[j]));
								currentLength++; 
							}
						}
					}
					scan.close();
				}
				catch (Exception x)
				{
					System.out.println("Could not Load.");
				}
			}
		}

// if int is > 0 or < 10 sets octave as number
else if (actionCommand.equals("Set")) {
	try {
		if (Integer.parseInt(octaveInput.getText()) > 0 && Integer.parseInt(octaveInput.getText()) < 10) {
			octave = Integer.parseInt(octaveInput.getText());
			octaveInput.setText("");
		} else
			System.out.println("Enter a number 1-9");
	} catch (Exception ex) {
		System.out.println("Enter a number 1-9");

	}
} else if (actionCommand.equals("Reset")) {
	sheetMusic.setText("");
	octave = 5;
	isRecording = true;
	result.setText("Reset + Recording.");
	currentLength = 0;
	currentLines = 0;
	for (int i = 0; i < recording.length; i++) {
		recording[i] = 0;
		noteRef = 0;
	}
} else if (actionCommand.equals("Record")) {
	isRecording = true;
	result.setText("Recording");
} else if (actionCommand.equals("Play")) {
	for (int i = 0; i < recording.length; i++) {
		if (recording[i] != 0) {
			try {
				playNote(recording[i]);

			} catch (Exception e1) {

				e1.printStackTrace();
			}
		}
	}
}
}
}
