package objectIdentities;

import java.sql.Timestamp;

// OBJECT TO STORE THE INFORMATION OF AN UNIQUE WINDOW ACQUISITION/PROCESSING 
public class AcqWindow {

	// ATTRIBUTES
	private Timestamp acqTimestamp;
	private long processTime;
	private double[] ppgData;

	private double[] SI;
	private double[] PRmw;
	private double[] SImw;
	private double[] PR;
	private double[] LVETmw;
	private double[] RI;
	private double[] LVET;

	private int[] ppgBeatStart;
	private int[] ppgBeat20;
	private int[] ppgBeat1der;
	private int[] ppgBeat50;
	private int[] ppgBeat80;
	private int[] ppgBeatPeak;
	private int[] ppgBeatEnd;

	private double[] DistanceMETRIC;
	private int[] ALARM;

	//
	// GETTER/SETTER OF acqTimestamp
	public Timestamp getAcqTimestamp() {
		return acqTimestamp;
	}

	public void setAcqTimestamp(Timestamp acqTimestamp) {
		this.acqTimestamp = acqTimestamp;
	}

	//
	// GETTER/SETTER OF processTime
	public long getProcessTime() {
		return processTime;
	}

	public void setProcessTime(long processTime) {
		this.processTime = processTime;
	}

	//
	// GETTER/SETTER OF ppgData
	public double[] getPpgData() {
		return ppgData;
	}

	public void setPpgData(double[] ppgData) {
		this.ppgData = ppgData;
	}

	//
	// GETTER/SETTER OF SI
	public double[] getSI() {
		return SI;
	}

	public void setSI(double[] sI) {
		SI = sI;
	}

	//
	// GETTER/SETTER OF PRmw
	public double[] getPRmw() {
		return PRmw;
	}

	public void setPRmw(double[] pRmw) {
		PRmw = pRmw;
	}

	//
	// GETTER/SETTER OF SImw
	public double[] getSImw() {
		return SImw;
	}

	public void setSImw(double[] sImw) {
		SImw = sImw;
	}

	//
	// GETTER/SETTER OF PR
	public double[] getPR() {
		return PR;
	}

	public void setPR(double[] pR) {
		PR = pR;
	}

	//
	// GETTER/SETTER OF LVETmw
	public double[] getLVETmw() {
		return LVETmw;
	}

	public void setLVETmw(double[] lVETmw) {
		LVETmw = lVETmw;
	}

	//
	// GETTER/SETTER OF RI
	public double[] getRI() {
		return RI;
	}

	public void setRI(double[] rI) {
		RI = rI;
	}

	//
	// GETTER/SETTER OF LVET
	public double[] getLVET() {
		return LVET;
	}

	public void setLVET(double[] lVET) {
		LVET = lVET;
	}

	//
	// GETTER/SETTER OF ppgBeatStart
	public int[] getPpgBeatStart() {
		return ppgBeatStart;
	}

	public void setPpgBeatStart(int[] ppgBeatStart) {
		this.ppgBeatStart = ppgBeatStart;
	}

	//
	// GETTER/SETTER OF ppgBeat20
	public int[] getPpgBeat20() {
		return ppgBeat20;
	}

	public void setPpgBeat20(int[] ppgBeat20) {
		this.ppgBeat20 = ppgBeat20;
	}

	//
	// GETTER/SETTER OF ppgBeat1der
	public int[] getPpgBeat1der() {
		return ppgBeat1der;
	}

	public void setPpgBeat1der(int[] ppgBeat1der) {
		this.ppgBeat1der = ppgBeat1der;
	}

	//
	// GETTER/SETTER OF ppgBeat50
	public int[] getPpgBeat50() {
		return ppgBeat50;
	}

	public void setPpgBeat50(int[] ppgBeat50) {
		this.ppgBeat50 = ppgBeat50;
	}

	//
	// GETTER/SETTER OF ppgBeat80
	public int[] getPpgBeat80() {
		return ppgBeat80;
	}

	public void setPpgBeat80(int[] ppgBeat80) {
		this.ppgBeat80 = ppgBeat80;
	}

	//
	// GETTER/SETTER OF ppgBeatPeak
	public int[] getPpgBeatPeak() {
		return ppgBeatPeak;
	}

	public void setPpgBeatPeak(int[] ppgBeatPeak) {
		this.ppgBeatPeak = ppgBeatPeak;
	}

	//
	// GETTER/SETTER OF ppgBeatEnd
	public int[] getPpgBeatEnd() {
		return ppgBeatEnd;
	}

	public void setPpgBeatEnd(int[] ppgBeatEnd) {
		this.ppgBeatEnd = ppgBeatEnd;
	}

	//
	// GETTER/SETTER OF DistanceMETRIC
	public double[] getDistanceMETRIC() {
		return DistanceMETRIC;
	}

	public void setDistanceMETRIC(double[] distanceMETRIC) {
		DistanceMETRIC = distanceMETRIC;
	}
	
	//
	// GETTER/SETTER OF ALARM
	public int[] getALARM() {
		return ALARM;
	}

	public void setALARM(int[] aLARM) {
		ALARM = aLARM;
	}

}
