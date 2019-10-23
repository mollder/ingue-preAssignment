package ingue.kakaopay.housingfinance.guarantee.pojo;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.Test;

public class TotalGuaranteeByYearTest {

  @Test
  public void 객체생성시_DetailAmount내_객체추가() {
    TotalGuaranteeByYear totalGuaranteeByYear = TotalGuaranteeByYear.create(2014, 1000, "인규은행");

    List<InstitutionMoney> institutionMoneyList = totalGuaranteeByYear.getDetailAmount();
    InstitutionMoney institutionMoney = institutionMoneyList.get(0);

    assertThat(institutionMoneyList.size()).isEqualTo(1);
    assertThat(institutionMoney.getMoney()).isEqualTo(1000);
    assertThat(institutionMoney.getInstitutionName()).isEqualTo("인규은행");
  }

  @Test
  public void 객체생성후_같은은행돈1000원추가시_은행돈은2000원() {
    TotalGuaranteeByYear totalGuaranteeByYear = TotalGuaranteeByYear.create(2014, 1000, "인규은행");
    totalGuaranteeByYear.addAmount("인규은행", 1000);

    List<InstitutionMoney> institutionMoneyList = totalGuaranteeByYear.getDetailAmount();
    InstitutionMoney institutionMoney = institutionMoneyList.get(0);

    assertThat(institutionMoney.getMoney()).isEqualTo(2000);
  }

  @Test
  public void 객체생성후_새로운은행돈1000원추가시_총돈은2000원_개별은행은1000원() {
    TotalGuaranteeByYear totalGuaranteeByYear = TotalGuaranteeByYear.create(2014, 1000, "인규은행");
    totalGuaranteeByYear.addAmount("새로운은행", 1000);

    assertThat(totalGuaranteeByYear.getTotalAmount()).isEqualTo(2000);

    List<InstitutionMoney> institutionMoneyList = totalGuaranteeByYear.getDetailAmount();

    for (InstitutionMoney institutionMoney : institutionMoneyList) {
      assertThat(institutionMoney.getMoney()).isEqualTo(1000);
    }
  }

  @Test
  public void 만약_detail_amount에있는_instituion이라면_있는지확인할수있고객체를받을수있어야한다()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    TotalGuaranteeByYear totalGuaranteeByYear = TotalGuaranteeByYear.create(2014, 1000, "인규은행");

    Method method = totalGuaranteeByYear.getClass()
        .getDeclaredMethod("isInstitutionExist", String.class);
    method.setAccessible(true);

    boolean isExist = (boolean) method.invoke(totalGuaranteeByYear, "인규은행");

    assertThat(isExist).isTrue();

    method = totalGuaranteeByYear.getClass()
        .getDeclaredMethod("getInstitutionMoneyByName", String.class);
    method.setAccessible(true);

    InstitutionMoney institutionMoney = (InstitutionMoney) method
        .invoke(totalGuaranteeByYear, "인규은행");

    assertThat(institutionMoney.getInstitutionName()).isEqualTo("인규은행");
  }
}