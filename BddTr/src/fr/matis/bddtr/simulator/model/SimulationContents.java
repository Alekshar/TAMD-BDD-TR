package fr.matis.bddtr.simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.matis.bddtr.simulator.model.data.AbstractData;
import fr.matis.bddtr.simulator.model.data.BasicData;
import fr.matis.bddtr.simulator.model.data.RealTimeData;
import fr.matis.bddtr.simulator.model.transaction.Transaction;

public class SimulationContents {
	Map<Integer, Transaction> transactions = new HashMap<Integer, Transaction>();
	int nextTransactionId = 1;
	List<BasicData> basicDatas = new ArrayList<BasicData>();
	List<RealTimeData> realTimeDatas = new ArrayList<RealTimeData>();
	private Random random = new Random();

	public void fill(SimulationConfig config) {
		for(int i=0, count=config.getBasicDataCount(); i<count; i++){
			BasicData basicData = new BasicData();
			basicDatas.add(basicData );
			//TODO communicate data creation to db
		}
		
		int min = config.getRealTimeDurationRange()[0];
		int max = config.getRealTimeDurationRange()[1]-min;
		
		for(int i=0, count=config.getRealTimeDataCount(); i<count; i++){
			RealTimeData rtData = new RealTimeData(generateDuration(min, max));
			realTimeDatas.add(rtData );
			//TODO communicate data creation to db
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

}
