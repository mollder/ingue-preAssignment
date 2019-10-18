package ingue.kakaopay.housingfinance.common.csv.reader;

import static org.assertj.core.api.Assertions.assertThat;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsvBufferedReader.class)
public class CsvReaderTest {

  @Autowired
  private CsvReader csvReader;

  @Test
  public void csv_샘플데이터_읽었을때_헤더사이즈는9() throws IOException {
    ClassPathResource resource = new ClassPathResource("data/sampleData.csv");
    InputStream inputStream = resource.getInputStream();

    Map<Institution, List<Guarantee>> dataList = csvReader.read(inputStream);
    Set<Institution> institutionSet = dataList.keySet();

    assertThat(institutionSet.size()).isEqualTo(9);
  }

  @Test
  public void csv_샘플데이터_읽었을때_헤더리스트내_주택도시기금이존재() throws IOException {
    ClassPathResource resource = new ClassPathResource("data/sampleData.csv");
    InputStream inputStream = resource.getInputStream();

    Map<Institution, List<Guarantee>> dataList = csvReader.read(inputStream);
    Set<Institution> institutionSet = dataList.keySet();

    assertThat(institutionSet.contains(new Institution("주택도시기금"))).isTrue();
  }
}