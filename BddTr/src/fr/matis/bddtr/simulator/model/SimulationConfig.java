package fr.matis.bddtr.simulator.model;

import java.util.HashMap;
import java.util.Map;

import fr.matis.bddtr.simulator.model.transaction.OperationType;

public class SimulationConfig {
	private Map<OperationType, Integer> operationDurations = new HashMap<OperationType, Integer>();
	private int simulationDuration;
	private double poissonLambda;
	private int realTimeDataCount;
	private int basicDataCount;
	private int operationsByTransactionRange[];
	private int realTimeDurationRange[];
	
	public SimulationConfig(Map<OperationType, Integer> operationDurations, int simulationDuration,
			double poissonLambda, int realTimeDataCount, int basicDataCount, int[] operationsByTransactionRange,
			int[] realTimeDurationRange) {
		super();
		this.operationDurations = operationDurations;
		this.simulationDuration = simulationDuration;
		this.poissonLambda = poissonLambda;
		this.realTimeDataCount = realTimeDataCount;
		this.basicDataCount = basicDataCount;
		this.operationsByTransactionRange = operationsByTransactionRange;
		this.realTimeDurationRange = realTimeDurationRange;
	}
	
	public Map<OperationType, Integer> getOperationDurations() {
		return operationDurations;
	}
	public int getSimulationDuration() {
		return simulationDuration;
	}
	public double getPoissonLambda() {
		return poissonLambda;
	}
	public int getRealTimeDataCount() {
		return realTimeDataCount;
	}
	public int getBasicDataCount() {
		return basicDataCount;
	}
	public int[] getOperationsByTransactionRange() {
		return operationsByTransactionRange;
	}
	public int[] getRealTimeDurationRange() {
		return realTimeDurationRange;
	}
	
	

}