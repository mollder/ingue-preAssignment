package ingue.kakaopay.housingfinance.institution.controller;

import ingue.kakaopay.housingfinance.institution.domain.Institution;
import ingue.kakaopay.housingfinance.institution.response.FindAllInstitutionsResponse;
import ingue.kakaopay.housingfinance.institution.service.InstitutionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Institution api 관련 컨트롤러
 */
@RestController(value = "/institutions")
@RequiredArgsConstructor
public class InstitutionController {

  private final InstitutionService institutionService;

  /**
   * 모든 금융 기관들을 돌려주는 api
   *
   * @return DB에 저장된 모든 금융 기관 목록
   */
  @GetMapping
  public ResponseEntity findAllInstitutions() {
    List<Institution> institutionList = this.institutionService.findAll();

    FindAllInstitutionsResponse findAllInstitutionsResponse = FindAllInstitutionsResponse
        .create(institutionList);

    return ResponseEntity.ok(findAllInstitutionsResponse);
  }
}