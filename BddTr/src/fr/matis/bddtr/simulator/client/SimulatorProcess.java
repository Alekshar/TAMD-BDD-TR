package fr.matis.bddtr.simulator.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.matis.bddtr.model.SimulationConfig;
import fr.matis.bddtr.model.SimulationContents;
import fr.matis.bddtr.model.data.AbstractData;
import fr.matis.bddtr.model.data.BasicData;
import fr.matis.bddtr.model.data.RealTimeData;
import fr.matis.bddtr.model.transaction.Operation;
import fr.matis.bddtr.model.transaction.OperationType;
import fr.matis.bddtr.model.transaction.Transaction;
import fr.matis.bddtr.simulator.api.ServerAPI;

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
			System.out.println("next user transaction "+nextUserTransaction);
		}
		for(RealTimeData data : contents.getRealTimeDatas()){
			if(data.getNextUpdateStamp() == stamp){
				updateRealTimeData(data);
			}
		}
		stamp++;
		server.doStep();
	}
	
	public void stepDone(long stamp){
		this.stamp = stamp;
		if(!stepByStep){
			processStep();
		}
	}

	private void updateRealTimeData(RealTimeData data) {
		Transaction transaction = new Transaction();
		transaction.addOperation(new Operation(OperationType.REALTIME_UPDATE, data));
		transaction.calculateTimeout(stamp, config);
		contents.addTransaction(transaction);
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
		transaction.calculateTimeout(stamp, config);
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
