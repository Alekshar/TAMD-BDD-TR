package fr.matis.bddtr.simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.matis.bddtr.simulator.api.ServerAPI;
import fr.matis.bddtr.simulator.model.data.BasicData;
import fr.matis.bddtr.simulator.model.data.RealTimeData;
import fr.matis.bddtr.simulator.model.transaction.Operation;
import fr.matis.bddtr.simulator.model.transaction.Transaction;

public class SimulationContents {
	Map<Integer, Transaction> transactions = new HashMap<Integer, Transaction>();
	Map<Integer, Operation> operations = new HashMap<Integer, Operation>();
	List<BasicData> basicDatas = new ArrayList<BasicData>();
	List<RealTimeData> realTimeDatas = new ArrayList<RealTimeData>();
	private Random random = new Random();

	public void fill(SimulationConfig config, ServerAPI server) {
		for(int i=0, count=config.getBasicDataCount(); i<count; i++){
			BasicData basicData = new BasicData();
			basicDatas.add(basicData );
			server.announceData(basicData);
		}
		
		int min = config.getRealTimeDurationRange()[0];
		int max = config.getRealTimeDurationRange()[1]-min;
		
		for(int i=0, count=config.getRealTimeDataCount(); i<count; i++){
			RealTimeData rtData = new RealTimeData(generateDuration(min, max));
			realTimeDatas.add(rtData );
			server.announceData(rtData);
		}
	}

	private int generateDuration(int min, int max) {
		if(max == 0){
			return min;
		}
		return min + random.nextInt(max);
	}

	public List<BasicData> getBasicDatas() {
		return basicDatas;
	}

	public List<RealTimeData> getRealTimeDatas() {
		return realTimeDatas;
	}

	public Transaction getTransaction(int trId) {
		return transactions.get(trId);
	}

	public void addTransaction(Transaction transaction) {
		transactions.put(transaction.getId(), transaction);
		for(Operation op : transaction.getOperations()){
			operations.put(op.getId(), op);
		}
	}

	public Operation getOperation(int opId) {
		return operations.get(opId);
	}

}
