package useful;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.csvreader.CsvWriter;

import objectIdentities.AcqRegistry;
import objectIdentities.AcqWindow;
import plux.newdriver.bioplux.Device;

public class UtilMethods {

	// INITIALIZE DEVICE.FRAME[] STRUCTURE
	public static Device.Frame[] initDeviceFrameArray(int nframes) {
		Device.Frame[] FRAMES = new Device.Frame[nframes];
		for (int i = 0; i < FRAMES.length; i++) {
			FRAMES[i] = new Device.Frame();
		}
		return FRAMES;
	}

	// GET PPG DATA FROM BIOSIGNALSPLUX FRAME OBJECTS LIST
	public static ArrayList<Double> getListPPGdata(int nframes, Device.Frame[] FRAMES, int PPGchannel) {

		ArrayList<Double> PPGdata = new ArrayList<Double>();

		for (int i = 0; i < nframes; i++) {
			PPGdata.add((double) FRAMES[i].an_in[PPGchannel]);
		}
		return PPGdata;
	}

	// GET CORRECT PPG SIGNAL WITH REPOSITION OF POSSIBLE MISSING VALUES
	public static double[] getArrayPPGcorrect(ArrayList<Double> listPPGdata, Device.Frame[] FRAMES, int PPGchannel) {

		// If there are missing values, the indexes of FRAMES and LISTPPGDATA
		// will differ. This is to correct the difference.
		int sumPastMissIntervals = 0;

		for (int i = 1; i < FRAMES.length; i++) {
			int seqDif = Math.abs(Math.abs(FRAMES[i].seq) - Math.abs(FRAMES[i - 1].seq));

			// If difference of SEQs is bigger than 1, there are SEQ-1 missing
			// values
			if (seqDif > 1) {
				int missIntervals = seqDif - 1;

				// FUNCTION TO CALCULATE AND ADD MISSED VALUES TO listPPGdata
				listPPGdata = calculateMissVals(listPPGdata, FRAMES, PPGchannel, missIntervals, i,
						sumPastMissIntervals);

				sumPastMissIntervals = sumPastMissIntervals + missIntervals;
			}
		}

		// Pass LISTPPGDATA to double[], and return it
		int signalSize = listPPGdata.size();
		double[] correctPPG = new double[signalSize];
		for (int i = 0; i < signalSize; i++) {
			correctPPG[i] = listPPGdata.get(i);
		}

		return correctPPG;
	}

	// CALCULATING MISSING VALUES OF PPG FRAMES AND ADDING TO PPG LIST (CALLED
	// BY getArrayPPGcorrect)
	public static ArrayList<Double> calculateMissVals(ArrayList<Double> listPPGdata, Device.Frame[] FRAMES,
			int PPGchannel, int missIntervals, int i, int sumPastMissIntervals) {

		int val1 = FRAMES[i - 1].an_in[PPGchannel];
		int val2 = FRAMES[i].an_in[PPGchannel];
		int ppgDif = val1 - val2;

		// Linear interpolation: Missing values differ from each other in the
		// same amplitude step
		double ppgStep = ppgDif / missIntervals;

		// Add the missing number of values, according to the interpolated step
		// and the correct index, in LISTPPGDATA
		double lastVal = val1;
		for (int j = 1; j <= missIntervals; j++) {
			double addVal = lastVal + ppgStep;
			listPPGdata.add(i + j + sumPastMissIntervals, addVal);
			lastVal = addVal;
		}

		return listPPGdata;
	}

