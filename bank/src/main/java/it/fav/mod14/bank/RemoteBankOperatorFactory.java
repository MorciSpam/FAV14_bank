package it.fav.mod14.bank;

public class RemoteBankOperatorFactory {
	private static RemoteBankOperatorFactory instance = new RemoteBankOperatorFactory();
	
	public static RemoteBankOperatorFactory getInstance() {
		return instance;
	}
	
	private RemoteBankOperatorFactory() {
	}
	
	public RemoteBankOperator create() {
		if(Configuration.getInstance().isTesting()) {
			return new FakeRemoteBankOperator();
		} else {
			return new SETRemoteBankOperator();
		}
	}
}
