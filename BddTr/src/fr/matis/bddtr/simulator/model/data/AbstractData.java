package fr.matis.bddtr.simulator.model.data;

public abstract class AbstractData {
	private static int nextId = 1;
	private int id;

	public AbstractData() {
		this(nextId);
		nextId++;
	}

	public AbstractData(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractData [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
	

}
