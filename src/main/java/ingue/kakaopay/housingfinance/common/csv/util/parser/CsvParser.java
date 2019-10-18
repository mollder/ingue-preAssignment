package ingue.kakaopay.housingfinance.common.csv.util.parser;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Csv 파일 내 문자열을 파싱해주는 클래스
 *
 * Header 형식, 본문 숫자 형식을 변환시켜주는 역할을 맡고 있다.
 */
@Component
public class CsvParser {

  private List<String> replaceHeaderList;
  private List<String> replaceList;

  @PostConstruct
  private void init() {
    replaceList = initReplaceNumList();
    replaceHeaderList = initReplaceHeaderList();
  }

  /**
   * csv내 돈 데이터들 형식을 숫자로 바꿔주는 메소드
   *
   * ex) "3,234" --> 3234
   * @param input
   * @return 변환된 금액
   */
  public int parseBodyData(String input) {
    String result = input;

    for (String replace : this.replaceList) {
      result = result.replaceAll(replace, "");
    }

    return Integer.parseInt(result);
  }

  /**
   * csv 파일 헤더에 금융기관 이름들을 변환시켜주는 메소드
   *
   * ex) 주택도시기금1)(억원) --> 주택도시기금
   * @param input
   * @return 변환된 금융기관 이름
   */
  public String parseHeaderData(String input) {
    String result = input;

    for(String replace : this.replaceHeaderList) {
      result = result.replaceAll(replace, "");
    }

    return result;
  }

  private List<String> initReplaceNumList() {
    List<String> replaceList = new ArrayList<>();
    replaceList.add("\"");
    replaceList.add(",");

    return replaceList;
  }

  private List<String> initReplaceHeaderList() {
    List<String> replaceHeaderList = new ArrayList<>();
    replaceHeaderList.add("\\(억원\\)");
    replaceHeaderList.add("1\\)");

    return replaceHeaderList;
  }
}