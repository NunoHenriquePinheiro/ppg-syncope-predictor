package mainPackage;

import java.awt.*;
import javax.swing.*;

import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import java.beans.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainInitializeProgram extends JPanel implements PropertyChangeListener {
	
	private static JFrame frame;
	private JTextArea taskOutput;
    
	private Task task;
    
    private boolean semaforoToRun = true;
    
    /**
	 * BACKGROUND INITIALIZATION OF MATLAB BACKEND
	 * AND OPENING OF THE MAIN PROGRAM (MainUser.java)
	 */
    class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            
            int progress = 0;
            setProgress(Math.min(progress, 100));
            
            //
    		// INITIALIZATION
            
    		// INITIALIZE THE MATLAB LAYER
            taskOutput.append("Welcome!\n\nINITIALIZING...\n\n");

    		// OBJECT TO INTERACT WITH MATLAB COMPILATION
    		PPGprocessUnoT2.PPGprocessT2 PPGprocess = null;

    		progress = progress + 25;
    		setProgress(Math.min(progress, 100));
    		
    		// INITIALIZATION OF MWARRAYS TO TRADE INFORMATION TO THE MATLAB
    		// COMPILATION
    		int flagFirstAccess = 1;
    		double initVar = 0;
    		Object st_point_prev = null;
    		
    		progress = progress + 50;
    		setProgress(Math.min(progress, 100));

    		MWNumericArray ppg_data = new MWNumericArray(initVar, MWClassID.DOUBLE);
    		MWNumericArray ppg_fs = new MWNumericArray(new Integer(1000), MWClassID.DOUBLE);
    		MWNumericArray flagAccess = new MWNumericArray(flagFirstAccess, MWClassID.DOUBLE);
    		MWNumericArray STpointPrev = new MWNumericArray(st_point_prev, MWClassID.DOUBLE);

    		taskOutput.append("\nAlmost there! This is the biggest step!\n\n");
    		
    		// INITIALIZATION OF THE CONTACT WITH THE MATLAB COMPILATION
    		try {
    			PPGprocess = new PPGprocessUnoT2.PPGprocessT2();
    			Object[] RESULT = PPGprocess.FUNCAO1_processPPG(5, ppg_data, ppg_fs, STpointPrev, flagAccess);
    			
    			progress = progress + 25;
        		setProgress(Math.min(progress, 100));
        		
    		} catch (MWException e1) {
    			taskOutput.setText("\nWe are sorry, please restart and verify errors!...\nERROR WITH MATLAB COMPILATION!\n\n");
    			taskOutput.append("Details:\n");
    			taskOutput.append(e1.getMessage());
    			
    			semaforoToRun = false;
    		}
            return null;
        }
 
        @Override
        public void done() {
            if(semaforoToRun){
            	frame.dispose();
            	
            	java.awt.EventQueue.invokeLater(new Runnable() {
        			public void run() {
        				new MainUser().setVisible(true);
        			}
        		});
            } else{
            	Toolkit.getDefaultToolkit().beep();
            	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        }
    }
 
    /**
	 * CONSTRUCTOR
	 */
    public MainInitializeProgram() {
        super(new BorderLayout());
        
        taskOutput = new JTextArea(20, 60);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
		task = new Task();
		task.addPropertyChangeListener(this);
		task.execute();
    }
    
    /**
     * Invoked when there is changes in the task's property events
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName() ) {
            int progress = (Integer) evt.getNewValue();
            String message = String.format("Completed %d%%\n", progress);
            taskOutput.append(message);
            
            if (task.isDone()) {
                taskOutput.append("Thank you! Enjoy!\n");
            }
        }
    }
    
    /**
     * USER'S GUI
     */
    static void createAndShowGUI() {
        // Create/Set up the window
        frame = new JFrame("Syncope PPG Detector - INITIALIZING...");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainInitializeProgram.class.getResource("PPG_CatarinaOliveira.png")));
 
        // Content pane
        JComponent newContentPane = new MainInitializeProgram();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
 
    /**
     * MAIN
     */
    public static void main(String[] args) {
    	
    	String laf = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(laf);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
			Logger.getLogger(MainUser.class.getName()).log(Level.SEVERE, null, ex);
		}
    	
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}