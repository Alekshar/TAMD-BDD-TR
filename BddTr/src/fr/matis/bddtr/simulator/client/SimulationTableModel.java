package fr.matis.bddtr.simulator.client;

import javax.swing.table.DefaultTableModel;

import fr.matis.bddtr.simulator.model.SimulationConfig;
import fr.matis.bddtr.simulator.model.SimulationContents;
import fr.matis.bddtr.simulator.model.transaction.Operation;
import fr.matis.bddtr.simulator.model.transaction.Transaction;

public class SimulationTableModel extends DefaultTableModel {
	private static final long serialVersionUID = -7314372016872242028L;
	private SimulationContents contents;
	private int columns;

	public SimulationTableModel(SimulationContents contents, SimulationConfig config) {
		super();
		this.contents = contents;
		contents.setListeningModel(this);
		this.columns = 1+config.getOperationsByTransactionRange()[1];
	}
	
	@Override
	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return columns;
	}

	@Override
	public String getColumnName(int arg0) {
		switch(arg0){
		case 0:
			return "transaction";
		case 1:
			return "status";
		default:
			return "op"+(arg0);
		}
	}

	@Override
	public int getRowCount() {
		if(contents == null){
			return 0;
		}
		return contents.getTransactions().size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Transaction transaction = contents.getTransaction(arg0);
		if(transaction == null){
			return "model error";
		}
		switch(arg1){
		case 0:
			return transaction.getType()+" "+transaction.getId()+" -> "+transaction.getStatus();
		default:
			Operation operation = null;
			try {
				operation = transaction.getOperations().get(arg1-1);
			} catch (IndexOutOfBoundsException e) {
				return "";
			}
			return operation.getType()+" -> "+operation.getStatus();
		}
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
	}

}
