package strategy;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static strategy.TestBasicAccount.PERMIT_ALL_WITHDRAWAL;

/**
 * Frequent withdrawals describes a limit such that if more than a certain number
 * of withdrawals are performed in a predetermined period, then a fee is applied
 * to the transaction.
 */
public class TestFrequentWithdrawals {
  @Test
  public void feeIsNotAppliedUnderTransactionCount() {
    // Withdrawal fees allowing five withdrawals in 30 days, or a fee of 10
    FeeCalculationStrategy feeStrategy = new FrequentWithdrawalFeeStrategy(30, 5, -10);
    Account account = new Account(feeStrategy, PERMIT_ALL_WITHDRAWAL);
    account.deposit(1000, LocalDate.of(2021, 1, 1));
    Assert.assertEquals("Balance should be 1000", 1000, account.getBalance());

    account.withdrawal(10, LocalDate.of(2021, 2, 1));
    Assert.assertEquals("Balance should be 990", 990, account.getBalance());
    account.withdrawal(10, LocalDate.of(2021, 2, 2));
    Assert.assertEquals("Balance should be 980", 980, account.getBalance());
    account.withdrawal(10, LocalDate.of(2021, 2, 3));
    Assert.assertEquals("Balance should be 970", 970, account.getBalance());
    account.withdrawal(10, LocalDate.of(2021, 2, 4));
    Assert.assertEquals("Balance should be 960", 960, account.getBalance());
  }

  @Test
  public void feeIsAppliedAtTransactionCount() {
    // Withdrawal fees allowing five withdrawals in 30 days, or a fee of 10
    FeeCalculationStrategy feeStrategy = new FrequentWithdrawalFeeStrategy(30, 5, -10);
    Account account = new Account(feeStrategy, PERMIT_ALL_WITHDRAWAL);
    account.deposit(1000, LocalDate.of(2021, 1, 1));
    Assert.assertEquals("Balance should be 1000", 1000, account.getBalance());

    account.withdrawal(10, LocalDate.of(2021, 2, 1));
    Assert.assertEquals("Balance should be 990", 990, account.getBalance());
    account.withdrawal(10, LocalDate.of(2021, 2, 2));
    Assert.assertEquals("Balance should be 980", 980, account.getBalance());
    account.withdrawal(10, LocalDate.of(2021, 2, 3));
    Assert.assertEquals("Balance should be 970", 970, account.getBalance());
    account.withdrawal(10, LocalDate.of(2021, 2, 4));
    Assert.assertEquals("Balance should be 960", 960, account.getBalance());
    account.withdrawal(10, LocalDate.of(2021, 2, 5));
    Assert.assertEquals("Balance should be 950", 950, account.getBalance());

    // This one should incur a fee of -10
    account.withdrawal(10, LocalDate.of(2021, 2, 6));
    Assert.assertEquals("Balance should be 930", 930, account.getBalance());
  }
}
