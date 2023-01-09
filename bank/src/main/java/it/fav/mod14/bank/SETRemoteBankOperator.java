package it.fav.mod14.bank;

public class SETRemoteBankOperator implements RemoteBankOperator {

	//perform a transaction with a remote back using the SET protocol
	@Override
	public boolean transfer(String destinationAccountCode, double amount) {
		System.out.println("REALLY REALLY transferring "+amount+" to "+destinationAccountCode+" using SET");
		return true;
	}
}

