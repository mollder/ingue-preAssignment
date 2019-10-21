package ingue.kakaopay.housingfinance.guarantee.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import ingue.kakaopay.housingfinance.institution.repository.InstitutionRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GuaranteeRepositoryTest {

  @Autowired
  private GuaranteeRepository guaranteeRepository;

  @Autowired
  private InstitutionRepository institutionRepository;

  @Before
  public void setUp() {
    institutionRepository.deleteAll();
    guaranteeRepository.deleteAll();

    Institution institution = new Institution("인규은행");
    Institution institution2 = new Institution("카카오뱅크");

    List<Institution> institutionList = new ArrayList<>();
    institutionList.add(institution);
    institutionList.add(institution2);

    institutionList = institutionRepository.saveAll(institutionList);

    for (int year = 2013; year < 2016; year++) {
      for (int money = 2040; money <= 2060; money++) {
        Guarantee guarantee = Guarantee.builder()
            .year(year)
            .month(2)
            .money(money)
            .build();

        guarantee.setInstitution(institutionList.get(money % 2));

        guaranteeRepository.save(guarantee);
      }
    }
  }

  @Test
  public void guarantee객체3개삽입후_findAll메소드실행시_3개객체반환() {
    List<Guarantee> guaranteeList = guaranteeRepository.findAll();

    assertThat(guaranteeList.size()).isEqualTo(3);
  }

  @Test
  public void guarantee_년도별로가져왔을때_년도오름차순으로되야함() {
    List<Guarantee> guaranteeListOrderByYear = guaranteeRepository
        .findAllInnerJoinWithInstitutionOrderByYear();

    Guarantee current = guaranteeListOrderByYear.get(0);
    Guarantee next;

    for (int i = 1; i < guaranteeListOrderByYear.size(); i++) {
      next = guaranteeListOrderByYear.get(i);

      int year = current.getYear();
      int nextYear = next.getYear();

      current = next;

      assertThat(year <= nextYear).isTrue();
    }
  }
}