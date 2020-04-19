package ingue.kakaopay.housingfinance.common.csv.util.reader;

import com.opencsv.CSVReader;
import ingue.kakaopay.housingfinance.common.FileExtension;
import ingue.kakaopay.housingfinance.common.csv.pojo.vo.CsvVO;
import ingue.kakaopay.housingfinance.common.csv.util.parser.CsvParser;
import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CsvBufferedReader implements CsvReader {

  private final CsvParser csvParser;

  public CsvVO read(MultipartFile multipartFile) {
    List<Guarantee> guaranteeList = null;
    List<Institution> institutionList = null;

    try (BufferedReader br = initReader(multipartFile)) {
      Map<Integer, Institution> headerMap = readHeader(br);

      institutionList = getInstitutionList(headerMap);
      guaranteeList = readBody(br, headerMap);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return new CsvVO(guaranteeList, institutionList);
  }

  private BufferedReader initReader(MultipartFile multipartFile) throws IOException {
    String fileName = multipartFile.getOriginalFilename();
    boolean isCsvFile = isCsvFile(Objects.requireNonNull(fileName));

    if (!isCsvFile) {
      throw new RuntimeException("invalid file extension : not csv file");
    }

    InputStream inputStream = multipartFile.getInputStream();

    return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
  }

  private boolean isCsvFile(String fileName) {
    return fileName.endsWith(FileExtension.CSV.getExtension());
  }

  private Map<Integer, Institution> readHeader(BufferedReader br) throws IOException {
    Map<Integer, Institution> headerMap = new HashMap<>();

    CSVReader csvReader = new CSVReader(br);
    String[] split = csvReader.readNext();

    for (int i = 2; i < split.length && !split[i].equals(""); i++) {
      String institutionName = csvParser.parseHeaderData(split[i]);

      Institution institution = new Institution(institutionName);
      headerMap.put(i, institution);
    }

    return headerMap;
  }

  private List<Institution> getInstitutionList(Map<Integer, Institution> headerMap) {
    List<Institution> institutionList = new ArrayList<>();

    Set<Integer> keySet = headerMap.keySet();

    for (int key : keySet) {
      institutionList.add(headerMap.get(key));
    }

    return institutionList;
  }

  private List<Guarantee> readBody(BufferedReader br,
      Map<Integer, Institution> headerMap) throws IOException {
    List<Guarantee> guaranteeList = new ArrayList<>();

    CSVReader csvReader = new CSVReader(br);
    String[] split = csvReader.readNext();

    while (split != null) {
      handleLine(split, guaranteeList, headerMap);
      split = csvReader.readNext();
    }

    return guaranteeList;
  }

  private void handleLine(String[] split, List<Guarantee> guaranteeList,
      Map<Integer, Institution> headerMap) {
    int year = Integer.parseInt(split[0]);
    int month = Integer.parseInt(split[1]);

    for (int i = 2; i < split.length && !split[i].equals(""); i++) {
      if(split[i].equals("")) continue;

      int money = csvParser.parseBodyData(split[i]);

      Guarantee guarantee = Guarantee.builder()
          .year(year)
          .month(month)
          .money(money)
          .build();

      Institution institution = headerMap.get(i);

      if (institution != null) {
        guarantee.setInstitution(institution);

        guaranteeList.add(guarantee);
      }
    }
  }
}