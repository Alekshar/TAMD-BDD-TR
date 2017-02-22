package fr.matis.bddtr.simulator.model.transaction;

import java.util.Random;

public enum OperationType {
	BASIC_WRITE,
	BASIC_READ,
	REALTIME_UPDATE, REALTIME_READ;

	static Random random = new Random();
	
	public static OperationType getRandomUserOperation() {
		switch(random.nextInt(3)){
		case 0:
			return OperationType.BASIC_READ;
		case 1:
			return OperationType.BASIC_WRITE;
		default:
			return OperationType.REALTIME_READ;
		}
	}
}
