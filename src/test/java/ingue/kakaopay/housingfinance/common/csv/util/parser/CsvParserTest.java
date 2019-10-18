package ingue.kakaopay.housingfinance.common.csv.util.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsvParser.class)
public class CsvParserTest {

  @Autowired
  private CsvParser csvParser;

  @Test
  public void 문자열과_쉼표가_있는_숫자를넣었을때_문자열과_쉼표제거() {
    String testNum = "\"5,322\"";

    int result = csvParser.parseBodyData(testNum);

    assertThat(result).isEqualTo(5322);
  }

  @Test
  public void 일반_숫자를_넣었을때_숫자_그대로_반환() {
    String testNum = "5322";

    int result = csvParser.parseBodyData(testNum);

    assertThat(result).isEqualTo(5322);
  }

  @Test
  public void 괄호_돈단위포함된_금융기관이름넣었을때_괄호_돈단위제거() {
    String testHeaderData = "주택도시기금1)(억원)";

    String result = csvParser.parseHeaderData(testHeaderData);

    assertThat(result).isEqualTo("주택도시기금");
  }

  @Test
  public void 올바른_금융기관_이름넣었을때_그대로_반환() {
    String testHeaderData = "주택도시기금";

    String result = csvParser.parseHeaderData(testHeaderData);

    assertThat(result).isEqualTo("주택도시기금");
  }

  @Test
  public void 지정된문자열_이외의_다른_문자열은_걸러내지못하는_경우() {
    String testHeaderData = "주택도시기금2)(천원)";

    String result = csvParser.parseHeaderData(testHeaderData);

    assertThat(result).isNotEqualTo("주택도시기금");
  }
}
