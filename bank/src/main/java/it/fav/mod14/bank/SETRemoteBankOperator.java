package it.fav.mod14.bank;

public class SETRemoteBankOperator {

	//perform a transaction with a remote back using the SET protocol
	public boolean transfer(String destinationAccountCode, double amount) {
		System.out.println("REALLY REALLY transferring "+amount+" to "+destinationAccountCode+" using SET");
		return true;
	}
}

