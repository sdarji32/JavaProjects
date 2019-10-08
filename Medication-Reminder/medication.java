import java.util.ArrayList;
import java.util.List;

public class medication {
	
	
	private String medName;
	private String medInfo;
	private int numTake;
	private static List<String> time = new ArrayList<String>();
	private String dosage;
	
	
	public medication(String medName,List time, String medInfo,String dosage){
		this.medName = medName;
		this.time = time;
		this.numTake = 0;
		this.medInfo = medInfo;
		this.dosage = dosage;
	}


	public String getMedName() {
		return medName;
	}


	public void setMedName(String medName) {
		this.medName = medName;
	}	

	public String getMedInfo() {
		
		return medInfo;
	}


	public void setMedInfo(String medInfo) {
		this.medInfo = medInfo;
	}

	
	public int getNumTake() {
		return numTake;
	}


	public void setNumTake(int numTake) {
		this.numTake = numTake;
	}
	public void addMedCount(){
		this.numTake++;
	}


	public static List<String> getTime() {
		return time;
	}


	public static void setTime(List<String> time) {
		medication.time = time;
	}


	public String getDosage() {
		return dosage;
	}


	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
}
