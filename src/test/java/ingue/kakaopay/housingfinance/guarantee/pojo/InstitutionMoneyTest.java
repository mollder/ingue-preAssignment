package ingue.kakaopay.housingfinance.guarantee.pojo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class InstitutionMoneyTest {

  @Test
  public void 객체생성시금액에_돈을추가했을때_성공적으로추가되는경우() {
    InstitutionMoney institutionMoney = InstitutionMoney.create("테스트은행",1000);

    assertThat(institutionMoney.getMoney()).isEqualTo(1000);

    institutionMoney.addMoney(1000);

    assertThat(institutionMoney.getMoney()).isEqualTo(2000);
  }
}