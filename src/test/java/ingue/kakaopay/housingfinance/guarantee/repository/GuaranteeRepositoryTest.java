package ingue.kakaopay.housingfinance.guarantee.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ingue.kakaopay.housingfinance.common.csv.service.CsvService;
import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.guarantee.pojo.AvgAmountByYear;
import ingue.kakaopay.housingfinance.institution.repository.InstitutionRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuaranteeRepositoryTest {

  @Autowired
  private GuaranteeRepository guaranteeRepository;

  @Autowired
  private InstitutionRepository institutionRepository;

  @Autowired
  private CsvService csvService;

  @Before
  public void setUp() throws IOException {
    guaranteeRepository.deleteAll();
    institutionRepository.deleteAll();

    ClassPathResource resource = new ClassPathResource("data/sampleData.csv");
    InputStream inputStream = resource.getInputStream();
    MockMultipartFile multipartFile = new MockMultipartFile("file", resource.getFilename(), null,
        inputStream);

    csvService.readCsvFile(multipartFile);
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

  @Test
  public void 샘플데이터를읽었을때_외환은행의_최솟값은78_최댓값은1702_2005년부터_2016년까지() {
    AvgAmountByYear min = guaranteeRepository.findMinAvgAmountByInstitutionNameBetweenYear("외환은행", 2005, 2016);
    AvgAmountByYear max = guaranteeRepository.findMaxAvgAmountByInstitutionNameBetweenYear("외환은행", 2005, 2016);

    assertThat(min.getAmount()).isEqualTo(78);
    assertThat(max.getAmount()).isEqualTo(1702);
  }

  @Test
  public void 샘플데이터를읽었을때_농협과_수협의_최솟값은_최댓값은_그리고서로값아야함_2005년부터_2017년까지() {
    AvgAmountByYear nonghyupMin = guaranteeRepository.findMinAvgAmountByInstitutionNameBetweenYear("농협은행", 2005, 2017);
    AvgAmountByYear nonghyupMax = guaranteeRepository.findMaxAvgAmountByInstitutionNameBetweenYear("농협은행", 2005, 2017);

    AvgAmountByYear suhyupMin = guaranteeRepository.findMinAvgAmountByInstitutionNameBetweenYear("수협은행", 2005, 2017);
    AvgAmountByYear suhyupMax = guaranteeRepository.findMaxAvgAmountByInstitutionNameBetweenYear("수협은행", 2005, 2017);

    assertThat(nonghyupMin.getYear()).isEqualTo(suhyupMin.getYear());
    assertThat(nonghyupMin.getAmount()).isEqualTo(suhyupMin.getAmount());

    assertThat(nonghyupMax.getYear()).isEqualTo(suhyupMax.getYear());
    assertThat(nonghyupMax.getAmount()).isEqualTo(suhyupMax.getAmount());
  }
}