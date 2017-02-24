package fr.matis.bddtr.simulator.api;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.JButton;

import fr.matis.bddtr.IAPI;
import fr.matis.bddtr.database.api.ClientAPI;
import fr.matis.bddtr.model.SimulationContents;
import fr.matis.bddtr.model.data.AbstractData;
import fr.matis.bddtr.model.transaction.Operation;
import fr.matis.bddtr.model.transaction.Status;
import fr.matis.bddtr.model.transaction.Transaction;
import fr.matis.bddtr.simulator.client.SimulatorProcess;

public class ServerAPI implements IAPI{
	static ServerAPI instance;
	private SimulationContents contents;
	private SimulatorProcess process;
	private JButton stepButton;
	
	public ServerAPI(SimulationContents contents, SimulatorProcess simulatorProcess) {
		super();
		instance = this;
		this.contents = contents;
		this.process = simulatorProcess;
	}

	@Override
	public void sendTransaction(Transaction transaction){
		ClientAPI.getInstance().sendTransaction(transaction);
		System.out.println("client > transaction sent : "+transaction);
	}

	@Override
	public void announceData(AbstractData data){
		ClientAPI.getInstance().announceData(data);
		System.out.println("client > data announced : "+data);
	}

	@Override
	public void doStep(){
		ClientAPI.getInstance().doStep();
	}

	@Override
	public void operationUpdate(int opId, Status status){
		Operation operation = contents.getOperation(opId);
		operation.setStatus(status);
		contents.fireChange();
		System.out.println("client > operation updated : "+operation);
	}

	@Override
	public void stepDone(long stamp){
		if(stepButton != null){
			stepButton.setEnabled(true);
		}
		process.stepDone(stamp);
	}

	public static ServerAPI getInstance(){
		return instance;
	}

	public void setStepButton(JButton button) {
		this.stepButton = button;
	}
	
}
