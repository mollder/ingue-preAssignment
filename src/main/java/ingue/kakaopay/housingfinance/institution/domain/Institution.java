package ingue.kakaopay.housingfinance.institution.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@SequenceGenerator(
    name = "INSTITUTION_SEQ_GENERATOR",
    sequenceName = "INSTITUTION_SEQ"
)
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"})
@ToString
public class Institution {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "INSTITUTION_SEQ_GENERATOR")
  @Column(name = "Institution_code")
  private Long code;

  @Column(unique = true)
  private String name;

  public Institution(String name) {
    this.name = name;
  }
}