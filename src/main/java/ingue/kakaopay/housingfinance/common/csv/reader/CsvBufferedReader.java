package ingue.kakaopay.housingfinance.common.csv.reader;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * BufferedReader를 이용해서 csv파일을 읽는 역할을 수행하는 클래스
 */
@Component
public class CsvBufferedReader implements CsvReader {

  public Map<Institution, List<Guarantee>> read(InputStream stream) {
    Map<Institution, List<Guarantee>> dataMap = new HashMap<>();
    Map<Integer, Institution> headerMap = new HashMap<>();

    try (BufferedReader br = initReader(stream)) {
      handleHeader(br, dataMap, headerMap);
      handleData(br, dataMap, headerMap);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return dataMap;
  }

  private void handleData(BufferedReader br, Map<Institution, List<Guarantee>> dataMap,
      Map<Integer, Institution> headerMap) {

  }

  private void handleHeader(BufferedReader br, Map<Institution, List<Guarantee>> dataMap,
      Map<Integer, Institution> headerMap) throws IOException {
    String line = br.readLine();
    if (line == null) {
      throw new RuntimeException("file header isn't exist");
    }

    String[] split = line.split(",");

    for (int i = 2; i < split.length; i++) {
      String institutionName = extractString(split[i], i);

      Institution institution = new Institution(institutionName);
      headerMap.put(i, institution);
      dataMap.put(institution, new ArrayList<>());
    }
  }

  private String extractString(String value, int index) {
    int postfix = 4;

    if (index == 2) {
      postfix += 2;
    }

    return value.substring(0, value.length()-postfix);
  }

  private BufferedReader initReader(InputStream inputStream) {
    return new BufferedReader(new InputStreamReader(inputStream));
  }
}