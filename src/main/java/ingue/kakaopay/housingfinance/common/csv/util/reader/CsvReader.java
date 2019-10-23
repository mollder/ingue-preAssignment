package ingue.kakaopay.housingfinance.common.csv.util.reader;

import ingue.kakaopay.housingfinance.common.csv.pojo.vo.CsvVO;
import org.springframework.web.multipart.MultipartFile;

public interface CsvReader {

  CsvVO read(MultipartFile multiPartFile);
}
