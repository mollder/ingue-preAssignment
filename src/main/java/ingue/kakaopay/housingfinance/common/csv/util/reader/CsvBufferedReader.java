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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * BufferedReader를 이용해서 csv파일을 읽는 역할을 수행하는 클래스
 */
@Component
@RequiredArgsConstructor
public class CsvBufferedReader implements CsvReader {

  private final CsvParser csvParser;

  /**
   * 파일을 내용을 읽은 뒤 내용을 List<Guarantee> , List<Institution> 객체로 변환후 돌려주는 메소드
   *
   * @param multipartFile 업로드 파일
   * @return List<Guarantee> , List<Institution>를 가지고 있는 CsvVO 객체
   */
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

  /**
   * 파일을 받아서 올바른 확장자라면 BufferedReader 객체를 생성해서 돌려주고 올바르지 않은 확장자라면 런타임 exception을 날리는 메소드
   *
   * @param multipartFile 업로드된 파일
   * @return 파라미터 파일로 생성한 BufferedReader 객체
   */
  private BufferedReader initReader(MultipartFile multipartFile) throws IOException {
    String fileName = multipartFile.getOriginalFilename();
    boolean isCsvFile = isCsvFile(Objects.requireNonNull(fileName));

    if (!isCsvFile) {
      throw new RuntimeException("invalid file extension : not csv file");
    }

    InputStream inputStream = multipartFile.getInputStream();

    return new BufferedReader(new InputStreamReader(inputStream));
  }

  /**
   * 파일이름을 받아서 csv 파일 여부를 확인하는 메소드
   *
   * @param fileName 파일이름
   * @return csv 파일인지 여부
   */
  private boolean isCsvFile(String fileName) {
    return fileName.endsWith(FileExtension.CSV.getExtension());
  }

  /**
   * 파일 첫줄을 읽고 map에 기관 정보와 기관 위치 index를 저장한 뒤 map을 돌려주는 메소드
   *
   * @param br 업로드된 파일로 생성한 BufferedReader 객체
   * @return 파일 index와 같이 기관정보를 저장해놓은 map
   */
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

  /**
   * 파일에서 기관 내용 위치와 기관 객체를 담은 map을 기관정보 리스트로 변환한 뒤 돌려주는 메소드
   *
   * @param headerMap 파일에서 기관 위치, 객체가 저장된 map
   * @return 기관정보 리스트
   */
  private List<Institution> getInstitutionList(Map<Integer, Institution> headerMap) {
    List<Institution> institutionList = new ArrayList<>();

    Set<Integer> keySet = headerMap.keySet();

    for (int key : keySet) {
      institutionList.add(headerMap.get(key));
    }

    return institutionList;
  }

  /**
   * 파일 본문 내용을 읽고 본문 내용을 Guarantee 객체 리스트로 변환한 뒤 돌려주는 메소드
   *
   * @param br        업로드 파일로 생성한 BufferedReader
   * @param headerMap 파일 내 기관 위치, 객체 등을 저장한 map
   * @return Guarantee 객체 리스트
   */
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

  /**
   * 파일 내용중 한줄에 대한 문자열을 받아서 문자열 내용을 Guarantee 객체로 변환한 뒤 파라미터로 받은 리스트에 삽입해주는 메소드
   *
   * @param line          파일 내용중 일부 한줄
   * @param guaranteeList guarantee 객체 리스트
   * @param headerMap     파일 내 기관 위치 기관 객체정보가 담긴 map
   */
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