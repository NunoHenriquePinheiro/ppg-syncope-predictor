package objectIdentities;

import java.util.ArrayList;

// OBJECT TO STORE THE INFORMATION OF AN ACQUISITION AND PROCESSING
public class AcqRegistry {

	// ATTRIBUTES
	private ArrayList<AcqWindow> AllWinSamples;

	// CONSTRUCTOR
	public AcqRegistry(ArrayList<AcqWindow> AllWinSamples) {
		super();
		this.AllWinSamples = AllWinSamples;
	}

	// GETTER/SETTER OF AllWinSamples
	public ArrayList<AcqWindow> getAllWinSamples() {
		return AllWinSamples;
	}

	public void setAllWinSamples(ArrayList<AcqWindow> allWinSamples) {
		AllWinSamples = allWinSamples;
	}
}
