package fr.matis.bddtr.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.matis.bddtr.model.data.BasicData;
import fr.matis.bddtr.model.data.RealTimeData;
import fr.matis.bddtr.model.transaction.Operation;
import fr.matis.bddtr.model.transaction.Transaction;
import fr.matis.bddtr.simulator.api.ServerAPI;

public class SimulationContents {
	List<Transaction> transactions = new ArrayList<Transaction>();
	List<Operation> operations = new ArrayList<Operation>();
	List<BasicData> basicDatas = new ArrayList<BasicData>();
	List<RealTimeData> realTimeDatas = new ArrayList<RealTimeData>();
	private Random random = new Random();
	private List<SimulationContentListener> listeners = new ArrayList<SimulationContentListener>();

	public void fill(SimulationConfig config, ServerAPI server) {
		for(int i=0, count=config.getBasicDataCount(); i<count; i++){
			BasicData basicData = new BasicData();
			basicDatas.add(basicData );
			server.announceData(basicData);
		}
		
		int min = config.getRealTimeDurationRange()[0];
		int max = config.getRealTimeDurationRange()[1]-min+1;
		
		for(int i=0, count=config.getRealTimeDataCount(); i<count; i++){
			RealTimeData rtData = new RealTimeData(generateDuration(min, max));
			realTimeDatas.add(rtData );
			server.announceData(rtData);
		}
	}

	private int generateDuration(int min, int max) {
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
		transactions.add(transaction);
		transaction.setId(transactions.size()-1);
		for(Operation op : transaction.getOperations()){
			operations.add(op);
			op.setId(operations.size()-1);
		}
		fireChange();
	}

	public Operation getOperation(int opId) {
		return operations.get(opId);
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void fireChange(){
		for(SimulationContentListener listener : listeners){
			listener.contentChanged(this);
		}
	}

	public void addListener(SimulationContentListener simulationContentListener) {
		listeners.add(simulationContentListener);
	}


}
