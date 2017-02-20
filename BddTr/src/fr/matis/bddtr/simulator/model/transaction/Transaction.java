package fr.matis.bddtr.simulator.model.transaction;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
	private List<Operation> operations = new ArrayList<Operation>();

	public List<Operation> getOperations() {
		return operations;
	}

	public void addOperation(Operation operation) {
		this.operations.add(operation);
	}

	public Status getStatus(){
		Status status = getStatus().TODO;
		for(Operation operation : operations){
			status = status.append(operation.getStatus());
		}
		return status;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transaction [operations=");
		builder.append(operations);
		builder.append("]");
		return builder.toString();
	}
	
}
