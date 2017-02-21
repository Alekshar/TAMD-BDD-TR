package fr.matis.bddtr.simulator.api;

import java.util.LinkedList;
import java.util.Queue;

import fr.matis.bddtr.simulator.client.SimulatorProcess;
import fr.matis.bddtr.simulator.model.SimulationContents;
import fr.matis.bddtr.simulator.model.data.AbstractData;
import fr.matis.bddtr.simulator.model.transaction.Operation;
import fr.matis.bddtr.simulator.model.transaction.Status;
import fr.matis.bddtr.simulator.model.transaction.Transaction;

public class ServerAPI {
	private SimulationContents contents;
	private SimulatorProcess process;
	
	private Queue<Operation> operations = new LinkedList<Operation>();
	
	public ServerAPI(SimulationContents contents, SimulatorProcess simulatorProcess) {
		super();
		this.contents = contents;
		this.process = simulatorProcess;
	}

	public void sendTransaction(Transaction transaction){
		System.out.println("transaction sent : "+transaction);
		transactionHandled(transaction.getId());
		for(Operation op : transaction.getOperations()){
			operations.add(op);
		}
	}
	
	public void announceData(AbstractData data){
		System.out.println("data announced : "+data);
		dataHandled(data.getId());
	}

	public void doStep(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(!operations.isEmpty()){
					operationUpdate(operations.poll().getId(), Status.DONE);	
				}
				stepDone();
			}
		}).start();
	}

	public void transactionHandled(int trId){
		Transaction transaction = contents.getTransaction(trId);
		System.out.println("transaction handled : "+transaction);
	}

	public void dataHandled(int dataId){
		System.out.println("data handled : "+dataId);
	}
	
	public void operationUpdate(int opId, Status status){
		Operation operation = contents.getOperation(opId);
		operation .setStatus(status);
		System.out.println("operation updated : "+operation);
	}

	public void stepDone(){
		process.processStep();
	}
	
}
