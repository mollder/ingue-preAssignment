package ingue.kakaopay.housingfinance.common.csv.util.reader;

import ingue.kakaopay.housingfinance.common.csv.vo.CsvVO;
import java.io.IOException;
import java.io.InputStream;

public interface CsvReader {

  CsvVO read(InputStream stream) throws IOException;
}
