package ingue.kakaopay.housingfinance.common;

import lombok.Getter;

/**
 * 파일 확장자 enum
 */
@Getter
public enum FileExtension {
  CSV("csv");

  private String extension;

  FileExtension(String extension) {
    this.extension = extension;
  }
}