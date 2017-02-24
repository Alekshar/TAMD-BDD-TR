package fr.matis.bddtr.model.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.matis.bddtr.model.SimulationConfig;
import fr.matis.bddtr.model.data.RealTimeData;

public class Transaction {
	private static final double TIMEOUT_COEFF = 2;
	private static int nextId=0;
	private int id;
	private long timeout;
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
		builder.append(", timeout=");
		builder.append(timeout);
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

	public long getTimeout() {
		return timeout;
	}
	
	public void calculateTimeout(long stamp, SimulationConfig config){
		if(operations.get(0).getType() == OperationType.REALTIME_UPDATE){
			this.timeout = stamp + ((RealTimeData) operations.get(0).getData()).getDuration();
			return;
		}
		long timeout = 0;
		for(Operation op : operations){
			timeout += config.getOperationDurations().get(op.getType());
		}
		timeout = timeout + (long) (((double) timeout) * new Random().nextDouble() * TIMEOUT_COEFF);
		this.timeout = stamp + timeout;
	}
}
