package fr.matis.bddtr.database.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import fr.matis.bddtr.database.api.ClientAPI;
import fr.matis.bddtr.model.SimulationConfig;
import fr.matis.bddtr.model.data.AbstractData;
import fr.matis.bddtr.model.data.BasicData;
import fr.matis.bddtr.model.data.RealTimeData;
import fr.matis.bddtr.model.transaction.Operation;
import fr.matis.bddtr.model.transaction.OperationType;
import fr.matis.bddtr.model.transaction.Status;
import fr.matis.bddtr.model.transaction.Transaction;

public class DatabaseProcess implements Runnable{

	private ClientAPI api = new ClientAPI(this);
	private SimulationConfig config;
	private int stamp = 0;
	
	private Map<Integer, AbstractData> datas = new HashMap<Integer, AbstractData>();
	
	private Queue<Transaction> transactions = new LinkedList<Transaction>();
	private Transaction currentTransaction;
	private Queue<Operation> operations = new LinkedList<Operation>();
	private Operation currentOperation;
	private int operationEndStamp = -1;
	
	private ReentrantLock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();
	private boolean waiting;
	
	public DatabaseProcess(SimulationConfig config) {
		super();
		this.config = config;
	}


	@Override
	public void run() {
		while(true){
			lock.lock();
			try {
				waiting = true;
				cond.await();
				waiting = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lock.unlock();
			if(operationEndStamp == stamp){
				Status newStatus = Status.DONE;
				
				//check realtime data process
				if(currentOperation.getData() instanceof RealTimeData){
					RealTimeData data = (RealTimeData) datas.get(currentOperation.getData().getId());
					if(currentOperation.getType() == OperationType.REALTIME_UPDATE){
						data.setStamp(stamp);
					} else {
						if(data.getNextUpdateStamp() < stamp){
							newStatus = Status.REFUSED;
						}
					}
				} 
				
				api.operationUpdate(currentOperation.getId(), newStatus);
				currentOperation = null;
			}
			else if(currentTransaction != null
					&& currentTransaction.getTimeout() <= stamp){
				//clear current transaction if timeout reached
				api.operationUpdate(currentOperation.getId(), Status.REFUSED);
				while(!operations.isEmpty()){
					api.operationUpdate(operations.poll().getId(), Status.REFUSED);
				}
				currentOperation = null;
			}
			
			//check timeout in next transactions
			List<Transaction> toRemove = new ArrayList<Transaction>();
			for(Transaction transaction : transactions){
				if(transaction.getTimeout() > stamp){
					continue;
				}
				Queue<Operation> operations = new LinkedList<Operation>(transaction.getOperations());
				while(!operations.isEmpty()){
					api.operationUpdate(operations.poll().getId(), Status.REFUSED);
				}
				toRemove.add(transaction);
			}
			for(Transaction tr : toRemove){
				transactions.remove(tr);
			}
			
			if(currentOperation == null){
				if(operations.isEmpty()){
					currentTransaction = null;
					if(!transactions.isEmpty()){
						currentTransaction = transactions.poll();
						operations = new LinkedList<Operation>(currentTransaction.getOperations());
					}
				} 
				currentOperation = operations.poll();
				if(currentOperation != null){
					operationEndStamp = stamp+getDuration(currentOperation.getType());
				}
			}
			api.stepDone(stamp);
			stamp++;
		}
		
	}
	
	private int getDuration(OperationType type) {
		return 1;
	}

	public void addTransaction(Transaction transaction){
		transactions.add(transaction);
	}

	public void addData(AbstractData data){
		datas.put(data.getId(), data);
	}
	
	public void doStep(){
		while(!waiting){
		}
		lock.lock();
		cond.signal();
		lock.unlock();
	}

	public long getStamp() {
		return stamp;
	}
	
	public static Thread startDatabase(SimulationConfig config){
		Thread thread = new Thread(new DatabaseProcess(config));
		thread.start();
		return thread;
	}
	
}
