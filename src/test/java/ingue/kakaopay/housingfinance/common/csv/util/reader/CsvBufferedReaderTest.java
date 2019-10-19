package ingue.kakaopay.housingfinance.common.csv.util.reader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import ingue.kakaopay.housingfinance.common.csv.util.parser.CsvParser;
import ingue.kakaopay.housingfinance.common.csv.vo.CsvVO;
import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@RunWith(MockitoJUnitRunner.class)
public class CsvBufferedReaderTest {

  @InjectMocks
  private CsvBufferedReader csvReader;

  @Mock
  private CsvParser csvParser;

  private MultipartFile multipartFile;

  @Before
  public void setUp() throws IOException {
    when(csvParser.parseHeaderData("주택도시기금1)")).thenReturn("주택도시기금");
    when(csvParser.parseHeaderData(eq("인규은행"))).thenReturn("인규은행");
    when(csvParser.parseBodyData(eq("2003"))).thenReturn(2003);
    when(csvParser.parseBodyData(eq("2004"))).thenReturn(2004);

    MockitoAnnotations.initMocks(this);

    ClassPathResource resource = new ClassPathResource("data/testData.csv");
    InputStream inputStream = resource.getInputStream();
    multipartFile = new MockMultipartFile("file", resource.getFilename(), null, inputStream);
  }

  @Test
  public void csv_테스트데이터_읽었을때_헤더사이즈는2() throws IOException {
    CsvVO csvVO = csvReader.read(multipartFile);
    List<Institution> institutionSet = csvVO.getInstitutionList();

    assertThat(institutionSet.size()).isEqualTo(2);
  }

  @Test
  public void csv_테스트데이터_읽었을때_헤더리스트내_주택도시기금이존재() throws IOException {
    CsvVO csvVO = csvReader.read(multipartFile);
    List<Institution> institutionList = csvVO.getInstitutionList();

    assertThat(institutionList.contains(new Institution("주택도시기금"))).isTrue();
  }

  @Test
  public void csv_테스트데이터_읽었을때_모든기관데이터_금액은2003() throws IOException {
    CsvVO csvVO = csvReader.read(multipartFile);
    List<Guarantee> guaranteeList = csvVO.getGuaranteeList();

    for (Guarantee guarantee : guaranteeList) {
      assertThat(guarantee.getMoney()).isEqualTo(2003);
    }
  }

  @Test
  public void 기관_두개_테스트데이터를_헤더처리메소드로_읽었을때_헤더_사이즈는2_주택도시기금과인규은행_존재()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Reader reader = new StringReader("연도,월,주택도시기금1),인규은행");
    BufferedReader br = new BufferedReader(reader);

    Method method = csvReader.getClass().getDeclaredMethod("readHeader", BufferedReader.class);
    method.setAccessible(true);

    Map<Integer, Institution> headerMap = (Map<Integer, Institution>) method.invoke(csvReader, br);

    assertThat(headerMap.size()).isEqualTo(2);
    assertThat(headerMap.containsValue(new Institution("주택도시기금"))).isTrue();
    assertThat(headerMap.containsValue(new Institution("인규은행"))).isTrue();
  }

  @Test
  public void 기관_두개_테스트데이터를_본문처리메소드로읽었을때_기관_금액은_0번째2003_1번째2004()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Reader reader = new StringReader("2003,3,2003,2004");
    BufferedReader br = new BufferedReader(reader);

    Map<Integer, Institution> headerMap = new HashMap<>();
    headerMap.put(2, new Institution("주택도시기금"));
    headerMap.put(3, new Institution("인규은행"));

    Method method = csvReader.getClass()
        .getDeclaredMethod("readBody", BufferedReader.class, Map.class);
    method.setAccessible(true);

    List<Guarantee> guaranteeList = (List<Guarantee>) method.invoke(csvReader, br, headerMap);

    assertThat(guaranteeList.get(0).getMoney()).isEqualTo(2003);
    assertThat(guaranteeList.get(1).getMoney()).isEqualTo(2004);
  }

  @Test(expected = RuntimeException.class)
  public void 파일확장자가_csv가_아닐때_런타임exception발생() throws IOException {
    ClassPathResource resource = new ClassPathResource("data/invalidFile.txt");
    InputStream inputStream = resource.getInputStream();
    multipartFile = new MockMultipartFile("file", resource.getFilename(), null, inputStream);

    csvReader.read(multipartFile);
  }

  @Test
  public void 파일확장자가_csv일때_true반환()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method method = csvReader.getClass()
        .getDeclaredMethod("isCsvFile", String.class);
    method.setAccessible(true);

    boolean result = (boolean) method.invoke(csvReader, multipartFile.getOriginalFilename());

    assertThat(result).isTrue();
  }
}