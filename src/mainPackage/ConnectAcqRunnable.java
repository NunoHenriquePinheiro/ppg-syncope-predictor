package mainPackage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import objectIdentities.*;
import plux.newdriver.bioplux.BPException;
import plux.newdriver.bioplux.Device;
import useful.UtilMethods;

public class ConnectAcqRunnable implements Runnable {

	/**
	 * VARIABLES
	 */
	// BOOLEAN TO (DIS)ALLOW THE CONNECTION/ACQUISITION
	private AtomicBoolean keepRunning;

	// OBJECT TO SAVE ALL THE ACQUISITION AND PROCESSING WINDOWS
	// (OBJECTS AcqWindow)
	private static AcqRegistry ALLDATA;

	/**
	 * GETTERS AND SETTERS
	 */
	public AtomicBoolean getKeepRunning() {
		return keepRunning;
	}

	public void setKeepRunning(AtomicBoolean keepRunning) {
		this.keepRunning = keepRunning;
	}

	public static AcqRegistry getALLDATA() {
		return ALLDATA;
	}

	public void setALLDATA(AcqRegistry aLLDATA) {
		ALLDATA = aLLDATA;
	}

	/**
	 * CONSTRUCTOR
	 */
	public ConnectAcqRunnable() {
		keepRunning = new AtomicBoolean(true);
		ALLDATA = new AcqRegistry(new ArrayList<AcqWindow>());
	}

	/**
	 * METHOD TO STOP THE RUNNABLE
	 */
	public void stop() {
		keepRunning.set(false);
	}

