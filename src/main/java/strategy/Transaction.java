package strategy;

import java.time.LocalDate;

// This is an excellent candidate for a "record" in newer Java releases
public class Transaction {
  private String description;
  private int amount;
  private LocalDate date;

  public Transaction(String description, int amount, LocalDate date) {
    this.description = description;
    this.amount = amount;
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public int getAmount() {
    return amount;
  }

  public LocalDate getDate() {
    return date;
  }

  @Override
  public String toString() {
    return date + ": " + description + ". Amount " + amount;
  }
}
