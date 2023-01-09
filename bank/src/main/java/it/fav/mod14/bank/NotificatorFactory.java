package it.fav.mod14.bank;

public class NotificatorFactory {
	private static NotificatorFactory instance = new NotificatorFactory();
	
	public static NotificatorFactory getInstance() {
		return instance;
	}
	
	private NotificatorFactory() {
	}
	
	Notificator create() {
		if(Configuration.getInstance().isTesting()) {
			return new FakeNotificator();
		} else {
			return new EmailNotificator();
		}
	}
}
