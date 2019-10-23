package ingue.kakaopay.housingfinance.common.csv.controller;

import ingue.kakaopay.housingfinance.common.csv.pojo.response.UploadCsvDataResponse;
import ingue.kakaopay.housingfinance.common.csv.pojo.vo.CsvVO;
import ingue.kakaopay.housingfinance.common.csv.service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Csv 파일 api 관련 컨트롤러 클래스
 */
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class CsvController {

  private final CsvService csvService;

  /**
   * csv 파일 업로드 api
   *
   * @param multipartFile 업로드할 csv 파일
   * @return DB에 저장된 파일 내용들
   */
  @PostMapping(value = "/file")
  public ResponseEntity uploadCsvData(@RequestParam("file") MultipartFile multipartFile) {
    CsvVO csvVO = csvService.readCsvFile(multipartFile);

    UploadCsvDataResponse uploadCsvDataResponse = UploadCsvDataResponse
        .create(csvVO.getGuaranteeList(), csvVO.getInstitutionList());

    return ResponseEntity.ok(uploadCsvDataResponse);
  }
}
