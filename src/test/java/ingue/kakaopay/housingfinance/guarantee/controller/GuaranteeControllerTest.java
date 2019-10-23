package ingue.kakaopay.housingfinance.guarantee.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.guarantee.repository.GuaranteeRepository;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import ingue.kakaopay.housingfinance.institution.repository.InstitutionRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GuaranteeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private InstitutionRepository institutionRepository;

  @Autowired
  private GuaranteeRepository guaranteeRepository;

  @Before
  public void setUp() {
    guaranteeRepository.deleteAll();
    institutionRepository.deleteAll();

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
  public void 년도별_각금융기관중_지원금액이가장큰_금융기관_조회_api테스트() throws Exception {
    mockMvc.perform(get("/largest-guarantee"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.year").value(2013))
        .andExpect(jsonPath("$.bank").value("인규은행"));
  }

  @Test
  public void 년도별_각금융기관_지원금액합계_조회api테스트() throws Exception {
    mockMvc.perform(get("/total-guarantee"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("년도별 각 금융기관 지원금액 합계"))
        .andExpect(jsonPath("$.totalGuaranteeByYearList").value(hasSize(3)))
        .andExpect(jsonPath("$.totalGuaranteeByYearList[0].year").value(2013))
        .andExpect(jsonPath("$.totalGuaranteeByYearList[0].totalAmount").value(43050))
        .andExpect(jsonPath("$.totalGuaranteeByYearList[0].detailAmount").value(hasSize(2)))
        .andExpect(
            jsonPath("$.totalGuaranteeByYearList[0].detailAmount[0].institutionName").value("인규은행"))
        .andExpect(jsonPath("$.totalGuaranteeByYearList[0].detailAmount[0].money").value(22550))
        .andExpect(jsonPath("$.totalGuaranteeByYearList[0].detailAmount[1].institutionName")
            .value("카카오뱅크"))
        .andExpect(jsonPath("$.totalGuaranteeByYearList[0].detailAmount[1].money").value(20500))
        .andExpect(
            jsonPath("$.totalGuaranteeByYearList[1].detailAmount[0].institutionName").value("인규은행"))
        .andExpect(jsonPath("$.totalGuaranteeByYearList[1].detailAmount[0].money").value(22550))
        .andExpect(jsonPath("$.totalGuaranteeByYearList[1].detailAmount[1].institutionName")
            .value("카카오뱅크"))
        .andExpect(jsonPath("$.totalGuaranteeByYearList[1].detailAmount[1].money").value(20500))
        .andExpect(
            jsonPath("$.totalGuaranteeByYearList[2].detailAmount[0].institutionName").value("인규은행"))
        .andExpect(jsonPath("$.totalGuaranteeByYearList[2].detailAmount[0].money").value(22550))
        .andExpect(jsonPath("$.totalGuaranteeByYearList[2].detailAmount[1].institutionName")
            .value("카카오뱅크"))
        .andExpect(jsonPath("$.totalGuaranteeByYearList[2].detailAmount[1].money").value(20500));
  }

  @Test
  public void 테스트데이터를넣었을때_외환은행의_년도별평균금액의_최소는2005최대는2016() throws Exception {
    Institution institution = new Institution("외환은행");
    institutionRepository.save(institution);

    for (int year = 2005; year <= 2016; year++) {
      Guarantee guarantee = Guarantee.builder()
          .year(year)
          .money(1)
          .money(year)
          .build();

      guarantee.setInstitution(institution);

      guaranteeRepository.save(guarantee);
    }

    mockMvc.perform(get("/minmax-guarantee"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.bank").value("외환은행"))
        .andExpect(jsonPath("$.supportAmount[0].year").value(2005))
        .andExpect(jsonPath("$.supportAmount[0].amount").value(2005))
        .andExpect(jsonPath("$.supportAmount[1].year").value(2016))
        .andExpect(jsonPath("$.supportAmount[1].amount").value(2016));
  }
}