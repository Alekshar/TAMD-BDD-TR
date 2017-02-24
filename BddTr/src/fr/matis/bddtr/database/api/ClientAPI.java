package fr.matis.bddtr.database.api;

import fr.matis.bddtr.IAPI;
import fr.matis.bddtr.database.server.DatabaseProcess;
import fr.matis.bddtr.model.data.AbstractData;
import fr.matis.bddtr.model.transaction.Status;
import fr.matis.bddtr.model.transaction.Transaction;
import fr.matis.bddtr.simulator.api.ServerAPI;

public class ClientAPI implements IAPI{
	
	DatabaseProcess db;
	static ClientAPI instance;
	
	public ClientAPI(DatabaseProcess db) {
		super();
		instance = this;
		this.db = db;
	}

	@Override
	public void stepDone(long stamp) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				ServerAPI.getInstance().stepDone(stamp);
			}
		}).start();
	}

	@Override
	public void sendTransaction(Transaction transaction) {
		db.addTransaction(transaction);
	}

	@Override
	public void announceData(AbstractData data) {
		db.addData(data);
	}

	@Override
	public void doStep() {
		db.doStep();				
	}

	public static ClientAPI getInstance(){
		return instance;
	}
	
	@Override
	public void operationUpdate(int opId, Status status) {
		System.out.println("server > "+db.getStamp()+" > op updated "+opId+" -> "+status);
		ServerAPI.getInstance().operationUpdate(opId, status);
	}

}
