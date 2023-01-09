package it.fav.mod14.bank;

public class Configuration {
	private static Configuration instance = new Configuration();
	private boolean testing = false;
	
	private Configuration() {
	}
	
	public static Configuration getInstance() {
		return instance;
	}
	
	public void setTesting(boolean testing) {
		this.testing = testing;
	}

	public boolean isTesting() {
		return testing;
	}
}
