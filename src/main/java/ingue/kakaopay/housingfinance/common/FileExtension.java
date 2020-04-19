package ingue.kakaopay.housingfinance.common;

import lombok.Getter;

@Getter
public enum FileExtension {
  CSV("csv");

  private String extension;

  FileExtension(String extension) {
    this.extension = extension;
  }
}