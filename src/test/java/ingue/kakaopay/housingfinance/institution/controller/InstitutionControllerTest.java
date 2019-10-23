package ingue.kakaopay.housingfinance.institution.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ingue.kakaopay.housingfinance.institution.domain.Institution;
import ingue.kakaopay.housingfinance.institution.repository.InstitutionRepository;
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
public class InstitutionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private InstitutionRepository institutionRepository;

  @Before
  public void setUp() {
    this.institutionRepository.deleteAll();

    for (int i = 1; i <= 3; i++) {
      Institution institution = new Institution("인규은행" + i);

      this.institutionRepository.save(institution);
    }
  }

  @Test
  public void 모든금융기관가져오는API_호출시_결과사이즈_3_code_및_은행이름_1_2_3() throws Exception {
    this.mockMvc.perform(get("/institutions"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("주택금융 공급 금융기관(은행) 목록 현황"))
        .andExpect(jsonPath("$.institutionList").value(hasSize(3)))
        .andExpect(jsonPath("$.institutionList[0].code").value(1))
        .andExpect(jsonPath("$.institutionList[0].name").value("인규은행1"))
        .andExpect(jsonPath("$.institutionList[1].code").value(2))
        .andExpect(jsonPath("$.institutionList[1].name").value("인규은행2"))
        .andExpect(jsonPath("$.institutionList[2].code").value(3))
        .andExpect(jsonPath("$.institutionList[2].name").value("인규은행3"));
  }
}