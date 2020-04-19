package ingue.kakaopay.housingfinance.common.csv.controller;

import ingue.kakaopay.housingfinance.common.csv.pojo.response.UploadCsvDataResponse;
import ingue.kakaopay.housingfinance.common.csv.pojo.vo.CsvVO;
import ingue.kakaopay.housingfinance.common.csv.service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class CsvController {

  private final CsvService csvService;

  @PostMapping(value = "/file")
  public UploadCsvDataResponse uploadCsvData(@RequestParam("file") MultipartFile multipartFile) {
    CsvVO csvVO = csvService.readCsvFile(multipartFile);

    return UploadCsvDataResponse.create(csvVO.getGuaranteeList(), csvVO.getInstitutionList());
  }
}
