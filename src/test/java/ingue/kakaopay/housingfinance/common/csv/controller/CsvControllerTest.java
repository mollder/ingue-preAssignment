package ingue.kakaopay.housingfinance.common.csv.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ingue.kakaopay.housingfinance.guarantee.repository.GuaranteeRepository;
import ingue.kakaopay.housingfinance.institution.repository.InstitutionRepository;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CsvControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private InstitutionRepository institutionRepository;

  @Autowired
  private GuaranteeRepository guaranteeRepository;

  @Before
  public void setUp() {
    this.guaranteeRepository.deleteAll();
    this.institutionRepository.deleteAll();
  }

  @Test
  public void 샘플파일_업로드api호출시_보증데이터개수_1386_은행개수9() throws Exception {
    ClassPathResource resource = new ClassPathResource("data/sampleData.csv");
    InputStream inputStream = resource.getInputStream();
    MockMultipartFile multipartFile = new MockMultipartFile("file", resource.getFilename(), null,
        inputStream);

    this.mockMvc.perform(multipart("/file")
        .file(multipartFile))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.guaranteeList").value(hasSize(1386)))
        .andExpect(jsonPath("$.institutionList").value(hasSize(9)));

    assertThat(this.guaranteeRepository.count()).isEqualTo(1386);
    assertThat(this.institutionRepository.count()).isEqualTo(9);
  }

  @Test
  public void 테스트파일_업로드api호출시_주택도시기금_인규은행존재_금액은2003() throws Exception {
    ClassPathResource testResource = new ClassPathResource("data/testData.csv");
    InputStream inputStream = testResource.getInputStream();
    MockMultipartFile testMultipartFile = new MockMultipartFile("file", testResource.getFilename(),
        null,
        inputStream);

    this.mockMvc.perform(multipart("/file")
        .file(testMultipartFile))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.guaranteeList[0].institution.name").value("주택도시기금"))
        .andExpect(jsonPath("$.guaranteeList[0].money").value(2003))
        .andExpect(jsonPath("$.guaranteeList[1].institution.name").value("인규은행"))
        .andExpect(jsonPath("$.guaranteeList[1].money").value(2003))
        .andExpect(jsonPath("$.institutionList[0].name").value("주택도시기금"))
        .andExpect(jsonPath("$.institutionList[1].name").value("인규은행"))
        ;
  }

  @Test(expected = Exception.class)
  public void csv이외파일형식_업로드시_exception_발생() throws Exception {
    ClassPathResource wrongResource = new ClassPathResource("data/invalidFile.txt");
    InputStream inputStream = wrongResource.getInputStream();
    MockMultipartFile wrongFile = new MockMultipartFile("file", wrongResource.getFilename(), null,
        inputStream);

    this.mockMvc.perform(multipart("/file")
        .file(wrongFile))
        .andDo(print())
        .andExpect(status().is5xxServerError());
  }
}