	// CONCATENATING TWO DOUBLE[]s INTO JUST ONE DOUBLE[]
	public static double[] concatDouble(double[] a, double[] b) {
		int aLen = a.length;
		int bLen = b.length;
		double[] c = new double[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}

	// CONVERT OBJECT (THAT CONTAINS AN ARRAY) TO A DOUBLE[]
	public static double[] objectToDoubleArray(Object RESULT) {

		String[] RES = RESULT.toString().split("\\s+");
		int RES_len = RES.length;

		// System.out.println("\nobjectToDoubleArray");
		// System.out.println("String[] RES.length = " + RES_len);
		// for (int i=0; i<RES_len; i++){
		// System.out.println("RES[" + i + "] = " + RES[i] + ", length=" +
		// RES[i].length() + ";");
		// }

		if (RES.length > 1) {

			// When splitting String to String[], some String[i] may be empty,
			// which does not matter
			int len_RESfinal = 0;
			for (int i = 0; i < RES_len; i++) {
				if ((RES[i].length() != 0) && (!RES[i].isEmpty())) {
					len_RESfinal++;
				}
			}

			double[] ResFINAL = new double[len_RESfinal];

			int iter = 0;
			for (int i = 0; i < RES_len; i++) {
				if ((RES[i].length() != 0) && (!RES[i].isEmpty())) {

					try {
						if (RES[i].contains("*")) {
							String[] decomposicao = RES[i].split("\\*");
							ResFINAL[iter] = 1;
							for (int dec = 0; dec < decomposicao.length; dec++) {
								ResFINAL[iter] = ResFINAL[iter] * Double.parseDouble(decomposicao[dec]);
							}
						} else {
							ResFINAL[iter] = Double.parseDouble(RES[i]);
						}
						iter++;
					} catch (NumberFormatException errNumForm) {
					}
				}
			}

			return ResFINAL;
		} else {
			return new double[0];
		}
	}

	// CONVERT OBJECT (THAT CONTAINS AN ARRAY) TO A INT[]
	public static int[] objectToIntArray(Object RESULT) {

		String[] RES = RESULT.toString().split("\\s+");
		int RES_len = RES.length;

		if (RES.length > 1) {

			// When splitting String to String[], some String[i] may be empty,
			// which does not matter
			int len_RESfinal = 0;
			for (int i = 0; i < RES_len; i++) {
				if ((RES[i].length() != 0) && (!RES[i].isEmpty())) {
					len_RESfinal++;
				}
			}

			int[] ResFINAL = new int[len_RESfinal];

			int iter = 0;
			for (int i = 0; i < RES_len; i++) {
				if ((RES[i].length() != 0) && (!RES[i].isEmpty())) {
					ResFINAL[iter] = Integer.parseInt(RES[i]);
					iter++;
				}
			}

			return ResFINAL;
		} else {
			return new int[0];
		}
	}

	// CONSTRUCT AND EXPORT CSV FILES
	public static void createExportCSV(AcqRegistry ALLDATA, String directory1, String directory2) throws IOException {
		CsvWriter csvFeatures = new CsvWriter(new FileWriter(directory1, true), ',');
		CsvWriter csvBeats = new CsvWriter(new FileWriter(directory2, true), ',');

		ArrayList<AcqWindow> dataToExport = ALLDATA.getAllWinSamples();
		AcqWindow winToExport = new AcqWindow();

		Timestamp winTimestamp;
		long winProcessTime;
		double[] winPPGdata;

		double[] winSI;
		double[] winPRmw;
		double[] winSImw;
		double[] winPR;
		double[] winLVETmw;
		double[] winRI;
		double[] winLVET;

		int[] winBeatStart;
		int[] winBeat20;
		int[] winBeat1der;
		int[] winBeat50;
		int[] winBeat80;
		int[] winBeatPeak;
		int[] winBeatEnd;

		double[] winDISTANCE;
		int[] winALARM;

		// Label each column-attribute of the CSV for PPG features
		csvFeatures.write("TimeStamp");
		csvFeatures.write("Processing time");
		csvFeatures.write("PPG raw data");
		csvFeatures.endRecord();

		// Label each column-attribute of the CSV for PPG characteristic points
		csvBeats.write("TimeStamp");
		csvBeats.write("Processing time");
		csvBeats.write("Pulse onset");
		csvBeats.write("Pulse 20%");
		csvBeats.write("Pulse 1stDiff peak");
		csvBeats.write("Pulse 50%");
		csvBeats.write("Pulse 80%");
		csvBeats.write("Pulse peak");
		csvBeats.write("Pulse offset");
		csvBeats.write("SI");
		csvBeats.write("PR_mw");
		csvBeats.write("SI_mw");
		csvBeats.write("PR");
		csvBeats.write("LVET_mw");
		csvBeats.write("RI");
		csvBeats.write("LVET");
		csvBeats.write("Distance values");
		csvBeats.write("Syncope Alert");
		csvBeats.endRecord();

		// Accumulated length of all the acquired PPG windows
		// (to adapt the indexes of the PPG characteristic points to a unique
		// array of data - instead of windows)
		int lenSum = 0;

		// Go through every saved ACQWINDOW inside ACQREGISTRY
		for (int i = 0; i < dataToExport.size(); i++) {

			// Get attributes
			winToExport = dataToExport.get(i);

			winTimestamp = winToExport.getAcqTimestamp();
			winProcessTime = winToExport.getProcessTime();
			winPPGdata = winToExport.getPpgData();

			winSI = winToExport.getSI();
			winPRmw = winToExport.getPRmw();
			winSImw = winToExport.getSImw();
			winPR = winToExport.getPR();
			winLVETmw = winToExport.getLVETmw();
			winRI = winToExport.getRI();
			winLVET = winToExport.getLVET();

			winBeatStart = winToExport.getPpgBeatStart();
			winBeat20 = winToExport.getPpgBeat20();
			winBeat1der = winToExport.getPpgBeat1der();
			winBeat50 = winToExport.getPpgBeat50();
			winBeat80 = winToExport.getPpgBeat80();
			winBeatPeak = winToExport.getPpgBeatPeak();
			winBeatEnd = winToExport.getPpgBeatEnd();

			winDISTANCE = winToExport.getDistanceMETRIC();
			winALARM = winToExport.getALARM();

			//
			// Go through the saved PPG DATA and BEATS of this ACQWINDOW and
			// write each CSV (two files) with the according information

			//
			// CSV FOR PPG SIGNAL

			// TIMESTAMP and PROCESSTIME in the beginning of each window
			csvFeatures.write("" + winTimestamp);
			csvFeatures.write("" + winProcessTime);

			for (int j = 0; j < winPPGdata.length; j++) {

				// Place of TIMESTAMP and PROCESSTIME
				if (j > 0) {
					csvFeatures.write("");
					csvFeatures.write("");
				}

				// PPG DATA
				csvFeatures.write("" + winPPGdata[j]);

				// Finish the record of a line of data
				csvFeatures.endRecord();
			}

			//
			// CSV FOR PPG CHARACTERISTIC POINTS AND FEATURES

			// TIMESTAMP and PROCESSTIME in the beginning of each window
			csvBeats.write("" + winTimestamp);
			csvBeats.write("" + winProcessTime);

			if ((winBeatStart != null) && (winBeatStart.length > 0)) {
				for (int j = 0; j < winBeatStart.length; j++) {

					// Place of TIMESTAMP and PROCESSTIME
					if (j > 0) {
						csvBeats.write("");
						csvBeats.write("");
					}

					// PPG CHAR POINTS
					csvBeats.write("" + (winBeatStart[j] + lenSum));
					csvBeats.write("" + (winBeat20[j] + lenSum));
					csvBeats.write("" + (winBeat1der[j] + lenSum));
					csvBeats.write("" + (winBeat50[j] + lenSum));
					csvBeats.write("" + (winBeat80[j] + lenSum));
					csvBeats.write("" + (winBeatPeak[j] + lenSum));
					csvBeats.write("" + (winBeatEnd[j] + lenSum));

					if (winSI.length > 0) {
						csvBeats.write("" + winSI[j]);
						csvBeats.write("" + winPRmw[j]);
						csvBeats.write("" + winSImw[j]);
						csvBeats.write("" + winPR[j]);
						csvBeats.write("" + winLVETmw[j]);
						csvBeats.write("" + winRI[j]);
						csvBeats.write("" + winLVET[j]);
						csvBeats.write("" + winDISTANCE[j]);

						// SYNCOPE ALERT
						csvBeats.write("" + winALARM[j]);
					} else {
						csvBeats.write("");
						csvBeats.write("");
						csvBeats.write("");
						csvBeats.write("");
						csvBeats.write("");
						csvBeats.write("");
						csvBeats.write("");
						csvBeats.write("");
						csvBeats.write("");
					}

					// Finish the record of a line of data
					csvBeats.endRecord();
				}

			} else {
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");
				csvBeats.write("");

				// Finish the record of a line of data
				csvBeats.endRecord();
			}

			// Update the accumulated length of the signal (all windows)
			lenSum = lenSum + winPPGdata.length;
		}

		csvFeatures.close();
		csvBeats.close();
	}

	// PRINT A DOUBLE ARRAY (USED TO DEBUG)
	public static void printDoubleARRAY(double[] arrayDouble, String TITLE) {
		System.out.println("\n" + TITLE);
		System.out.print("VALORES: ");

		if (arrayDouble != null) {
			for (int i = 0; i < arrayDouble.length - 1; i++) {
				System.out.print(arrayDouble[i] + ", ");
			}
			System.out.println(arrayDouble[arrayDouble.length - 1]);
		}
		System.out.println("Length: " + arrayDouble.length);
	}

	// PRINT AN INT ARRAY (USED TO DEBUG)
	public static void printIntARRAY(int[] arrayInt, String TITLE) {
		System.out.println("\n" + TITLE);
		System.out.print("VALORES: ");

		if (arrayInt != null) {
			for (int i = 0; i < arrayInt.length - 1; i++) {
				System.out.print(arrayInt[i] + ", ");
			}
			System.out.println(arrayInt[arrayInt.length - 1]);
		}
	}

}
