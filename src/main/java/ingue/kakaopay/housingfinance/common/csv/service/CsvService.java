package ingue.kakaopay.housingfinance.common.csv.service;

import ingue.kakaopay.housingfinance.common.csv.util.reader.CsvReader;
import ingue.kakaopay.housingfinance.common.csv.vo.CsvVO;
import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.guarantee.service.GuaranteeService;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import ingue.kakaopay.housingfinance.institution.service.InstitutionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Csv 파일 비즈니스 로직을 담당하는 클래스
 */
@Service
@RequiredArgsConstructor
public class CsvService {

  private final InstitutionService institutionService;
  private final GuaranteeService guaranteeService;
  private final CsvReader csvReader;

  /**
   * 업로드한 파일을 받아서
   * Guarantee, Institution 으로 변환해주는 메소드를 호출한뒤
   * 변환된 Guarantee, Institution 객체들을 DB에 삽입해주는 메소드
   *
   * @param multipartFile 업로드한 파일
   * @return DB에 저장된 Guarantee, Institution 객체들
   */
  public CsvVO readCsvFile(MultipartFile multipartFile) {
    CsvVO csvVO = csvReader.read(multipartFile);

    return saveCsvData(csvVO);
  }

  private CsvVO saveCsvData(CsvVO csvVO) {
    List<Guarantee> guaranteeList = csvVO.getGuaranteeList();
    List<Institution> institutionList = csvVO.getInstitutionList();

    institutionList = this.institutionService.saveAll(institutionList);
    guaranteeList = this.guaranteeService.saveAll(guaranteeList);

    return new CsvVO(guaranteeList, institutionList);
  }
}
