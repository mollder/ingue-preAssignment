package ingue.kakaopay.housingfinance.guarantee.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.guarantee.pojo.AvgAmountByYear;
import ingue.kakaopay.housingfinance.guarantee.pojo.InstitutionMoney;
import ingue.kakaopay.housingfinance.guarantee.pojo.LargestGuaranteeInstitutionByYear;
import ingue.kakaopay.housingfinance.guarantee.pojo.MinAndMaxAvgGuaranteesByYear;
import ingue.kakaopay.housingfinance.guarantee.pojo.TotalGuaranteeByYear;
import ingue.kakaopay.housingfinance.guarantee.repository.GuaranteeRepository;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GuaranteeServiceTest {

  @InjectMocks
  private GuaranteeService guaranteeService;

  @Mock
  private GuaranteeRepository guaranteeRepository;

  @Before
  public void setUp() {
    Institution institution = new Institution("인규은행");
    Institution institution2 = new Institution("카카오뱅크");
    Institution institution3 = new Institution("외환은행");

    List<Institution> institutionList = new ArrayList<>();
    institutionList.add(institution);
    institutionList.add(institution2);
    institutionList.add(institution3);

    List<Guarantee> guaranteeList = new ArrayList<>();

    for (int year = 2013; year < 2016; year++) {
      for (int money = 2040; money <= 2060; money++) {
        Guarantee guarantee = Guarantee.builder()
            .year(year)
            .month(2)
            .money(money)
            .build();

        guarantee.setInstitution(institutionList.get(money % 3));
        guaranteeList.add(guarantee);
      }
    }

    when(guaranteeRepository.findAllInnerJoinWithInstitutionOrderByYear())
        .thenReturn(guaranteeList);

    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void 샘플데이터를처리했을때_년도별_가장큰금액을지원한_기관과년도는_외환은행_2013년() {
    LargestGuaranteeInstitutionByYear largestGuaranteeInstitutionByYear = guaranteeService
        .getLargestGuaranteeInstitutionByYear();

    assertThat(largestGuaranteeInstitutionByYear.getBank()).isEqualTo("외환은행");
    assertThat(largestGuaranteeInstitutionByYear.getYear()).isEqualTo(2013);
  }

  @Test
  public void 샘플데이터를처리했을때_2013년도부터_2015년도까지_총돈합은_23050_인규은행돈22550_카카오뱅크돈_20500() {
    List<TotalGuaranteeByYear> totalGuaranteeByYear = guaranteeService
        .findTotalGuaranteeByYear();

    assertThat(totalGuaranteeByYear.size()).isEqualTo(3);

    for (int i = 0; i < 3; i++) {
      TotalGuaranteeByYear findTotalGuaranteeByYear = totalGuaranteeByYear.get(i);

      assertThat(findTotalGuaranteeByYear.getYear()).isEqualTo(2013 + i);
      assertThat(findTotalGuaranteeByYear.getTotalAmount()).isEqualTo(43050);

      List<InstitutionMoney> detailAmount = findTotalGuaranteeByYear.getDetailAmount();

      InstitutionMoney institutionMoney = detailAmount.get(0);

      assertThat(institutionMoney.getInstitutionName()).isEqualTo("인규은행");
      assertThat(institutionMoney.getMoney()).isEqualTo(14343);

      InstitutionMoney institutionMoney2 = detailAmount.get(1);

      assertThat(institutionMoney2.getInstitutionName()).isEqualTo("카카오뱅크");
      assertThat(institutionMoney2.getMoney()).isEqualTo(14350);
    }
  }

  @Test
  public void totalGuaranteeByYear리스트안객체가_year로찾았을때_확인가능()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    List<TotalGuaranteeByYear> totalGuaranteeByYearList = new ArrayList<>();

    TotalGuaranteeByYear totalGuaranteeByYear = TotalGuaranteeByYear.create(2014, 1000, "인규은행");
    totalGuaranteeByYearList.add(totalGuaranteeByYear);

    Method method = guaranteeService.getClass()
        .getDeclaredMethod("isExistYear", int.class, List.class);
    method.setAccessible(true);

    boolean isExist = (boolean) method.invoke(guaranteeService, 2013, totalGuaranteeByYearList);
    assertThat(isExist).isFalse();

    isExist = (boolean) method.invoke(guaranteeService, 2014, totalGuaranteeByYearList);

    assertThat(isExist).isTrue();

    method = guaranteeService.getClass()
        .getDeclaredMethod("getTotalGuaranteeUseYear", int.class, List.class);
    method.setAccessible(true);

    TotalGuaranteeByYear totalGuaranteeByYear1 = (TotalGuaranteeByYear) method
        .invoke(guaranteeService, 2014, totalGuaranteeByYearList);

    assertThat(totalGuaranteeByYear1.getYear()).isEqualTo(2014);
  }
}