package strategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account {    private String holder;
  private static int NEXT_ID = 1;
  private int accountId = NEXT_ID++;
  private int balance;
  private FeeCalculationStrategy feeCalculationStrategy;
  private WithdrawalApprovalStrategy withdrawalApprovalStrategy;
  private final List<Transaction> transactions = new ArrayList<>();

  public Account(FeeCalculationStrategy feeCalculationStrategy, WithdrawalApprovalStrategy withdrawalApprovalStrategy) {
    if (feeCalculationStrategy == null || withdrawalApprovalStrategy == null) {
      throw new IllegalArgumentException("Strategies must not be null");
    }
    this.feeCalculationStrategy = feeCalculationStrategy;
    this.withdrawalApprovalStrategy = withdrawalApprovalStrategy;
  }

  public void setApplyFeesStrategy(FeeCalculationStrategy feeCalculationStrategy) {
    if (feeCalculationStrategy == null) {
      throw new IllegalArgumentException("Strategies must not be null");
    }
    this.feeCalculationStrategy = feeCalculationStrategy;
  }

  public void setGrantWithdrawalStrategy(WithdrawalApprovalStrategy withdrawalApprovalStrategy) {
    if (withdrawalApprovalStrategy == null) {
      throw new IllegalArgumentException("Strategies must not be null");
    }
    this.withdrawalApprovalStrategy = withdrawalApprovalStrategy;
  }

  public int getBalance() {
    return this.balance;
  }

  // transactions with positive amounts increase balance,
  // negative amounts decrease balance
  private void transaction(String description, int amount, LocalDate today) {
    Transaction txn = new Transaction(description, amount, today);
    transactions.add(txn);
    balance += amount;
    System.out.println(txn + " final balance is " + balance);
  }

  public int withdrawal(int amount, LocalDate date) {
    if (amount < 0) throw new IllegalArgumentException("Withdrawals should be coded as positive amounts");
    if (withdrawalApprovalStrategy.grantWithdrawal(balance, -amount, date, transactions)) {
      // expect fees to be reported as negative amounts
      int fee = this.feeCalculationStrategy.calculateFee(balance, -amount, date, transactions);
      transaction("Withdrawal, basic amount " + amount, fee - amount, date);
      return amount;
    } else {
      transaction("Withdrawal declined", 0, date);
      return 0;
    }
  }

  public int deposit(int amount, LocalDate date) {
    if (amount < 0) throw new IllegalArgumentException("Deposits should be positive amounts");
    transaction("Deposit", amount, date);
    return amount;
  }
}
