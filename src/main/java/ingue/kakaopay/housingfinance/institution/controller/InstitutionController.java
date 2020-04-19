package ingue.kakaopay.housingfinance.institution.controller;

import ingue.kakaopay.housingfinance.institution.domain.Institution;
import ingue.kakaopay.housingfinance.institution.response.FindAllInstitutionsResponse;
import ingue.kakaopay.housingfinance.institution.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class InstitutionController {

  private final InstitutionService institutionService;

  @GetMapping(value = "/institutions")
  public FindAllInstitutionsResponse findAllInstitutions() {
    List<Institution> institutionList = this.institutionService.findAll();

    return FindAllInstitutionsResponse.create(institutionList);
  }
}