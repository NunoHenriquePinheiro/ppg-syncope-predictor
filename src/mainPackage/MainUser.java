package mainPackage;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

import objectIdentities.AcqRegistry;
import objectIdentities.AcqWindow;
import useful.UtilMethods;

public class MainUser extends javax.swing.JFrame {

	/////////////////////////////////////////////////////////////////
	// USER'S GUI

	/**
	 * USER'S GUI
	 */
	private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        contentTabPanel = new javax.swing.JTabbedPane();
        syncDetectPanel = new javax.swing.JPanel();
        semaforoLabel = new javax.swing.JLabel();
        riskSyncLabel = new javax.swing.JLabel();
        inputsLabel = new javax.swing.JLabel();
        fsLabel = new javax.swing.JLabel();
        freqsText = new javax.swing.JTextField();
        hzLabel = new javax.swing.JLabel();
        bitsLabel = new javax.swing.JLabel();
        bitsComboBox = new javax.swing.JComboBox<>();
        channelLabel = new javax.swing.JLabel();
        channelComboBox = new javax.swing.JComboBox<>();
        pluxPortLabel = new javax.swing.JLabel();
        pluxPortText = new javax.swing.JTextField();
        startButton = new javax.swing.JButton();
        exportButton = new javax.swing.JButton();
        allrightsLabel = new javax.swing.JLabel();
        ppgSignLabel = new javax.swing.JLabel();
        ppgSignText = new javax.swing.JTextField();
        ppgFeatLabel = new javax.swing.JLabel();
        ppgFeatText = new javax.swing.JTextField();
        csvLabel = new javax.swing.JLabel();
        csvLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messagesText = new javax.swing.JTextArea();
        DefaultCaret caret = (DefaultCaret)messagesText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        chooseSeriesLabel = new javax.swing.JLabel();
        chooseSeriesComboBox = new javax.swing.JComboBox<>();
        helpPanel = new javax.swing.JPanel();
        allrightsLabel1 = new javax.swing.JLabel();
        theSystemLabel = new javax.swing.JLabel();
        theSystemText = new javax.swing.JTextArea();
        requirementsText = new javax.swing.JTextArea();
        requirementsLabel = new javax.swing.JLabel();
        featInforLabel = new javax.swing.JLabel();
        featInforText = new javax.swing.JTextArea();
        contactsLabel = new javax.swing.JLabel();
        contactsText = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Syncope PPG Detector");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(102, 102, 102));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Syncope Detector through PPG");
        titleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        semaforoLabel.setBackground(new java.awt.Color(204, 204, 204));
        semaforoLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        semaforoLabel.setForeground(new java.awt.Color(255, 255, 255));
        semaforoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        semaforoLabel.setText("No data available");
        semaforoLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        semaforoLabel.setOpaque(true);

        riskSyncLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        riskSyncLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        riskSyncLabel.setText("Risk of Syncope?");

        inputsLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        inputsLabel.setText("INPUTS:");

        fsLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fsLabel.setText("Sampling Frequency:");

        freqsText.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        freqsText.setText("100");
        freqsText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                final JTextComponent source = (JTextComponent) input;
                String text = source.getText();

                try {  
                    int RECEBIDO = Integer.parseInt(text);

                    if ((RECEBIDO<36) || (RECEBIDO>1000)) {
                        JOptionPane.showMessageDialog(null, "The sampling frequency must be an integer within [36, 1000] Hz!", "OOPS!",
                            JOptionPane.ERROR_MESSAGE);
                        freqsText.setText("1000");
                        return false;
                    } else {
                        return true;
                    }

                } catch(NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "The sampling frequency must be an integer within [36, 1000] Hz!", "OOPS!",
                        JOptionPane.ERROR_MESSAGE);
                    freqsText.setText("1000");
                    return false;
                }
            }
        });
        freqsText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                freqsTextActionPerformed(evt);
            }
        });

        hzLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        hzLabel.setText("Hz");

        bitsLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bitsLabel.setText("Number of bits:");

        bitsComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bitsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "12 bits", "8 bits" }));
        bitsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bitsComboBoxActionPerformed(evt);
            }
        });

        channelLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        channelLabel.setText("Channel with PPG:");

        channelComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        channelComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Channel 1", "Channel 2", "Channel 3", "Channel 4" }));
        channelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                channelComboBoxActionPerformed(evt);
            }
        });

        pluxPortLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pluxPortLabel.setText("Biosignalsplux Port:");

        pluxPortText.setText("00:07:80:3B:46:3E");
        pluxPortText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                final JTextComponent source = (JTextComponent) input;
                String text = source.getText(); 

                if ((text==null) || (text.isEmpty()) || (text.trim().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Biosignalsplux Port is not valid!", "OOPS!",
                        JOptionPane.ERROR_MESSAGE);
                    pluxPortText.setText("00:07:80:3B:46:3E");
                    return false;
                } else {
                    return true;
                }
            }
        });
        pluxPortText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pluxPortTextActionPerformed(evt);
            }
        });

        startButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        startButton.setText("START");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        exportButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        exportButton.setText("EXPORT");
        exportButton.setEnabled(false);
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        allrightsLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        allrightsLabel.setText("All rights reserved to CISUC - 2016");
        allrightsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        ppgSignLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ppgSignLabel.setText("PPG signal filename to export:");

        ppgSignText.setText("PPGdata");
        ppgSignText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                final JTextComponent source = (JTextComponent) input;
                String text = source.getText(); 

                if ((text==null) || (text.isEmpty()) || (text.trim().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "The PPG signal filename is not valid!", "OOPS!",
                        JOptionPane.ERROR_MESSAGE);
                    ppgSignText.setText("PPGdata");
                    return false;
                } else {
                    return true;
                }
            }
        });

        ppgFeatLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ppgFeatLabel.setText("PPG features filename to export:");

        ppgFeatText.setText("PPGfeatures_charPoints");
        ppgFeatText.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                final JTextComponent source = (JTextComponent) input;
                String text = source.getText(); 

                if ((text==null) || (text.isEmpty()) || (text.trim().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "The PPG features filename is not valid!", "OOPS!",
                        JOptionPane.ERROR_MESSAGE);
                    ppgFeatText.setText("PPGfeatures_charPoints");
                    return false;
                } else {
                    return true;
                }
            }
        });

        csvLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        csvLabel.setText(".csv");

        csvLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        csvLabel1.setText(".csv");

        messagesText.setColumns(20);
        messagesText.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        messagesText.setRows(5);
        messagesText.setText("\n\n\t\t\t\t\t\t--- MESSAGES WILL BE SHOWN HERE ---\n\n");
        jScrollPane1.setViewportView(messagesText);

        chooseSeriesLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chooseSeriesLabel.setText("Choose a series to show:");

        chooseSeriesComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PPG raw data", "SI", "PR, 90 sec", "SI, 90 sec", "PR", "LVET, 90 sec", "RI", "LVET" }));
        chooseSeriesComboBox.setEnabled(false);
        chooseSeriesComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				chooseSeriesComboBoxActionPerformed(evt);
			}
		});

        javax.swing.GroupLayout syncDetectPanelLayout = new javax.swing.GroupLayout(syncDetectPanel);
        syncDetectPanel.setLayout(syncDetectPanelLayout);
        syncDetectPanelLayout.setHorizontalGroup(
            syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(syncDetectPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(syncDetectPanelLayout.createSequentialGroup()
                            .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ppgFeatLabel)
                                .addComponent(pluxPortLabel)
                                .addComponent(channelLabel)
                                .addComponent(fsLabel)
                                .addComponent(bitsLabel)
                                .addComponent(ppgSignLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(syncDetectPanelLayout.createSequentialGroup()
                                    .addComponent(ppgFeatText, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(csvLabel1))
                                .addGroup(syncDetectPanelLayout.createSequentialGroup()
                                    .addComponent(ppgSignText, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(csvLabel))
                                .addComponent(bitsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(syncDetectPanelLayout.createSequentialGroup()
                                    .addComponent(freqsText, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(hzLabel))
                                .addComponent(channelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pluxPortText, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(syncDetectPanelLayout.createSequentialGroup()
                            .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(exportButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(inputsLabel))
                    .addGroup(syncDetectPanelLayout.createSequentialGroup()
                        .addComponent(chooseSeriesLabel)
                        .addGap(18, 18, 18)
                        .addComponent(chooseSeriesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, syncDetectPanelLayout.createSequentialGroup()
                        .addComponent(semaforoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, syncDetectPanelLayout.createSequentialGroup()
                        .addComponent(riskSyncLabel)
                        .addGap(151, 151, 151))))
            .addGroup(syncDetectPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(syncDetectPanelLayout.createSequentialGroup()
                        .addGap(0, 768, Short.MAX_VALUE)
                        .addComponent(allrightsLabel))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        syncDetectPanelLayout.setVerticalGroup(
            syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(syncDetectPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(inputsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(syncDetectPanelLayout.createSequentialGroup()
                        .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pluxPortLabel)
                            .addComponent(pluxPortText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(channelLabel)
                            .addComponent(channelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fsLabel)
                            .addComponent(freqsText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hzLabel))
                        .addGap(18, 18, 18)
                        .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bitsLabel)
                            .addComponent(bitsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ppgSignLabel)
                            .addComponent(ppgSignText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csvLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ppgFeatLabel)
                            .addComponent(ppgFeatText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csvLabel1))
                        .addGap(26, 26, 26)
                        .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(exportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(syncDetectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chooseSeriesLabel)
                            .addComponent(chooseSeriesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))
                    .addGroup(syncDetectPanelLayout.createSequentialGroup()
                        .addComponent(riskSyncLabel)
                        .addGap(18, 18, 18)
                        .addComponent(semaforoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allrightsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        contentTabPanel.addTab("Detect your Syncope", syncDetectPanel);

        allrightsLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        allrightsLabel1.setText("All rights reserved to CISUC - 2016");
        allrightsLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        theSystemLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        theSystemLabel.setText("THE SYSTEM:");

        theSystemText.setBackground(new java.awt.Color(240, 240, 240));
        theSystemText.setColumns(20);
        theSystemText.setFont(new java.awt.Font("Cambria", 0, 13)); // NOI18N
        theSystemText.setRows(5);
        theSystemText.setText("This is a real-time system that accesses your PPG signal in 10-sec intervals, collecting cardiovascular data and predicting the occurrence of syncope episodes.\nWith this, more than preventing a syncope from happening, you can visualize, export and analyze the saved PPG signal, registered beats and calculated features.");
        theSystemText.setOpaque(false);

        requirementsText.setBackground(new java.awt.Color(240, 240, 240));
        requirementsText.setColumns(20);
        requirementsText.setFont(new java.awt.Font("Cambria", 0, 13)); // NOI18N
        requirementsText.setRows(5);
        requirementsText.setText("Hardware:    Biosignalsplux, with an attached PPG sensor\n                       Computer with a Bluetooth connector\n\nSoftware:      Microsoft Windows, Java 7/8 and MATLAB Runtime\n                       Biosignalsplux DLL-files");
        requirementsText.setOpaque(false);

        requirementsLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        requirementsLabel.setText("REQUIREMENTS:");

        featInforLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        featInforLabel.setText("PPG FEATURES:");

        featInforText.setBackground(new java.awt.Color(240, 240, 240));
        featInforText.setColumns(20);
        featInforText.setFont(new java.awt.Font("Cambria", 0, 13)); // NOI18N
        featInforText.setRows(5);
        featInforText.setText("SI:                         ...................................................................................................      Stiffness Index\nPR:                        ...................................................................................................      Pulse Rate\nRI:                         ...................................................................................................      Reflection Index\nLVET:                   ...................................................................................................      Left Ventricular Ejection Time\nPR, 90 sec:          ...................................................................................................      PR's change in 90 sec\nSI, 90 sec:            ...................................................................................................      SI's change in 90 sec\nLVET, 90 sec:     ...................................................................................................      LVET's change in 90 sec\n\nATTENTION: The exported values are normalized within each window!");
        featInforText.setOpaque(false);

        contactsLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        contactsLabel.setText("CONTACTS:");

        contactsText.setBackground(new java.awt.Color(240, 240, 240));
        contactsText.setColumns(20);
        contactsText.setFont(new java.awt.Font("Cambria", 0, 13)); // NOI18N
        contactsText.setRows(5);
        contactsText.setText("Nuno Pinheiro:                        npinheiro@student.fisica.uc.pt\nRicardo Couceiro, PhD:          rcouceir@dei.uc.pt\nProf. Paulo Carvalho, PhD:    carvalho@dei.uc.pt");
        contactsText.setOpaque(false);

        javax.swing.GroupLayout helpPanelLayout = new javax.swing.GroupLayout(helpPanel);
        helpPanel.setLayout(helpPanelLayout);
        helpPanelLayout.setHorizontalGroup(
            helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpPanelLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(theSystemText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(helpPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(allrightsLabel1))
                    .addGroup(helpPanelLayout.createSequentialGroup()
                        .addGroup(helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(theSystemLabel)
                            .addGroup(helpPanelLayout.createSequentialGroup()
                                .addGroup(helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(requirementsLabel)
                                    .addComponent(featInforLabel)
                                    .addComponent(contactsLabel))
                                .addGap(48, 48, 48)
                                .addGroup(helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(contactsText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(requirementsText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(featInforText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        helpPanelLayout.setVerticalGroup(
            helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, helpPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(theSystemLabel)
                .addGap(18, 18, 18)
                .addComponent(theSystemText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(requirementsLabel)
                    .addComponent(requirementsText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(featInforLabel)
                    .addComponent(featInforText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(helpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactsLabel)
                    .addComponent(contactsText, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(allrightsLabel1)
                .addContainerGap())
        );

        contentTabPanel.addTab("Help!", helpPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentTabPanel)
            .addGroup(layout.createSequentialGroup()
                .addGap(333, 333, 333)
                .addComponent(titleLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contentTabPanel))
        );

        pack();
        setLocationRelativeTo(null);
    }

	/**
	 * VARIABLES OF THE USER'S GUI
	 */
	private javax.swing.JLabel allrightsLabel;
	private javax.swing.JLabel allrightsLabel1;
	private javax.swing.JComboBox<String> bitsComboBox;
	private javax.swing.JLabel bitsLabel;
	private javax.swing.JComboBox<String> channelComboBox;
	private javax.swing.JLabel channelLabel;
	private static javax.swing.JComboBox<String> chooseSeriesComboBox;
	private javax.swing.JLabel chooseSeriesLabel;
	private javax.swing.JLabel contactsLabel;
	private javax.swing.JTextArea contactsText;
	private javax.swing.JTabbedPane contentTabPanel;
	private javax.swing.JLabel csvLabel;
	private javax.swing.JLabel csvLabel1;
	private static javax.swing.JButton exportButton;
	private javax.swing.JLabel featInforLabel;
	private javax.swing.JTextArea featInforText;
	private javax.swing.JTextField freqsText;
	private javax.swing.JLabel fsLabel;
	private javax.swing.JPanel helpPanel;
	private javax.swing.JLabel hzLabel;
	private javax.swing.JLabel inputsLabel;
	private javax.swing.JScrollPane jScrollPane1;
	private static javax.swing.JTextArea messagesText;
	private javax.swing.JLabel pluxPortLabel;
	private javax.swing.JTextField pluxPortText;
	private javax.swing.JLabel ppgFeatLabel;
	private javax.swing.JTextField ppgFeatText;
	private javax.swing.JLabel ppgSignLabel;
	private javax.swing.JTextField ppgSignText;
	private javax.swing.JLabel requirementsLabel;
	private javax.swing.JTextArea requirementsText;
	private javax.swing.JLabel riskSyncLabel;
	private static javax.swing.JLabel semaforoLabel;
	private static javax.swing.JButton startButton;
	private javax.swing.JPanel syncDetectPanel;
	private javax.swing.JLabel theSystemLabel;
	private javax.swing.JTextArea theSystemText;
	private javax.swing.JLabel titleLabel;

	/**
	 * INTERACTIONS FROM/TO THE USER'S GUI
	 */
	public static void restartMessageText(String MENSAGEM) {
		messagesText.setText(MENSAGEM);
	}
	public static void appendMessageText(String MENSAGEM) {
		messagesText.append(MENSAGEM);
	}
	
	public boolean isStartOn() {
		return startOn;
	}
	public static void setStartOn(boolean starton) {
		startOn = starton;
	}
	
	public static void setEnableStartButton(boolean boleano){
		startButton.setEnabled(boleano);
	}
	public static void setTextStartButton(String texto){
		startButton.setText(texto);
	}
	
	public static void setEnableExportChooseSerButton(boolean boleano){
		exportButton.setEnabled(boleano);
		chooseSeriesComboBox.setEnabled(boleano);
	}
	
	public static void setSemaforoLabel(int SINAL){
		if(SINAL==0){
			semaforoLabel.setText("No, you are good!");
			semaforoLabel.setBackground(Color.green);
		} else {
			semaforoLabel.setText("Yes, watch out and remain calm!");
			semaforoLabel.setBackground(Color.red);
		}
	}
	
	//
	//
	/////////////////////////////////////////////////////////////////
	// ACTION EVENTS AND MAIN
	

	/**
	 * ACTION EVENTS
	 */
	private static boolean startOn = true;

	private void pluxPortTextActionPerformed(java.awt.event.ActionEvent evt) {
	}

	private void channelComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
	}

	private void freqsTextActionPerformed(java.awt.event.ActionEvent evt) {
	}

	private void bitsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
	}

	private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {

		// Button acting as START
		if (startOn) {
			startOn = false;
			startButton.setText("STOP");
			exportButton.setEnabled(false);
			chooseSeriesComboBox.setEnabled(false);

			deviceName = pluxPortText.getText();
			freqAcq = Integer.parseInt(freqsText.getText());
			Nbits = Integer.parseInt(bitsComboBox.getSelectedItem().toString().split(" bits")[0]);
			PPGchannel = Integer.parseInt(channelComboBox.getSelectedItem().toString().split("Channel ")[1]) - 1;

			LOOPER = new ConnectAcqRunnable();
			Thread THREAD = new Thread(LOOPER);
			THREAD.start();
		}

		// Button acting as STOP
		else {
			LOOPER.stop();
			LOOPER = null;

//			startOn = true;
//			startButton.setText("START");
//			exportButton.setEnabled(true);
//			chooseSeriesComboBox.setEnabled(true);

//			messagesText.setText("\n\n\t\t\t\t\t\t--- MESSAGES WILL BE SHOWN HERE ---\n\n");
		}
	}

	private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {

		AcqRegistry dataToExport = ConnectAcqRunnable.getALLDATA();
		
		if((dataToExport!=null) && (dataToExport.getAllWinSamples().size()!=0)){
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Select Folder");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				String diretoria = chooser.getSelectedFile().getAbsolutePath() + "\\";
				String nomePPGdata = ppgSignText.getText();
				String nomePPGfeat = ppgFeatText.getText();
				String dirPPGdataAll = diretoria+nomePPGdata+".csv";
				String dirPPGfeatAll = diretoria+nomePPGfeat+".csv";
				
				// before we open the file check to see if they already exist
				boolean dirPPGdataExists = new File(dirPPGdataAll).exists();
				boolean dirPPGfeatExists = new File(dirPPGfeatAll).exists();

				if (dirPPGdataExists) {
					messagesText.setText("\nWe are sorry, a problem occurred!...\nERROR NAMING CSV!\n\n");
					messagesText.append("Try again! The following file already exists:\n" + dirPPGdataAll + "\n");
				} else if (dirPPGfeatExists) {
					messagesText.setText("\nWe are sorry, a problem occurred!...\nERROR NAMING CSV!\n\n");
					messagesText.append("Try again! The following file already exists:\n" + dirPPGfeatAll + "\n");
				} else {
					
					//
					//
					// CONSTRUCT AND EXPORT CSV FILE
					try {
						UtilMethods.createExportCSV(dataToExport, dirPPGdataAll, dirPPGfeatAll);
						messagesText.setText("\nEXPORTED!\n");
					} catch (IOException e) {
						messagesText.setText("\nWe are sorry, a problem occurred!...\nERROR EXPORTING CSV!\n\n");
						messagesText.append("Details:\n");
						messagesText.append(e.getMessage());
						exportButton.setEnabled(false);
						chooseSeriesComboBox.setEnabled(false);
					}
				}
			} else {}
			
		} else {
			JOptionPane.showMessageDialog(null, "There is no recorded data to save!", "OOPS!",
		              JOptionPane.ERROR_MESSAGE);
			exportButton.setEnabled(false);
			chooseSeriesComboBox.setEnabled(false);
		}
	}

	private void chooseSeriesComboBoxActionPerformed(java.awt.event.ActionEvent evt) {

		String serieMostrar = chooseSeriesComboBox.getSelectedItem().toString();

		ArrayList<AcqWindow> dataALL = ConnectAcqRunnable.getALLDATA().getAllWinSamples();
		double[] dataToShow = new double[0];
		
		if((dataALL!=null) && (dataALL.size()!=0)){
			if(serieMostrar.equals("PPG raw data")){
				for(int i=0; i<dataALL.size(); i++){
					dataToShow = UtilMethods.concatDouble(dataToShow, dataALL.get(i).getPpgData());
				}
				
			} else if(serieMostrar.equals("SI")){
				for(int i=0; i<dataALL.size(); i++){
					dataToShow = UtilMethods.concatDouble(dataToShow, dataALL.get(i).getSI());
				}
				
			} else if(serieMostrar.equals("PR, 90 sec")){
				for(int i=0; i<dataALL.size(); i++){
					dataToShow = UtilMethods.concatDouble(dataToShow, dataALL.get(i).getPRmw());
				}
				
			} else if(serieMostrar.equals("SI, 90 sec")){
				for(int i=0; i<dataALL.size(); i++){
					dataToShow = UtilMethods.concatDouble(dataToShow, dataALL.get(i).getSImw());
				}
				
			} else if(serieMostrar.equals("PR")){
				for(int i=0; i<dataALL.size(); i++){
					dataToShow = UtilMethods.concatDouble(dataToShow, dataALL.get(i).getPR());
				}
				
			} else if(serieMostrar.equals("LVET, 90 sec")){
				for(int i=0; i<dataALL.size(); i++){
					dataToShow = UtilMethods.concatDouble(dataToShow, dataALL.get(i).getLVETmw());
				}
				
			} else if(serieMostrar.equals("RI")){
				for(int i=0; i<dataALL.size(); i++){
					dataToShow = UtilMethods.concatDouble(dataToShow, dataALL.get(i).getRI());
				}
				
			} else if(serieMostrar.equals("LVET")){
				for(int i=0; i<dataALL.size(); i++){
					dataToShow = UtilMethods.concatDouble(dataToShow, dataALL.get(i).getLVET());
				}
			}
			
			if(dataToShow.length==0){
				JOptionPane.showMessageDialog(null, "This feature is not available!", "OOPS!",
			              JOptionPane.ERROR_MESSAGE);
				
			} else {
				
				final double[] dataToPass = dataToShow;
				new ChartTest(serieMostrar,dataToPass).setVisible(true);
			}
			
		} else {
			JOptionPane.showMessageDialog(null, "There is no recorded data to show!", "OOPS!",
		              JOptionPane.ERROR_MESSAGE);
			exportButton.setEnabled(false);
			chooseSeriesComboBox.setEnabled(false);
		}
	}

	//
	//
	/////////////////////////////////////////////////////////////////
	// CONSTRUCTOR AND VARIABLES TO OPERATE

	// VARIABLES FROM THE USER'S INPUT
	private static String deviceName;
	private static int freqAcq;
	private static int Nchannels = 0xFF;
	private static int Nbits;
	private static int PPGchannel;

	// Structure to call the parallel CONNECTION/ACQUISITION
	private ConnectAcqRunnable LOOPER;

	/**
	 * CONSTRUCTOR
	 */
	public MainUser() {
		//
		// USER'S GUI RELATED STUFF
		initComponents();
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("PPG_logo.png")));
		theSystemText.setBackground(new Color(0, 0, 0, 0));
		requirementsText.setBackground(new Color(0, 0, 0, 0));
		featInforText.setBackground(new Color(0, 0, 0, 0));
		contactsText.setBackground(new Color(0, 0, 0, 0));
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
    	
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainUser().setVisible(true);
			}
		});
    }
	
	
	/**
	 * GETTERS AND SETTERS OF INPUTS
	 */
	public static String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String devicename) {
		deviceName = devicename;
	}

	public static int getFreqAcq() {
		return freqAcq;
	}

	public void setFreqAcq(int freqacq) {
		freqAcq = freqacq;
	}

	public static int getNchannels() {
		return Nchannels;
	}

	public void setNchannels(int nchannels) {
		Nchannels = nchannels;
	}

	public static int getNbits() {
		return Nbits;
	}

	public void setNbits(int nbits) {
		Nbits = nbits;
	}

	public static int getPPGchannel() {
		return PPGchannel;
	}

	public void setPPGchannel(int pPGchannel) {
		PPGchannel = pPGchannel;
	}
	
}
