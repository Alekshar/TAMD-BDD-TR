package fr.matis.bddtr.simulator.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;
import javax.xml.crypto.Data;

import fr.matis.bddtr.database.server.DatabaseProcess;
import fr.matis.bddtr.model.SimulationConfig;
import fr.matis.bddtr.model.transaction.OperationType;

import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class ClientWindow {

	private JFrame frmRealtimeDatabaseSimulator;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow window = new ClientWindow();
					window.frmRealtimeDatabaseSimulator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRealtimeDatabaseSimulator = new JFrame();
		frmRealtimeDatabaseSimulator.setTitle("Realtime database simulator");
		frmRealtimeDatabaseSimulator.setBounds(100, 100, 533, 320);
		frmRealtimeDatabaseSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmRealtimeDatabaseSimulator.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{20, 0, 70, 0, 70, 20};
		gbl_panel.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblSimulationDuration = new JLabel("Simulation duration");
		GridBagConstraints gbc_lblSimulationDuration = new GridBagConstraints();
		gbc_lblSimulationDuration.insets = new Insets(0, 0, 5, 5);
		gbc_lblSimulationDuration.gridx = 1;
		gbc_lblSimulationDuration.gridy = 1;
		panel.add(lblSimulationDuration, gbc_lblSimulationDuration);
		
		JSpinner spinSimDuration = new JSpinner();
		spinSimDuration.setModel(new SpinnerNumberModel(new Integer(1000), new Integer(100), null, new Integer(100)));
		GridBagConstraints gbc_spinSimDuration = new GridBagConstraints();
		gbc_spinSimDuration.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinSimDuration.insets = new Insets(0, 0, 5, 5);
		gbc_spinSimDuration.gridx = 2;
		gbc_spinSimDuration.gridy = 1;
		panel.add(spinSimDuration, gbc_spinSimDuration);
		
		JLabel lblLambdaForPoisson = new JLabel("lambda for poisson");
		GridBagConstraints gbc_lblLambdaForPoisson = new GridBagConstraints();
		gbc_lblLambdaForPoisson.insets = new Insets(0, 0, 5, 5);
		gbc_lblLambdaForPoisson.gridx = 1;
		gbc_lblLambdaForPoisson.gridy = 2;
		panel.add(lblLambdaForPoisson, gbc_lblLambdaForPoisson);
		
		JSpinner spinPoissonLambda = new JSpinner();
		spinPoissonLambda.setModel(new SpinnerNumberModel(new Double(5), new Double(0), null, new Double(1)));
		GridBagConstraints gbc_spinPoissonLambda = new GridBagConstraints();
		gbc_spinPoissonLambda.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinPoissonLambda.insets = new Insets(0, 0, 5, 5);
		gbc_spinPoissonLambda.gridx = 2;
		gbc_spinPoissonLambda.gridy = 2;
		panel.add(spinPoissonLambda, gbc_spinPoissonLambda);
		
		JLabel lblNumberOfRealtime = new JLabel("Number of realtime data");
		GridBagConstraints gbc_lblNumberOfRealtime = new GridBagConstraints();
		gbc_lblNumberOfRealtime.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfRealtime.gridx = 1;
		gbc_lblNumberOfRealtime.gridy = 3;
		panel.add(lblNumberOfRealtime, gbc_lblNumberOfRealtime);
		
		JSpinner spinRtDataCount = new JSpinner();
		spinRtDataCount.setModel(new SpinnerNumberModel(new Integer(5), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinRtDataCount = new GridBagConstraints();
		gbc_spinRtDataCount.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinRtDataCount.insets = new Insets(0, 0, 5, 5);
		gbc_spinRtDataCount.gridx = 2;
		gbc_spinRtDataCount.gridy = 3;
		panel.add(spinRtDataCount, gbc_spinRtDataCount);
		
		JLabel lblNumberOfBasic = new JLabel("Number of basic data");
		GridBagConstraints gbc_lblNumberOfBasic = new GridBagConstraints();
		gbc_lblNumberOfBasic.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfBasic.gridx = 1;
		gbc_lblNumberOfBasic.gridy = 4;
		panel.add(lblNumberOfBasic, gbc_lblNumberOfBasic);
		
		JSpinner spinBasicDataCount = new JSpinner();
		spinBasicDataCount.setModel(new SpinnerNumberModel(new Integer(5), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinBasicDataCount = new GridBagConstraints();
		gbc_spinBasicDataCount.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinBasicDataCount.insets = new Insets(0, 0, 5, 5);
		gbc_spinBasicDataCount.gridx = 2;
		gbc_spinBasicDataCount.gridy = 4;
		panel.add(spinBasicDataCount, gbc_spinBasicDataCount);
		
		JLabel lblOperationsByTransaction = new JLabel("operations by transaction between");
		GridBagConstraints gbc_lblOperationsByTransaction = new GridBagConstraints();
		gbc_lblOperationsByTransaction.insets = new Insets(0, 0, 5, 5);
		gbc_lblOperationsByTransaction.gridx = 1;
		gbc_lblOperationsByTransaction.gridy = 5;
		panel.add(lblOperationsByTransaction, gbc_lblOperationsByTransaction);
		
		JSpinner spinMinOpByTrans = new JSpinner();
		spinMinOpByTrans.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinMinOpByTrans = new GridBagConstraints();
		gbc_spinMinOpByTrans.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinMinOpByTrans.insets = new Insets(0, 0, 5, 5);
		gbc_spinMinOpByTrans.gridx = 2;
		gbc_spinMinOpByTrans.gridy = 5;
		panel.add(spinMinOpByTrans, gbc_spinMinOpByTrans);
		
		JLabel lblAnd = new JLabel("and");
		GridBagConstraints gbc_lblAnd = new GridBagConstraints();
		gbc_lblAnd.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnd.gridx = 3;
		gbc_lblAnd.gridy = 5;
		panel.add(lblAnd, gbc_lblAnd);
		
		JSpinner spinMaxOpByTrans = new JSpinner();
		spinMinOpByTrans.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Integer value = (Integer) spinMinOpByTrans.getValue();
				Integer maxValue = (Integer) spinMaxOpByTrans.getValue();
				if(maxValue < value){
					maxValue = value;
				}
				spinMaxOpByTrans.setModel(new SpinnerNumberModel(maxValue, value, null, new Integer(1)));
			}
		});
		spinMaxOpByTrans.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinMaxOpByTrans = new GridBagConstraints();
		gbc_spinMaxOpByTrans.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinMaxOpByTrans.insets = new Insets(0, 0, 5, 0);
		gbc_spinMaxOpByTrans.gridx = 4;
		gbc_spinMaxOpByTrans.gridy = 5;
		panel.add(spinMaxOpByTrans, gbc_spinMaxOpByTrans);
		
		JLabel lblPeriodOfRealtime = new JLabel("Period of realtime data between");
		GridBagConstraints gbc_lblPeriodOfRealtime = new GridBagConstraints();
		gbc_lblPeriodOfRealtime.insets = new Insets(0, 0, 5, 5);
		gbc_lblPeriodOfRealtime.gridx = 1;
		gbc_lblPeriodOfRealtime.gridy = 6;
		panel.add(lblPeriodOfRealtime, gbc_lblPeriodOfRealtime);
		
		JSpinner spinMinRtPeriod = new JSpinner();
		spinMinRtPeriod.setModel(new SpinnerNumberModel(new Integer(10), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinMinRtPeriod = new GridBagConstraints();
		gbc_spinMinRtPeriod.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinMinRtPeriod.insets = new Insets(0, 0, 5, 5);
		gbc_spinMinRtPeriod.gridx = 2;
		gbc_spinMinRtPeriod.gridy = 6;
		panel.add(spinMinRtPeriod, gbc_spinMinRtPeriod);
		
		JLabel lblAnd_1 = new JLabel("and");
		GridBagConstraints gbc_lblAnd_1 = new GridBagConstraints();
		gbc_lblAnd_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnd_1.gridx = 3;
		gbc_lblAnd_1.gridy = 6;
		panel.add(lblAnd_1, gbc_lblAnd_1);
		
		JSpinner spinMaxRtPeriod = new JSpinner();
		spinMinRtPeriod.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Integer value = (Integer) spinMinRtPeriod.getValue();
				Integer maxValue = (Integer) spinMaxRtPeriod.getValue();
				if(maxValue < value){
					maxValue = value;
				}
				spinMaxRtPeriod.setModel(new SpinnerNumberModel(maxValue, value, null, new Integer(1)));
			}
		});
		spinMaxRtPeriod.setModel(new SpinnerNumberModel(new Integer(100), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinMaxRtPeriod = new GridBagConstraints();
		gbc_spinMaxRtPeriod.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinMaxRtPeriod.insets = new Insets(0, 0, 5, 0);
		gbc_spinMaxRtPeriod.gridx = 4;
		gbc_spinMaxRtPeriod.gridy = 6;
		panel.add(spinMaxRtPeriod, gbc_spinMaxRtPeriod);
		
		JLabel lblDurationForBasic = new JLabel("Duration for basic data read");
		GridBagConstraints gbc_lblDurationForBasic = new GridBagConstraints();
		gbc_lblDurationForBasic.insets = new Insets(0, 0, 5, 5);
		gbc_lblDurationForBasic.gridx = 1;
		gbc_lblDurationForBasic.gridy = 7;
		panel.add(lblDurationForBasic, gbc_lblDurationForBasic);
		
		JSpinner spinBasicRead = new JSpinner();
		spinBasicRead.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinBasicRead = new GridBagConstraints();
		gbc_spinBasicRead.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinBasicRead.insets = new Insets(0, 0, 5, 5);
		gbc_spinBasicRead.gridx = 2;
		gbc_spinBasicRead.gridy = 7;
		panel.add(spinBasicRead, gbc_spinBasicRead);
		
		JLabel lblWrite = new JLabel("write");
		GridBagConstraints gbc_lblWrite = new GridBagConstraints();
		gbc_lblWrite.insets = new Insets(0, 0, 5, 5);
		gbc_lblWrite.gridx = 3;
		gbc_lblWrite.gridy = 7;
		panel.add(lblWrite, gbc_lblWrite);
		
		JSpinner spinBasicWrite = new JSpinner();
		spinBasicWrite.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinBasicWrite = new GridBagConstraints();
		gbc_spinBasicWrite.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinBasicWrite.insets = new Insets(0, 0, 5, 0);
		gbc_spinBasicWrite.gridx = 4;
		gbc_spinBasicWrite.gridy = 7;
		panel.add(spinBasicWrite, gbc_spinBasicWrite);
		
		JLabel lblDurationForRealtime = new JLabel("Duration for realtime data read");
		GridBagConstraints gbc_lblDurationForRealtime = new GridBagConstraints();
		gbc_lblDurationForRealtime.insets = new Insets(0, 0, 5, 5);
		gbc_lblDurationForRealtime.gridx = 1;
		gbc_lblDurationForRealtime.gridy = 8;
		panel.add(lblDurationForRealtime, gbc_lblDurationForRealtime);
		
		JSpinner spinRtRead = new JSpinner();
		spinRtRead.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinRtRead = new GridBagConstraints();
		gbc_spinRtRead.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinRtRead.insets = new Insets(0, 0, 5, 5);
		gbc_spinRtRead.gridx = 2;
		gbc_spinRtRead.gridy = 8;
		panel.add(spinRtRead, gbc_spinRtRead);
		
		JLabel lblUpdate = new JLabel("update");
		GridBagConstraints gbc_lblUpdate = new GridBagConstraints();
		gbc_lblUpdate.insets = new Insets(0, 0, 5, 5);
		gbc_lblUpdate.gridx = 3;
		gbc_lblUpdate.gridy = 8;
		panel.add(lblUpdate, gbc_lblUpdate);
		
		JSpinner spinRtUpdate = new JSpinner();
		spinRtUpdate.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinRtUpdate = new GridBagConstraints();
		gbc_spinRtUpdate.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinRtUpdate.insets = new Insets(0, 0, 5, 0);
		gbc_spinRtUpdate.gridx = 4;
		gbc_spinRtUpdate.gridy = 8;
		panel.add(spinRtUpdate, gbc_spinRtUpdate);
		
		JButton btnStartSimulation = new JButton("Start simulation");

		JCheckBox chkStepByStep = new JCheckBox("step by step mode");
		GridBagConstraints gbc_chkStepByStep = new GridBagConstraints();
		gbc_chkStepByStep.insets = new Insets(0, 0, 0, 5);
		gbc_chkStepByStep.gridx = 1;
		gbc_chkStepByStep.gridy = 9;
		panel.add(chkStepByStep, gbc_chkStepByStep);
		GridBagConstraints gbc_btnStartSimulation = new GridBagConstraints();
		gbc_btnStartSimulation.anchor = GridBagConstraints.EAST;
		gbc_btnStartSimulation.gridwidth = 3;
		gbc_btnStartSimulation.gridx = 2;
		gbc_btnStartSimulation.gridy = 9;
		panel.add(btnStartSimulation, gbc_btnStartSimulation);
		
		btnStartSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Map<OperationType, Integer> operationDurations = new HashMap<>();
				operationDurations.put(OperationType.BASIC_READ, (Integer) spinBasicRead.getValue());
				operationDurations.put(OperationType.BASIC_WRITE, (Integer) spinBasicWrite.getValue());
				operationDurations.put(OperationType.REALTIME_READ, (Integer) spinRtRead.getValue());
				operationDurations.put(OperationType.REALTIME_UPDATE, (Integer) spinRtUpdate.getValue());
				int simulationDuration = (int) spinSimDuration.getValue();
				double poissonLambda = (double) spinPoissonLambda.getValue();
				int realTimeDataCount = (int) spinRtDataCount.getValue();
				int basicDataCount = (int) spinBasicDataCount.getValue();
				int[] operationsByTransactionRange = {
						(int) spinMinOpByTrans.getValue(),
						(int) spinMaxOpByTrans.getValue()
					};
				int[] realTimeDurationRange = {
						(int) spinMinRtPeriod.getValue(),
						(int) spinMaxRtPeriod.getValue()
					};
				SimulationConfig config = new SimulationConfig(operationDurations, simulationDuration, poissonLambda, realTimeDataCount, basicDataCount, operationsByTransactionRange, realTimeDurationRange);
				DatabaseProcess.startDatabase(config);
				SimulatorProcess process = new SimulatorProcess(config, chkStepByStep.isSelected());
			
				new Thread(new Runnable() {
					@Override
					public void run() {
						process.start();
					}
				}).start();
				SimulationWindow window = new SimulationWindow(process);
			}
		});
		
	}

}
