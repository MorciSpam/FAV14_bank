package it.fav.mod14.bank;

public class EmailNotificator {

	//notify a message to the account holder using email
	public void notify(Account account, String message) {
		System.out.println("REALLY REALLY sending email with text: \""+message+"\" to "+account.getEmail());
	}
}
