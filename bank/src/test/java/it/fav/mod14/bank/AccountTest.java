package it.fav.mod14.bank;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalMatchers;

class AccountTest {
	private static final double EPSILON = 1E-7;

	@Test
	void testBalance30() {
		Account _account = Account.builder()
				.setOwner("Superpippo")
				.setPhoneNumber("1234")
				.setEmail("super@pippo.com")
				.setBalance(0.0).build();

		assertEquals(30.0, _account.setBalance30());
		assertEquals(30.0, _account.getBalance());

	}

	@BeforeAll
	public static void setupConfig() {
		Configuration.getInstance().setTesting(true);
	}
	
	@Test
	void testSuccessfulMakeTransfer() {
		final String DEST_ACCOUNT = "XYZ123";
		final double AMOUNT = 50.0;
		final double BALANCE = 100.0;
		Account account = Account.builder()
				.setOwner("Superpippo")
				.setPhoneNumber("1234")
				.setEmail("super@pippo.com")
				.setBalance(BALANCE).build();

		Notificator notificator = mock(Notificator.class);
		RemoteBankOperator remoteBankOperator = mock(RemoteBankOperator.class);
		when(remoteBankOperator.transfer(anyString(), anyDouble())).thenReturn(true);

		assertTrue(account.makeTransfer(DEST_ACCOUNT, AMOUNT, notificator, remoteBankOperator));
		assertEquals(50.0, account.getBalance(), EPSILON);
		verify(notificator).notify(any(Account.class), anyString());
		verify(remoteBankOperator).transfer(eq(DEST_ACCOUNT), AdditionalMatchers.eq(AMOUNT, EPSILON));
	}

	@Test
	void testInsufficientFundsMakeTransfer() {
		final String DEST_ACCOUNT = "XYZ123";
		final double AMOUNT = 200.0;
		final double BALANCE = 100.0;
		Account account = Account.builder()
				.setOwner("Superpippo")
				.setPhoneNumber("1234")
				.setEmail("super@pippo.com")
				.setBalance(BALANCE).build();

		Notificator notificator = mock(Notificator.class);
		RemoteBankOperator remoteBankOperator = mock(RemoteBankOperator.class);
		when(remoteBankOperator.transfer(anyString(), anyDouble())).thenReturn(true);

		assertFalse(account.makeTransfer(DEST_ACCOUNT, AMOUNT, notificator, remoteBankOperator));
		assertEquals(100.0, account.getBalance(), EPSILON);
		verify(notificator, never()).notify(any(Account.class), anyString());
		verify(remoteBankOperator, never()).transfer(eq(DEST_ACCOUNT), anyDouble());
	}
	
	@Test
	void testAroundBalanceMakeTransfer() {
		final String DEST_ACCOUNT = "XYZ123";
		final double AMOUNT = 100.0;
		final double DELTA = 0.1;
		Account account = Account.builder()
				.setOwner("Superpippo")
				.setPhoneNumber("1234")
				.setEmail("super@pippo.com")
				.setBalance(AMOUNT).build();

		Notificator notificator = mock(Notificator.class);
		RemoteBankOperator remoteBankOperator = mock(RemoteBankOperator.class);
		when(remoteBankOperator.transfer(anyString(), anyDouble())).thenReturn(true);

		assertFalse(account.makeTransfer(DEST_ACCOUNT, AMOUNT+DELTA, notificator, remoteBankOperator));
		assertEquals(AMOUNT, account.getBalance(), EPSILON);
		verify(notificator, never()).notify(any(Account.class), anyString());
		verify(remoteBankOperator, never()).transfer(eq(DEST_ACCOUNT), anyDouble());
		
		clearInvocations(notificator);
		clearInvocations(remoteBankOperator);
		assertTrue(account.makeTransfer(DEST_ACCOUNT, AMOUNT-DELTA, notificator, remoteBankOperator));
		assertEquals(DELTA, account.getBalance(), EPSILON);
		verify(notificator).notify(any(Account.class), anyString());
		verify(remoteBankOperator).transfer(eq(DEST_ACCOUNT), AdditionalMatchers.eq(AMOUNT-DELTA, EPSILON));

		clearInvocations(notificator);
		clearInvocations(remoteBankOperator);
		account.setBalance(AMOUNT);
		assertTrue(account.makeTransfer(DEST_ACCOUNT, AMOUNT, notificator, remoteBankOperator));
		assertEquals(0.0, account.getBalance(), EPSILON);
		verify(notificator).notify(any(Account.class), anyString());
		verify(remoteBankOperator).transfer(eq(DEST_ACCOUNT), AdditionalMatchers.eq(AMOUNT, EPSILON));
	}
}
