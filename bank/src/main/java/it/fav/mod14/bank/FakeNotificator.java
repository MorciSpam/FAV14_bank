package it.fav.mod14.bank;

public class FakeNotificator implements Notificator {

	@Override
	public void notify(Account account, String message) {
		System.out.println("FAKING sending message with text: \""+message+"\" to "+account.getOwner());
	}
}
