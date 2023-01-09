package it.fav.mod14.bank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountTest {
	
	@Test
	void testSuccessfulMakeTransfer() {
		Account account = Account.builder()
				.setOwner("Superpippo")
				.setPhoneNumber("1234")
				.setEmail("super@pippo.com")
				.setBalance(100.0).build();

		assertTrue(account.makeTransfer("XYZ123", 50.0));
		assertEquals(50.0, account.getBalance(), 1E-7);
	}

	@Test
	void testInsufficientFundsMakeTransfer() {
		Account account = Account.builder()
				.setOwner("Superpippo")
				.setPhoneNumber("1234")
				.setEmail("super@pippo.com")
				.setBalance(100.0).build();

		assertFalse(account.makeTransfer("XYZ123", 200.0));
		assertEquals(100.0, account.getBalance(), 1E-7);
	}
	
	@Test
	void testAroundBalanceMakeTransfer() {
		final double AMOUNT = 100.0;
		final double DELTA = 0.1;
		Account account = Account.builder()
				.setOwner("Superpippo")
				.setPhoneNumber("1234")
				.setEmail("super@pippo.com")
				.setBalance(AMOUNT).build();

		assertFalse(account.makeTransfer("XYZ123", AMOUNT+DELTA));
		assertEquals(AMOUNT, account.getBalance(), 1E-7);

		assertTrue(account.makeTransfer("XYZ123", AMOUNT-DELTA));
		assertEquals(DELTA, account.getBalance(), 1E-7);

		account.setBalance(AMOUNT);
		assertTrue(account.makeTransfer("XYZ123", AMOUNT));
		assertEquals(0.0, account.getBalance(), 1E-7);
	}
}
