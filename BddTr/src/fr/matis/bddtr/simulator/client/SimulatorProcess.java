package fr.matis.bddtr.simulator.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.matis.bddtr.simulator.api.ServerAPI;
import fr.matis.bddtr.simulator.model.SimulationConfig;
import fr.matis.bddtr.simulator.model.SimulationContents;
import fr.matis.bddtr.simulator.model.data.AbstractData;
import fr.matis.bddtr.simulator.model.data.BasicData;
import fr.matis.bddtr.simulator.model.data.RealTimeData;
import fr.matis.bddtr.simulator.model.transaction.Operation;
import fr.matis.bddtr.simulator.model.transaction.OperationType;
import fr.matis.bddtr.simulator.model.transaction.Transaction;

public class SimulatorProcess {
	private long stamp = 0;
	private SimulationContents contents = new SimulationContents();
	private SimulationConfig config;
	private long nextUserTransaction;
	private Random random = new Random();
	private ServerAPI server;
	private boolean stepByStep;

	public SimulatorProcess(SimulationConfig config) {
		this(config, false);
	}

	public SimulatorProcess(SimulationConfig config, boolean stepByStep) {
		super();
		this.server = new ServerAPI(contents, this);
		this.stepByStep = stepByStep;
		this.config = config;
		this.nextUserTransaction = PoissonUtil.nextEvent(config.getPoissonLambda());
		contents.fill(config, server);
	}
	
	public void start() {
		processStep();
	}
	
	public void processStep(){
		if(stamp == config.getSimulationDuration()){
			return;
		}
		if(nextUserTransaction == stamp){
			createUserTransaction();
			nextUserTransaction = stamp+PoissonUtil.nextEvent(config.getPoissonLambda()); 
		}
		for(RealTimeData data : contents.getRealTimeDatas()){
			if(data.getNextUpdateStamp() == stamp){
				updateRealTimeData(data);
			}
		}
		stamp++;
		server.doStep();
	}
	
	public void stepDone(){
		if(!stepByStep){
			processStep();
		}
	}

	private void updateRealTimeData(RealTimeData data) {
		Transaction transaction = new Transaction();
		transaction.addOperation(new Operation(OperationType.REALTIME_UPDATE, data));
		contents.addTransaction(transaction);
		data.setStamp(stamp);
		server.sendTransaction(transaction);
	}

	private void createUserTransaction() {
		Transaction transaction = new Transaction();
		for(int i=0, opCount = generateOperationCount(); i<opCount; i++){
			OperationType type = OperationType.getRandomUserOperation();
			AbstractData data;
			if(type == OperationType.REALTIME_READ){
				data = getRandomRealTimeData();
			} else {
				data = getRandomBasicData();
			}
			transaction.addOperation(new Operation(type, data));
		}
		contents.addTransaction(transaction);
		server.sendTransaction(transaction);
	}

	private RealTimeData getRandomRealTimeData() {
		List<RealTimeData> datas = contents.getRealTimeDatas();
		int rand = random.nextInt(datas.size());
		return datas.get(rand);
	}

	private BasicData getRandomBasicData() {
		List<BasicData> datas = contents.getBasicDatas();
		int rand = random.nextInt(datas.size());
		return datas.get(rand);
	}

	private int generateOperationCount() {
		int minOperations = config.getOperationsByTransactionRange()[0];
		int maxOperations = config.getOperationsByTransactionRange()[1] - minOperations + 1;
		return minOperations + random.nextInt(maxOperations);
	}

	public static void main(String[] args) {
		Map<OperationType, Integer> operationDurations = new HashMap<>();
		operationDurations.put(OperationType.BASIC_READ, 5);
		operationDurations.put(OperationType.BASIC_WRITE, 5);
		operationDurations.put(OperationType.REALTIME_READ, 5);
		operationDurations.put(OperationType.REALTIME_UPDATE, 5);
		int simulationDuration = 1000;
		double poissonLambda = 10;
		int realTimeDataCount = 10;
		int basicDataCount = 10;
		int[] operationsByTransactionRange = {1,3};
		int[] realTimeDurationRange = {10,50};
		SimulationConfig config = new SimulationConfig(operationDurations, simulationDuration, poissonLambda, realTimeDataCount, basicDataCount, operationsByTransactionRange, realTimeDurationRange);
		SimulatorProcess process = new SimulatorProcess(config);
		process.start();
	}

	public SimulationContents getContents() {
		return contents;
	}

	public SimulationConfig getConfig() {
		return config;
	}

	public long getStamp() {
		return stamp;
	}

	public boolean isStepByStep() {
		return stepByStep;
	}

}
