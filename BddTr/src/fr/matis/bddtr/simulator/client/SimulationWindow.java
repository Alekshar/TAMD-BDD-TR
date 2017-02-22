package fr.matis.bddtr.simulator.client;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import fr.matis.bddtr.simulator.model.SimulationConfig;
import fr.matis.bddtr.simulator.model.transaction.OperationType;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SimulationWindow {

	private JFrame frmSimulationDisplay;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					Map<OperationType, Integer> operationDurations = new HashMap<>();
					operationDurations.put(OperationType.BASIC_READ, 5);
					operationDurations.put(OperationType.BASIC_WRITE, 5);
					operationDurations.put(OperationType.REALTIME_READ, 5);
					operationDurations.put(OperationType.REALTIME_UPDATE, 5);
					int simulationDuration = 1000;
					double poissonLambda = 10;
					int realTimeDataCount = 20;
					int basicDataCount = 10;
					int[] operationsByTransactionRange = {1,3};
					int[] realTimeDurationRange = {10,50};
					SimulationConfig config = new SimulationConfig(operationDurations, simulationDuration, poissonLambda, realTimeDataCount, basicDataCount, operationsByTransactionRange, realTimeDurationRange);
					SimulatorProcess process = new SimulatorProcess(config, true);

					new SimulationWindow(process);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param process 
	 */
	public SimulationWindow(SimulatorProcess process) {
		initialize(process);
		frmSimulationDisplay.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 * @param process 
	 */
	private void initialize(SimulatorProcess process) {
		frmSimulationDisplay = new JFrame();
		frmSimulationDisplay.setTitle("Simulation display");
		frmSimulationDisplay.setBounds(100, 100, 450, 300);
		frmSimulationDisplay.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmSimulationDisplay.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(new SimulationTableModel(process.getContents(), process.getConfig()));
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		
		int step = process.isStepByStep() ? 0 : process.getConfig().getSimulationDuration();
		JLabel lblCurrentStep = new JLabel("Current step : "+step);
		panel_1.add(lblCurrentStep);
		
		JButton btnNextStep = new JButton("Next step");
		btnNextStep.setEnabled(process.isStepByStep());
		btnNextStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				process.processStep();
				lblCurrentStep.setText("current step : "+process.getStamp());
			}
		});
		panel_1.add(btnNextStep);
	}

}
