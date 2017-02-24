package fr.matis.bddtr.model.transaction;

public enum Status {
	TODO,
	IN_PROGRESS,
	DONE,
	REFUSED;

	public Status append(Status status) {
		if(this == IN_PROGRESS
				|| status == IN_PROGRESS){
			return IN_PROGRESS;
		}
		if(this == REFUSED || status == REFUSED){
			return REFUSED;
		}
		return status;
	}
}
