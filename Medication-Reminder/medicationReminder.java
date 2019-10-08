import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;


public class medicationReminder extends JFrame  implements ActionListener {
	public static final int WIDTH = 400;
	public static final int HEIGHT = 600;
	private JLabel enterNameLbl;
	private JTextField enterName;
	private JLabel enterNumofTimeLbl;
	private JTextField enterNumofTime;
	private JButton enterNumofTimeBtn;
	private JLabel enterTimeLbl;
	private JLabel enterTimeExLbl;
	private JTextField enterTime;
	
	
	private JLabel enterInfoLBl;
	private JTextArea enterInfo;
	private JLabel title;
	private JTextArea info; 
	private JButton addMedBTN;
	private JButton EnterMed;
	private JComboBox listOfMeds;
	private JTextArea listOfMedsTextArea;
	private JButton listofMedBTN;
	private JLabel timeLbl;
	private JLabel numTakenLbl;
	private JLabel nameLbl;
	public static int hour;
	public static int min;
	public static int sec;
	public static int AMorPM;
	public static int day;
	public static boolean enteringMed=false; 
	public static int numMed=1;
	public JLabel clock;
	public static JLabel dosageLbl;
	public static JTextField enterDosage;
	public static JLabel dosageLblBottom;
	
	
	
	
	
	
	private static List<medication> medList = new ArrayList<medication>();
	private static List<String> medNameList = new ArrayList<String>();
	
	
	public static void main(String[] args){
		
		medicationReminder x = new medicationReminder();
		x.setVisible(true);
		time();
		
		
		
	}
	/*
	 * This constructor sets up the GUI with all the necessary features 
	 */
	public medicationReminder(){
		setTitle("Medication Reminder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH,HEIGHT);
		setLayout(new BorderLayout());
		
		
		JPanel TitlePanel = new JPanel();
		TitlePanel.setLayout(new FlowLayout());
		title = new JLabel("Medication Reminder");
		TitlePanel.add(title);
		
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		
		
		JPanel enterNamePanel = new JPanel();
		enterNamePanel.setLayout(new FlowLayout());
		Box box = Box.createVerticalBox();
		
		EnterMed = new JButton("Enter new Medication");
		EnterMed.setAlignmentX(JButton.CENTER_ALIGNMENT);
		EnterMed.addActionListener(this);
		box.add(EnterMed);
		enterNameLbl = new JLabel("Enter the name of the Medication");
		enterName = new JTextField("",10);
		enterName.setEditable(false);
		
		enterNamePanel.add(enterNameLbl);
		enterNamePanel.add(enterName);
		box.add(enterNamePanel);
		
		
		
		
		JPanel enterTimePanel = new JPanel();
		enterTimePanel.setLayout(new FlowLayout());
		enterTimeLbl = new JLabel("Enter the time(s) that you have to take the Medication");
		JPanel enterTimeExPanel = new JPanel();
		enterTimeExPanel.setLayout(new FlowLayout());
		enterTimeExLbl = new JLabel("Ex: 10:45 am (Put a / between each time)");
		enterTimeExPanel.add(enterTimeExLbl);
		enterTime= new JTextField();
		enterTime.setEnabled(false);;
		
		
		enterTimePanel.add(enterTimeLbl);
		
		box.add(enterTimePanel);
		box.add(enterTimeExPanel);
		box.add(enterTime);
		
		Box enterInfoBox = Box.createVerticalBox();
		enterInfoLBl = new JLabel("Enter any other info about the medication");
		enterInfoLBl.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		enterInfoBox.add(enterInfoLBl);
		
		enterInfo = new JTextArea("",5, 5);
		enterInfo.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		enterInfo.setEditable(false);
		enterInfoBox.add(enterInfo);
		box.add(enterInfoBox);
		
		JPanel enterDosagePanel  = new JPanel();
		enterDosage = new JTextField("",5);
		dosageLbl = new JLabel("Enter the amount of medciation you have to take in mg: ");
		enterDosage.setEditable(false);
		enterDosagePanel.add(dosageLbl);
		enterDosagePanel.add(enterDosage);
		
		box.add(enterDosagePanel);
		
		
		JPanel x = new JPanel();
		addMedBTN = new JButton("Add Medication");
		addMedBTN.setAlignmentX(JButton.CENTER_ALIGNMENT);
		addMedBTN.addActionListener(this);
		
	
		x.add(addMedBTN);
		
		box.add(x);
		
		bottomPanel.add(box);
		
		Box medListArea = Box.createVerticalBox();
		JPanel listMedPanel = new JPanel();
		listOfMeds = new JComboBox();
		
		listofMedBTN= new JButton("Select");
		listofMedBTN.addActionListener(this);
		listMedPanel.add(listOfMeds);
		listMedPanel.add(listofMedBTN);
		
		
		medListArea.add(listMedPanel);
		
		nameLbl = new  JLabel("Name: ");
		numTakenLbl = new JLabel("Number of times taken: ");
		timeLbl = new JLabel("Time medication has to be taken: ");
		dosageLblBottom = new JLabel("The amount of medication you have to take: ");
		
		medListArea.add(nameLbl);
		medListArea.add(timeLbl);
		medListArea.add(numTakenLbl);
		medListArea.add(dosageLblBottom);
		
		listOfMedsTextArea = new JTextArea("",5,5);
		medListArea.add(listOfMedsTextArea);
		
		
		
		
		
		add(TitlePanel, BorderLayout.NORTH);
		add(bottomPanel,BorderLayout.CENTER);
		add(medListArea,BorderLayout.SOUTH);
		
		
		
	}
	/*
	 * This method detects which button is pressed and then performs the correct  action
	 * according to the button that was pressed
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
		List<String>Time = new ArrayList<String>();
		String actionComand = arg0.getActionCommand();
		//Enter new Medication button was pressed: sets of the textField to Editable
		if(actionComand.equals("Enter new Medication")){
			enteringMed=true;
			enterName.setEditable(true);
			enterTime.setEditable(true);
			enterInfo.setEditable(true);
			enterDosage.setEditable(true);
			
		//The add Medication Pressed: this takes all the texts from the textfields and puts them into a 
		//medication class 
		}else if(actionComand.equals("Add Medication")){
			String time = enterTime.getText().replaceAll("\\s","");
			String medName = enterName.getText();
			medNameList.add(medName);
			String medInfo = enterInfo.getText();
			
			String dosage = enterDosage.getText();
			String hour ="";
			String min= "";
			boolean hourOrMin = true;
			String temp="";
	
			for(int i =0;i<time.length();i++){
				if(time.charAt(i)=='/'){
					Time.add(temp);
					System.out.println(temp);
					temp="";
					
				}else
					temp+=time.charAt(i);
				
			}
			Time.add(temp);
			medication x = new medication(medName,Time,medInfo,dosage);
			medList.add(x);
			listOfMeds.addItem(medName);
			enterName.setText("");
			enterTime.setText("");
			enterInfo.setText("");
			enterDosage.setText("");
			enterName.setEditable(false);
			enterTime.setEnabled(false);
			enterInfo.setEditable(false);
			
			enteringMed=false;
			
		}
		
		//The select button was pressed: this displays the information for the slected medication at the 
		//bottom of application 
		else if(actionComand.equals("Select")){
			
			String x = (String) listOfMeds.getSelectedItem();
			for(int i = 0; i<medList.size();i++){
				if(x.equals(medList.get(i).getMedName())){
					nameLbl.setText("Name: "+ medList.get(i).getMedName());
					String time = "Time medication has to be taken: ";
					
					for(int j =0;j<medList.get(i).getTime().size();j++){
						time = time + medList.get(i).getTime().get(j)+" ";
						
					}
					
					timeLbl.setText(time);
					
						
					numTakenLbl.setText("Time medication has to be taken: "+ 
							medList.get(i).getNumTake());
					System.out.println(medList.get(i).getMedInfo());
					listOfMedsTextArea.setText(medList.get(i).getMedInfo());
					dosageLblBottom.setText("The amount of medication you have to take: "+ 
					medList.get(i).getDosage()+" mg");
					

				}
			}
		}
			
			
	}
	//This method is keeps time while the appkication is running
	// also calls the checkTime method
	public static void time(){
		
	
		while(true){
			Calendar cal = new GregorianCalendar();
			 hour = cal.get(Calendar.HOUR);
			 min = cal.get(Calendar.MINUTE);
			 sec = cal.get(Calendar.SECOND);
			AMorPM = cal.get(Calendar.AM_PM);
			day = cal.get(Calendar.DAY_OF_WEEK);
			
			
			String time = hour+":"+min;
			if(AMorPM==1){
				time = time+ "pm";
				
			}else 
				time = time+"am";
			checkTime(time,sec,enteringMed);
			
			
		
		}
	}
	//This method checks the time of medication that was entered. Then display a pop up window if the 
	//correct time is reached
	public static void checkTime(String time,int sec,boolean enteringMed){
		boolean alarm=false;
		if(enteringMed==false){
		
		for(int i =0; i<medList.size();i++){
		
			for(int index = 0;index<medList.get(i).getTime().size();index++){
			
				if(medList.get(i).getTime().get(index).equalsIgnoreCase(time)&&sec==0){
					alarm= true;
				}
					
			}
					
			
			
			
				if(alarm){
				int input = JOptionPane.showOptionDialog(null, "Take "+ medList.get(i).getMedName()+" Now", "Medication Reminder", 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, 
						null, null, null);

				if(input == JOptionPane.OK_OPTION)
				{
				    
				    medList.get(i).addMedCount();
				}
		
				}
			}
		
		}
	}
	
	}



	
	
	
	

	
	



