package fr.matis.bddtr.model.transaction;

import fr.matis.bddtr.model.data.AbstractData;

public class Operation {
	private static int nextId;
	private int id;
	private OperationType type;
	private AbstractData data;
	private Status status = Status.TODO;

	public Operation(OperationType type, AbstractData data) {
		super();
		this.id = nextId++;
		this.type = type;
		this.data = data;
	}

	public OperationType getType() {
		return type;
	}
	
	public AbstractData getData() {
		return data;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Operation [id=");
		builder.append(id);
		builder.append(", type=");
		builder.append(type);
		builder.append(", data=");
		builder.append(data);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