	/**
	 * METHOD TO RUN THE CONNECTION/ACQUISITION
	 */
	@Override
	public void run() {
		
		// boolean to verify that the program was not interrupted before connecting/acquiring
		boolean ENTRARconnect = true;

		// INITIALIZE THE MATLAB LAYER

		MainUser.restartMessageText("\nINITIALIZING, WAIT A MOMENT...\n");

		// OBJECT TO INTERACT WITH MATLAB COMPILATION
		PPGprocessUnoT2.PPGprocessT2 PPGprocess = null;

		// INITIALIZATION OF MWARRAYS TO TRADE INFORMATION TO THE MATLAB
		// COMPILATION
		int flagFirstAccess = 1;
		double initVar = 0;
		Object st_point_prev = null;

		MWNumericArray ppg_data = new MWNumericArray(initVar, MWClassID.DOUBLE);
		MWNumericArray ppg_fs = new MWNumericArray(new Integer(1000), MWClassID.DOUBLE);
		MWNumericArray flagAccess = new MWNumericArray(flagFirstAccess, MWClassID.DOUBLE);
		MWNumericArray STpointPrev = new MWNumericArray(st_point_prev, MWClassID.DOUBLE);

		MainUser.appendMessageText("Longest step ahead...\n\n");

		// INITIALIZATION OF THE CONTACT WITH THE MATLAB COMPILATION
		try {
			PPGprocess = new PPGprocessUnoT2.PPGprocessT2();
			Object[] RESULT = PPGprocess.FUNCAO1_processPPG(5, ppg_data, ppg_fs, STpointPrev, flagAccess);

		} catch (MWException e1) {
			MainUser.restartMessageText(
					"\nWe are sorry, please restart and verify errors!...\nERROR WITH MATLAB COMPILATION!\n\n");
			MainUser.appendMessageText("Details:\n");
			MainUser.appendMessageText(e1.getMessage());
		}

		//
		//
		// VARIABLES TO PERFORM THE ACQUISITION

		flagFirstAccess = 0;
		flagAccess = new MWNumericArray(flagFirstAccess, MWClassID.DOUBLE);
		st_point_prev = null;
		STpointPrev = new MWNumericArray(st_point_prev, MWClassID.DOUBLE);

		// BOOLEAN THAT ENSURES THE SLIDING WINDOW DEFINITION
		// If true, a fully new 10-sec window will be acquired
		boolean flagNframesAcq = true;
		double[] newWindowPart = null;
		int nframesLeft = 0;

		//
		// PPG ATTRIBUTES TO BE SAVED IN EACH WINDOW OBJECT
		double[] SI_values = null;
		double[] PRmw_values = null;
		double[] SImw_values = null;
		double[] PR_values = null;
		double[] LVETmw_values = null;
		double[] RI_values = null;
		double[] LVET_values = null;

		int[] ppgBeat_Start = null;
		int[] ppgBeat_20 = null;
		int[] ppgBeat_1der = null;
		int[] ppgBeat_50 = null;
		int[] ppgBeat_80 = null;
		int[] ppgBeat_Peak = null;
		int[] ppgBeat_End = null;

		double[] SyncDISTANCE = null;
		int[] SyncALERT = null;

		// Define acquiring windows of 10 sec
		int winAcq = 10;

		// Sampling Frequency of acquisition
		int freqAcq = MainUser.getFreqAcq();

		// Number of samples/frames to acquire
		int nframes = freqAcq * winAcq;

		// Initialize structure to acquire each frame of data
		// WHEN WE ACQUIRE EXACTLY nframes FRAMES
		Device.Frame[] FRAMES = UtilMethods.initDeviceFrameArray(nframes);

		// // OBJECT TO INTERACT WITH MATLAB COMPILATION
		// PPGprocessUnoT2.PPGprocessT2 PPGprocess;

		// VARIABLE THAT WILL HAVE THE PPG VALID DATA OF EACH WINDOW
		double[] PPGready = null;
		
		// VERIFY THAT THE PROGRAM WAS NOT STOPPED
		// If it was, it will not proceed to the CONNECT/ACQUISIT.
		if (!keepRunning.get()) {
			MainUser.appendMessageText("\nPROGRAM STOPPED!\n");
			MainUser.setStartOn(true);
			MainUser.setTextStartButton("START");
			MainUser.setEnableStartButton(true);
			ENTRARconnect = false;
		}
		
		//
		//
		// CONNECT AND ACQUIRE

		// INPUT VARIABLES
		String deviceName = MainUser.getDeviceName();
		int Nchannels = MainUser.getNchannels();
		int Nbits = MainUser.getNbits();
		int PPGchannel = MainUser.getPPGchannel();

		// The connection to BIOSIGNALSPLUX is forced, only if it is interrupted
		boolean alwaysConnecting = true;
		int numConnectTry = 1;

		while (ENTRARconnect) {
			try {

				// CONNECT TO PLUX DEVICE
				MainUser.restartMessageText("\nNEW ACQUISITION! CONNECTING!\nThe PPG sensor must be on your finger!\n");
				Device dev = new Device(deviceName);

				// BEGIN ACQUISITION IN LIVE STREAM
				dev.BeginAcq(freqAcq, Nchannels, Nbits);
				// 1000 Hz, 8 (0xFF) channels, 12 bits

				if (!keepRunning.get()) {
					dev.EndAcq();
					dev.Close();
					MainUser.appendMessageText("\nPROGRAM STOPPED!\n");
					MainUser.setStartOn(true);
					MainUser.setTextStartButton("START");
					MainUser.setEnableStartButton(true);
					MainUser.setEnableExportChooseSerButton(true);
					alwaysConnecting = false;
					break;
				}

				//
				//
				// CYCLE TO ACQUIRE IN EACH 10 sec
				//
				//

				// Este ciclo sera depois parado pelo
				// utilizador!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				while (keepRunning.get()) {

					// RESTART EACH VARIABLE TO BE SAVED AS AN EMPTY ONE
					SI_values = new double[0];
					PRmw_values = new double[0];
					SImw_values = new double[0];
					PR_values = new double[0];
					LVETmw_values = new double[0];
					RI_values = new double[0];
					LVET_values = new double[0];
					ppgBeat_Start = new int[0];
					ppgBeat_20 = new int[0];
					ppgBeat_1der = new int[0];
					ppgBeat_50 = new int[0];
					ppgBeat_80 = new int[0];
					ppgBeat_Peak = new int[0];
					ppgBeat_End = new int[0];
					SyncDISTANCE = new double[0];
					SyncALERT = new int[0];

					// OBJECT TO REGISTER THE ACQUISITION/PROCESSING VALUES OF
					// ONE WINDOW
					AcqWindow WINSAMPLE = new AcqWindow();

					// Add TIMESTAMP to the acquisition of 10 sec (WINSAMPLE)
					Timestamp acqTimestamp = new Timestamp(new Date().getTime());
					WINSAMPLE.setAcqTimestamp(acqTimestamp);

					// Start time of the operation - to calculate PROCESSTIME
					long startTime = System.currentTimeMillis();

					// ACQUISITION OF 10 SEC WINDOW FRAMES OF PPG DATA

					MainUser.appendMessageText("\nACQUIRING!\n");

					//
					//
					// DEFINITION OF THE WINDOW TO WORK ON
					//
					//

					if (!keepRunning.get()) {
						MainUser.setEnableStartButton(false);
						alwaysConnecting = false;
						break;
					}

					if (flagNframesAcq) {

						dev.GetFrames(nframes, FRAMES); // get 'nframes' frames
						MainUser.appendMessageText("Window Acquired!\n");

						ArrayList<Double> listPPGdata = UtilMethods.getListPPGdata(nframes, FRAMES, PPGchannel);
						double[] arrayPPGcorrect = UtilMethods.getArrayPPGcorrect(listPPGdata, FRAMES, PPGchannel);
						PPGready = arrayPPGcorrect;
					}

					else {
						// aqui faco algo semelhante aos passos de cima,
						// mas tendo em conta 2 momentos diferentes:
						// 1. pontos sobrantes do FRAMES anterior
						// 2. pontos para novos frames adquiridos

						// STRUCTURE TO ACQUIRE LESS THAN nframes FRAMES
						Device.Frame[] FRAMES2 = UtilMethods.initDeviceFrameArray(nframesLeft);

						// Acquire and prepare the number of frames that are
						// needed to complete the signal window
						dev.GetFrames(nframesLeft, FRAMES2);
						// get 'nframesLeft' frames

						MainUser.appendMessageText("Window Acquired!\n");

						ArrayList<Double> listPPGdata2 = UtilMethods.getListPPGdata(nframesLeft, FRAMES2, PPGchannel);
						double[] arrayPPGcorrect2 = UtilMethods.getArrayPPGcorrect(listPPGdata2, FRAMES2, PPGchannel);
						PPGready = UtilMethods.concatDouble(newWindowPart, arrayPPGcorrect2);
					}

					if (!keepRunning.get()) {
						MainUser.setEnableStartButton(false);
						alwaysConnecting = false;
						break;
					}

					//
					//
					// PROCESSING THE PPG WINDOW WITH MATLAB COMPILED FUNCTIONS
					//
					//

					ppg_data = new MWNumericArray(PPGready, MWClassID.DOUBLE);
					ppg_fs = new MWNumericArray(freqAcq, MWClassID.DOUBLE);

					MainUser.appendMessageText("\nPROCESSING!\n");

					if (!keepRunning.get()) {
						MainUser.setEnableStartButton(false);
						alwaysConnecting = false;
						break;
					}

					try {
						PPGprocess = new PPGprocessUnoT2.PPGprocessT2();
						Object[] RESULT = PPGprocess.FUNCAO1_processPPG(17, ppg_data, ppg_fs, STpointPrev, flagAccess);

						SI_values = UtilMethods.objectToDoubleArray(RESULT[8]);
						PRmw_values = UtilMethods.objectToDoubleArray(RESULT[9]);
						SImw_values = UtilMethods.objectToDoubleArray(RESULT[10]);
						PR_values = UtilMethods.objectToDoubleArray(RESULT[11]);
						LVETmw_values = UtilMethods.objectToDoubleArray(RESULT[12]);
						RI_values = UtilMethods.objectToDoubleArray(RESULT[13]);
						LVET_values = UtilMethods.objectToDoubleArray(RESULT[14]);

						ppgBeat_Start = UtilMethods.objectToIntArray(RESULT[0]);
						ppgBeat_20 = UtilMethods.objectToIntArray(RESULT[4]);
						ppgBeat_1der = UtilMethods.objectToIntArray(RESULT[3]);
						ppgBeat_50 = UtilMethods.objectToIntArray(RESULT[5]);
						ppgBeat_80 = UtilMethods.objectToIntArray(RESULT[6]);
						ppgBeat_Peak = UtilMethods.objectToIntArray(RESULT[1]);
						ppgBeat_End = UtilMethods.objectToIntArray(RESULT[2]);

						SyncDISTANCE = UtilMethods.objectToDoubleArray(RESULT[16]);
						SyncALERT = UtilMethods.objectToIntArray(RESULT[15]);

						st_point_prev = RESULT[7];
						STpointPrev = new MWNumericArray(st_point_prev, MWClassID.DOUBLE);

					} catch (MWException e) {
						MainUser.appendMessageText(
								"\nWe are sorry, a problem occurred!...\nERROR WITH MATLAB COMPILATION!\n\n");
						MainUser.appendMessageText("Details:\n");
						MainUser.appendMessageText(e.getMessage());
					}

					if (!keepRunning.get()) {
						MainUser.setEnableStartButton(false);
						alwaysConnecting = false;
						break;
					}

					// CHANGE THE ALARM OF RISK OF SYNCOPE!!!!!!!!
					int ALERTA_GUI = 0;
					for (int nuno = 0; nuno < SyncALERT.length; nuno++) {
						ALERTA_GUI = ALERTA_GUI + SyncALERT[nuno];
					}
					MainUser.setSemaforoLabel(ALERTA_GUI);

					// Define PPG DATA OF INTEREST (data that includes PPG
					// characteristic points)
					double[] PPGinterest;
					if ((ppgBeat_End == null) || (ppgBeat_End.length == 0)) {
						PPGinterest = PPGready;
					} else {
						PPGinterest = new double[ppgBeat_End[ppgBeat_End.length - 1]];
						for (int i = 0; i < ppgBeat_End[ppgBeat_End.length - 1]; i++) {
							// em Matlab, indexes comecam em 1. Em Java, comecam
							// em 0. Logo,
							// 'i<ppgBeat_endInd[ppgBeat_endInd.length]', em
							// vez de 'i<=...'
							PPGinterest[i] = PPGready[i];
						}
					}

					// Add PROCESSTIME to the acquisition object (WINSAMPLE)
					long endTime = System.currentTimeMillis();
					long processTime = endTime - startTime;
					WINSAMPLE.setProcessTime(processTime);

					// Add PPG DATA to the acquisition object
					// (WINSAMPLE)
					WINSAMPLE.setPpgData(PPGinterest);

					// Add PROCESS VARIABLES (Features + CharPoints) to the
					// acquisition object
					// (WINSAMPLE)
					WINSAMPLE.setSI(SI_values);
					WINSAMPLE.setPRmw(PRmw_values);
					WINSAMPLE.setSImw(SImw_values);
					WINSAMPLE.setPR(PR_values);
					WINSAMPLE.setLVETmw(LVETmw_values);
					WINSAMPLE.setRI(RI_values);
					WINSAMPLE.setLVET(LVET_values);

					WINSAMPLE.setPpgBeatStart(ppgBeat_Start);
					WINSAMPLE.setPpgBeat20(ppgBeat_20);
					WINSAMPLE.setPpgBeat1der(ppgBeat_1der);
					WINSAMPLE.setPpgBeat50(ppgBeat_50);
					WINSAMPLE.setPpgBeat80(ppgBeat_80);
					WINSAMPLE.setPpgBeatPeak(ppgBeat_Peak);
					WINSAMPLE.setPpgBeatEnd(ppgBeat_End);

					WINSAMPLE.setDistanceMETRIC(SyncDISTANCE);
					WINSAMPLE.setALARM(SyncALERT);

					// Add ACQWINDOW (WINSAMPLE) to ACQREGISTRY (ALLDATA)
					ALLDATA.getAllWinSamples().add(WINSAMPLE);

					//
					//
					//
					// DEFINING THE NEW SLIDING WINDOW
					//
					//
					//

					if ((ppgBeat_Start == null) || (ppgBeat_End == null) || (ppgBeat_Start.length == 0)
							|| (ppgBeat_End.length == 0)) {
						flagNframesAcq = true;
					} else {
						flagNframesAcq = false;

						// Save values from the current signal window that were
						// not
						// analyzed yet
						int winPartIndex1 = ppgBeat_End[ppgBeat_End.length - 1];
						int winPartIndex2 = PPGready.length;
						newWindowPart = new double[winPartIndex2 - winPartIndex1];

						int newWinIter = 0;
						for (int guardar = winPartIndex1; guardar < winPartIndex2; guardar++) {
							newWindowPart[newWinIter] = PPGready[guardar];
							newWinIter++;
						}

						// Calculate the number of new frames that we need to
						// acquire
						nframesLeft = nframes - (winPartIndex2 - winPartIndex1);
					}
				}

				dev.EndAcq();
				dev.Close();

			} catch (BPException e) {

				MainUser.appendMessageText("\nWe are sorry, a problem occurred!...\n");
				MainUser.appendMessageText("ERROR WITH BIOSIGNALSPLUX! MAC address: " + deviceName + "\n\n");
				MainUser.appendMessageText("Details:\n");
				MainUser.appendMessageText("Exception: " + e.code + ".\n - Message: " + e.getMessage() + "\n");

				if (alwaysConnecting) {
					MainUser.appendMessageText("\nCONNECTION LOST!\nWe will try again in a maximum of 3 sec!\nATTEMPT "
							+ numConnectTry + "/3\n");

					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
					}

					if (numConnectTry >= 3) {
						ENTRARconnect = false;
						alwaysConnecting = false;
					} else {
						numConnectTry++;
					}
				} else {
					MainUser.appendMessageText("\nCONNECTION LOST!");
				}
			}

			// Decision whether a new connection is tried or not
			if (!alwaysConnecting) {
				MainUser.appendMessageText("\nPROGRAM STOPPED!\n");
				keepRunning.set(false);
				MainUser.setStartOn(true);
				MainUser.setTextStartButton("START");
				MainUser.setEnableStartButton(true);
				MainUser.setEnableExportChooseSerButton(true);
				break;
			}
		}
	}
}
