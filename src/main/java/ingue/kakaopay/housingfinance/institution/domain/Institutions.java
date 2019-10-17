package ingue.kakaopay.housingfinance.institution.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(
    name = "INSTITUTIONS_SEQ_GENERATOR",
    sequenceName = "INSTITUTIONS_SEQ"
)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Institutions {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "INSTITUTIONS_SEQ_GENERATOR")
  @Column(name = "Institution_code")
  private Long code;

  private String name;

  public Institutions(String name) {
    this.name = name;
  }

}