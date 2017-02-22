package fr.matis.bddtr.simulator.model.transaction;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
	private static int nextId;
	private int id;
	private List<Operation> operations = new ArrayList<Operation>();

	public Transaction() {
		super();
		this.id = nextId++;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void addOperation(Operation operation) {
		this.operations.add(operation);
	}

	public Status getStatus(){
		Status status = Status.TODO;
		for(Operation operation : operations){
			status = status.append(operation.getStatus());
		}
		return status;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transaction [id=");
		builder.append(id);
		builder.append(", operations=");
		builder.append(operations);
		builder.append("]");
		return builder.toString();
	}

	public int getId() {
		return id;
	}

	public TransactionType getType() {
		if(OperationType.REALTIME_UPDATE.equals(operations.get(0).getType())){
			return TransactionType.REALTIME_UPDATE;
		}
		return TransactionType.USER;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
