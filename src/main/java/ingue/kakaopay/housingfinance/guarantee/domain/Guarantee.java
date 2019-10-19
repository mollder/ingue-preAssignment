package ingue.kakaopay.housingfinance.guarantee.domain;

import ingue.kakaopay.housingfinance.institution.domain.Institution;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@SequenceGenerator(
    name = "Guarantee_SEQ_GENERATOR",
    sequenceName = "Guarantee_SEQ"
)
@Getter
@NoArgsConstructor
@ToString(exclude = "institution")
public class Guarantee {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "Guarantee_SEQ_GENERATOR")
  @Column(name = "guarantee_id")
  private Long id;

  private int year;
  private int month;
  private int money;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Institution_code")
  private Institution institution;

  @Builder
  private Guarantee(int year, int month, int money) {
    this.year = year;
    this.month = month;
    this.money = money;
  }

  public void setInstitution(Institution institution) {
    this.institution = institution;
  }
}