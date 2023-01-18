package it.fav.mod14.bank;

public interface Notificator {

	//notify a message to the account holder using email
	void notify(Account account, String message);
}