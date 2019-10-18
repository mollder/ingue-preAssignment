package ingue.kakaopay.housingfinance.common.csv.util.reader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import ingue.kakaopay.housingfinance.common.csv.util.parser.CsvParser;
import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

@RunWith(MockitoJUnitRunner.class)
public class CsvBufferedReaderTest {

  @InjectMocks
  private CsvBufferedReader csvReader;

  @Mock
  private CsvParser csvParser;

  @Before
  public void setUp() {
    when(csvParser.parseHeaderData("주택도시기금1)")).thenReturn("주택도시기금");
    when(csvParser.parseHeaderData(eq("인규은행"))).thenReturn("인규은행");
    when(csvParser.parseBodyData(eq("2003"))).thenReturn(2003);

    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void csv_테스트헤더데이터_읽었을때_헤더사이즈는2() throws IOException {
    ClassPathResource resource = new ClassPathResource("data/testHeader.csv");
    InputStream inputStream = resource.getInputStream();

    Map<Institution, List<Guarantee>> dataList = csvReader.read(inputStream);
    Set<Institution> institutionSet = dataList.keySet();

    assertThat(institutionSet.size()).isEqualTo(2);
  }

  @Test
  public void csv_샘플데이터_읽었을때_헤더리스트내_주택도시기금이존재() throws IOException {
    ClassPathResource resource = new ClassPathResource("data/testHeader.csv");
    InputStream inputStream = resource.getInputStream();

    Map<Institution, List<Guarantee>> dataList = csvReader.read(inputStream);
    Set<Institution> institutionSet = dataList.keySet();

    assertThat(institutionSet.contains(new Institution("주택도시기금"))).isTrue();
  }
}