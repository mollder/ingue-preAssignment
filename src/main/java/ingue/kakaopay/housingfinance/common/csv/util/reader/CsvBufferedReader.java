package ingue.kakaopay.housingfinance.common.csv.util.reader;

import ingue.kakaopay.housingfinance.common.csv.util.parser.CsvParser;
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
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * BufferedReader를 이용해서 csv파일을 읽는 역할을 수행하는 클래스
 */
@Component
public class CsvBufferedReader implements CsvReader {

  private final CsvParser csvParser;

  public CsvBufferedReader(CsvParser csvParser) {
    this.csvParser = csvParser;
  }

  public Map<Institution, List<Guarantee>> read(InputStream stream) {
    Map<Institution, List<Guarantee>> dataMap = new HashMap<>();

    try (BufferedReader br = initReader(stream)) {
      Map<Integer, Institution> headerMap = readHeader(br);
      dataMap = readBody(br, headerMap);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return dataMap;
  }

  private BufferedReader initReader(InputStream inputStream) {
    return new BufferedReader(new InputStreamReader(inputStream));
  }

  private Map<Integer, Institution> readHeader(BufferedReader br) throws IOException {
    Map<Integer, Institution> headerMap = new HashMap<>();

    String line = br.readLine();
    if (line == null) {
      throw new RuntimeException("file header isn't exist");
    }

    String[] split = line.split(",");

    for (int i = 2; i < split.length; i++) {
      String institutionName = csvParser.parseHeaderData(split[i]);

      Institution institution = new Institution(institutionName);
      headerMap.put(i, institution);
    }

    return headerMap;
  }

  private Map<Institution, List<Guarantee>> readBody(BufferedReader br,
      Map<Integer, Institution> headerMap) throws IOException {
    Map<Institution, List<Guarantee>> dataMap = initDataMap(headerMap);

    String line = br.readLine();

    while (line != null) {
      handleLine(line, dataMap, headerMap);
      line = br.readLine();
    }

    return dataMap;
  }

  private Map<Institution, List<Guarantee>> initDataMap(Map<Integer, Institution> headerMap) {
    Map<Institution, List<Guarantee>> dataMap = new HashMap<>();

    Set<Integer> indexSet = headerMap.keySet();

    for (int index : indexSet) {
      dataMap.put(headerMap.get(index), new ArrayList<>());
    }

    return dataMap;
  }

  private void handleLine(String line, Map<Institution, List<Guarantee>> dataMap,
      Map<Integer, Institution> headerMap) {
    String[] split = line.split(",");

    if (split.length <= 2) {
      throw new RuntimeException("data body format is invalid");
    }

    int year = Integer.parseInt(split[0]);
    int month = Integer.parseInt(split[1]);

    for (int i = 2; i < split.length; i++) {
      int money = csvParser.parseBodyData(split[i]);

      Guarantee guarantee = Guarantee.builder()
          .year(year)
          .month(month)
          .money(money)
          .build();

      Institution institution = headerMap.get(i);

      if (institution != null) {
        guarantee.setInstitution(institution);

        List<Guarantee> guaranteeList = dataMap.get(institution);
        guaranteeList.add(guarantee);
      }
    }
  }
}