package ingue.kakaopay.housingfinance.common.csv.util.reader;

import ingue.kakaopay.housingfinance.guarantee.domain.Guarantee;
import ingue.kakaopay.housingfinance.institution.domain.Institution;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface CsvReader {

  Map<Institution, List<Guarantee>> read(InputStream stream) throws IOException;
}
