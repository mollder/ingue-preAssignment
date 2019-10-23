package ingue.kakaopay.housingfinance.guarantee.pojo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 년도별 각 금융기관의 지원금액 합계 api에서 각 개별은행의 지원금액을 나타내는 클래스
 */
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
