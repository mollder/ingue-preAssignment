package ingue.kakaopay.housingfinance.guarantee.domain;

import ingue.kakaopay.housingfinance.institution.domain.Institutions;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(
    name = "Guarantees_SEQ_GENERATOR",
    sequenceName = "Guarantees_SEQ"
)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Guarantees {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "Guarantees_SEQ_GENERATOR")
  @Column(name = "guarantee_id")
  private Long id;

  private int year;
  private int month;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Institution_code")
  private Institutions institution;

  public Guarantees(int year, int month) {
    this.year = year;
    this.month = month;
  }
}