package strategy;

import java.time.LocalDate;
import java.util.List;

public interface WithdrawalApprovalStrategy {
  boolean grantWithdrawal(int balance, int amount, LocalDate today, List<Transaction> history);
}
