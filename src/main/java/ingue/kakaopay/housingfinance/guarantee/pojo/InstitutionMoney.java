package ingue.kakaopay.housingfinance.guarantee.pojo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = "institutionName")
public class InstitutionMoney {

  private String institutionName;
  private int money;

  private InstitutionMoney(String institutionName, int money) {
    this.institutionName = institutionName;
    this.money = money;
  }

  public static InstitutionMoney create(String institutionName, int money) {
    return new InstitutionMoney(institutionName, money);
  }

  void addMoney(int money) {
    this.money += money;
  }
}
