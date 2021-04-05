package strategy;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static strategy.TestBasicAccount.NO_FEE_STRATEGY;

public class TestOverdraftWithdrawal {
  @Test
  public void withdrawOwnMoneySucceeds() {
    Account account = new Account(NO_FEE_STRATEGY, OverdraftWithdrawalApprovalStrategy.borrowing(1000));
    account.deposit(1000, LocalDate.of(2021, 1, 1));
    Assert.assertEquals("Balance should be 1000", 1000, account.getBalance());

    account.withdrawal(1000, LocalDate.of(2021, 2, 1));
    Assert.assertEquals("Balance should be zero", 0, account.getBalance());
  }

  @Test
  public void withdrawUnapprovedFails() {
    Account account = new Account(NO_FEE_STRATEGY, OverdraftWithdrawalApprovalStrategy.borrowing(1000));
    account.deposit(1000, LocalDate.of(2021, 1, 1));
    Assert.assertEquals("Balance should be 1000", 1000, account.getBalance());

    account.withdrawal(2001, LocalDate.of(2021, 2, 1));
    Assert.assertEquals("Balance should be 1000", 1000, account.getBalance());
  }

  @Test
  public void withdrawApprovedSucceeds() {
    Account account = new Account(NO_FEE_STRATEGY, OverdraftWithdrawalApprovalStrategy.borrowing(1000));
    account.deposit(1000, LocalDate.of(2021, 1, 1));
    Assert.assertEquals("Balance should be 1000", 1000, account.getBalance());

    account.withdrawal(2000, LocalDate.of(2021, 2, 1));
    Assert.assertEquals("Balance should be -1000", -1000, account.getBalance());
  }
}
