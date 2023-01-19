package it.fav.mod14.bank;

import java.util.Optional;

public class Account {
	private static final int ID_LIMIT = 12345;
	private static final String EMAIL_DEFAULT = "";
	private static final String PHONE_NUMBER_DEFAULT = "";
	private static final NotificationType NOTIFICATION_PREFERENCE_DEFAULT = NotificationType.EMAIL;

	public static class Builder {
		private Optional<String> owner = Optional.empty();
		private Optional<Double> balance = Optional.empty();
		private Optional<Integer> id = Optional.empty();
		private Optional<String> email = Optional.empty();
		private Optional<String> phoneNumber = Optional.empty();
		private Optional<NotificationType> notificationPreference = Optional.empty();

		private Builder() {
		}

		public Builder setOwner(String owner) {
			this.owner = Optional.of(owner);
			return this;
		}

		public Builder setBalance(double balance) {
			this.balance = Optional.of(balance);
			return this;
		}

		public Builder setId(int id) {
			this.id = Optional.of(id);
			return this;
		}

		public Builder setEmail(String email) {
			this.email = Optional.of(email);
			return this;
		}

		public Builder setPhoneNumber(String phoneNumber) {
			this.phoneNumber = Optional.of(phoneNumber);
			return this;
		}

		public Builder setNotificationPreference(NotificationType notificationPreference) {
			this.notificationPreference = Optional.of(notificationPreference);
			return this;
		}

		public Account build() {
			if(this.owner.isEmpty()) {
				throw new IllegalStateException("Owner must be set");
			}
			String owner = this.owner.get();
			String email = this.email.orElse(EMAIL_DEFAULT);
			String phoneNumber = this.phoneNumber.orElse(PHONE_NUMBER_DEFAULT);
			int id = this.id.orElse((int) (Math.random() * ID_LIMIT));
			double balance = this.balance.orElse(.0);
			NotificationType notificationPreference = this.notificationPreference
					.orElse(NOTIFICATION_PREFERENCE_DEFAULT);
			return new Account(owner, email, phoneNumber, id, balance, notificationPreference);
		}
	}

	private String owner;
	private double balance;
	private int id;
	private String email;
	private String phoneNumber;
	private NotificationType notificationPreference;

	public static Builder builder() {
		return new Builder();
	}

	private Account(String owner, String email, String phoneNumber, int id, double balance,
			NotificationType notificationPreference) {
		this.owner = owner;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.id = id;
		this.balance = balance;
		this.notificationPreference = notificationPreference;
	}

	public boolean withdraw(double amount) {
		if (this.balance - amount >= 0) {
			this.balance -= amount;
			return true;
		} else {
			return false;
		}
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}
	
	public String getOwner() {
		return owner;
	}

	public NotificationType getNotificationPreference() {
		return notificationPreference;
	}

	public void setNotificationPreference(NotificationType notificationPreference) {
		this.notificationPreference = notificationPreference;
	}

	public void clear() {
		this.balance = 0;
	}
	
	public boolean makeTransfer(String destinationAccountCode, double amount, Notificator notificator, RemoteBankOperator remoteBankOperator) {
		if(getBalance() >= amount) {
			if(remoteBankOperator.transfer(destinationAccountCode, amount)) {
				withdraw(amount);
				notificator.notify(
					this,
					String.format("Trasferred %f from %s to %s", 
							amount, toString(), destinationAccountCode));
				return true;
			} else {
				notificator.notify(
					this,
					String.format("Failed to trasfer %f from %s to %s", 
					amount, toString(), destinationAccountCode));
				return false;
			}
		} else {
			return false;
		}
	}
	
	public double setBalance50() {
		this.balance=50.0;
		return this.balance;
	}

	@Override
	public String toString() {
		return String.format("{Account id: %d, owner: %s, balance: %f}", this.id, this.owner, this.balance);
	}
	
}
