package fr.matis.bddtr.simulator.client;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import fr.matis.bddtr.simulator.model.SimulationConfig;
import fr.matis.bddtr.simulator.model.SimulationContentListener;
import fr.matis.bddtr.simulator.model.SimulationContents;
import fr.matis.bddtr.simulator.model.transaction.OperationType;
import fr.matis.bddtr.simulator.model.transaction.Status;
import fr.matis.bddtr.simulator.model.transaction.Transaction;
import fr.matis.bddtr.simulator.model.transaction.TransactionType;

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
		frmSimulationDisplay.setBounds(100, 100, 501, 300);
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
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		JButton btnNextStep = new JButton("Next step");
		panel_2.add(btnNextStep);
		btnNextStep.setEnabled(process.isStepByStep());
		JLabel lblCurrentStep = new JLabel("Current step : "+step);
		panel_2.add(lblCurrentStep);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.SOUTH);
		
		JLabel lblUserRatio = new JLabel("Refused user ratio : 0");
		panel_3.add(lblUserRatio);
		
		JLabel lblUpdateRatio = new JLabel("Refused update ratio : 0");
		panel_3.add(lblUpdateRatio);
		
		JLabel lblGlobal = new JLabel("Global : 0");
		panel_3.add(lblGlobal);
		btnNextStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				process.processStep();
				lblCurrentStep.setText("current step : "+process.getStamp());
			}
		});
		process.getContents().addListener(new  SimulationContentListener() {
			@Override
			public void contentChanged(SimulationContents contents) {
				List<Transaction> transactions = contents.getTransactions();
				int[] user = {0, 0};  //refused, refused+done
				int[] update = {0, 0};  //refused, refused+done
				int[] total = {0, 0};  //refused, refused+done
				for(Transaction tr : transactions){
					if(tr.getType() == TransactionType.REALTIME_UPDATE){
						if(tr.getStatus() == Status.DONE){
							update[1]++;
							total[1]++;
						} else if(tr.getStatus() == Status.REFUSED){
							update[0]++;
							update[1]++;
							total[0]++;
							total[1]++;
						}
					} else {
						if(tr.getStatus() == Status.DONE){
							user[1]++;
							total[1]++;
						} else if(tr.getStatus() == Status.REFUSED){
							user[0]++;
							user[1]++;
							total[0]++;
							total[1]++;
						}
					}
				}
				if(user[1] != 0){
					lblUserRatio.setText("Refused user ratio : "+String.format("%.2f", (user[0]*1./user[1])));
				}
				if(update[1] != 0){
					lblUpdateRatio.setText("Refused update ratio : "+String.format("%.2f", (update[0]*1./update[1])));
				}
				if(total[1] != 0){
					lblGlobal.setText("Global : "+String.format("%.2f", (total[0]*1./total[1])));
				}
			}
		});
	}

}
