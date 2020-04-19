package ingue.kakaopay.housingfinance.common.csv.util.parser;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

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
   * <p>
   * ex) "3,234" --> 3234
   *
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
   * <p>
   * ex) 주택도시기금1)(억원) --> 주택도시기금
   *
   * @return 변환된 금융기관 이름
   */
  public String parseHeaderData(String input) {
    String result = input;

    for (String replace : this.replaceHeaderList) {
      result = result.replaceAll(replace, "");
    }

    return result;
  }

  /**
   * "5,323"과 같은 문자열을 5323과 같은 숫자로 바꾸기 위해
   * <p>
   * " , 과 같이 제거할 문자열 리스트를 반환해주는 메소드
   *
   * @return 제거할 문자들을 모아놓은 리스트
   */
  private List<String> initReplaceNumList() {
    List<String> replaceList = new ArrayList<>();
    replaceList.add("\"");
    replaceList.add(",");

    return replaceList;
  }

  /**
   * 주택도시기금1)(억원)과 같은 문자를 주택도시기금으로 변경하기 위해
   * <p>
   * 1) (억원) 과 같은 제거할 문자들의 리스트를 반환해주는 메소드
   *
   * @return 제거할 문자 리스트
   */
  private List<String> initReplaceHeaderList() {
    List<String> replaceHeaderList = new ArrayList<>();
    replaceHeaderList.add("\\(억원\\)");
    replaceHeaderList.add("1\\)");

    return replaceHeaderList;
  }
}