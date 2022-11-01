package activities;

import example.BankAccount;
import example.NotEnoughFundsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Activity2 {

    private BankAccount account;

    @Test
    public void notEnoughFunds() {

        // Create an object for BankAccount class
        account = new BankAccount(9);
        // Assertion for exception
        assertThrows(NotEnoughFundsException.class, () -> account.withdraw(10));
    }

    @Test
    public void enoughFunds() {
        // Create an object for BankAccount class
        account = new BankAccount(100);
        // Assertion for exception
        assertDoesNotThrow(() -> account.withdraw(100));
    }
}
