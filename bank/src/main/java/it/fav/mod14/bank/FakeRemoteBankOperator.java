package it.fav.mod14.bank;

public class FakeRemoteBankOperator implements RemoteBankOperator {

	@Override
	public boolean transfer(String destinationAccountCode, double amount) {
		System.out.println("FAKING transferring "+amount+" to "+destinationAccountCode);
		return true;
	}
}
