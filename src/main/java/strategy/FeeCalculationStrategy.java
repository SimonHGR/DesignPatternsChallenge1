package strategy;

import java.time.LocalDate;
import java.util.List;

public interface FeeCalculationStrategy {
  // returned fee will be *added* to balance, hence should
  // generally be negative
  // amount will be coded as a *transaction* amount, describing
  // the effect on the balance. Hence a normal withdrawal
  // will be presented here as a negative amount
  int calculateFee(int balance, int amount, LocalDate today, List<Transaction> history);
}
