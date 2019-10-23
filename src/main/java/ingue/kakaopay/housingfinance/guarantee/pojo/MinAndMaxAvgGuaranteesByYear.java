package ingue.kakaopay.housingfinance.guarantee.pojo;

import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MinAndMaxAvgGuaranteesByYear {

  private String bank;
  List<AvgAmountByYear> supportAmount;

  private MinAndMaxAvgGuaranteesByYear(String bank, List<AvgAmountByYear> supportAmount) {
    this.bank = bank;
    this.supportAmount = supportAmount;
  }

  public static MinAndMaxAvgGuaranteesByYear create(String bank,
      List<AvgAmountByYear> supportAmount) {
    return new MinAndMaxAvgGuaranteesByYear(bank, supportAmount);
  }
}