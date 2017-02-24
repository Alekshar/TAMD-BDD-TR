package fr.matis.bddtr;

import fr.matis.bddtr.model.data.AbstractData;
import fr.matis.bddtr.model.transaction.Status;
import fr.matis.bddtr.model.transaction.Transaction;

public interface IAPI {

	void stepDone(long currentStamp);

	void sendTransaction(Transaction transaction);

	void announceData(AbstractData data);

	void doStep();

	void operationUpdate(int opId, Status status);

}
