package strategy;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class TestBasicAccount {
  // permit all withdrawals
  public static final WithdrawalApprovalStrategy PERMIT_ALL_WITHDRAWAL = (bal, amt, date, history) -> true;
  // No fee ever
  public static final FeeCalculationStrategy NO_FEE_STRATEGY = (bal, amt, date, history) -> 0;

  @Test
  public void accountBasicFunction() {
    Account account = new Account(NO_FEE_STRATEGY, PERMIT_ALL_WITHDRAWAL);
    account.deposit(1000, LocalDate.of(2021, 1, 1));
    Assert.assertEquals("Balance should be 1000", 1000, account.getBalance());

    account.withdrawal(10, LocalDate.of(2021, 2, 1));
    Assert.assertEquals("Balance should be 990", 990, account.getBalance());

    account.deposit(100, LocalDate.of(2021, 2, 2));
    Assert.assertEquals("Balance should be 1090", 1090, account.getBalance());
  }
}
