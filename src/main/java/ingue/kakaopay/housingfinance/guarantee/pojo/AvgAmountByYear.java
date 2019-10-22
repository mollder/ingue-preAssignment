package ingue.kakaopay.housingfinance.guarantee.pojo;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class AvgAmountByYear {

  private int year;
  private int amount;

  public AvgAmountByYear(int year, double amount) {
    this.year = year;
    this.amount = (int) amount;
  }
}