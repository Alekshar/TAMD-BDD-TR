package fr.matis.bddtr.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import fr.matis.bddtr.model.transaction.OperationType;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SimulationConfig [operationDurations=");
		builder.append(operationDurations);
		builder.append(", simulationDuration=");
		builder.append(simulationDuration);
		builder.append(", poissonLambda=");
		builder.append(poissonLambda);
		builder.append(", realTimeDataCount=");
		builder.append(realTimeDataCount);
		builder.append(", basicDataCount=");
		builder.append(basicDataCount);
		builder.append(", operationsByTransactionRange=");
		builder.append(Arrays.toString(operationsByTransactionRange));
		builder.append(", realTimeDurationRange=");
		builder.append(Arrays.toString(realTimeDurationRange));
		builder.append("]");
		return builder.toString();
	}
	
	

}
