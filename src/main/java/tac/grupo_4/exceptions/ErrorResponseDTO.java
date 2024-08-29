package tac.grupo_4.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {
  private String statusCode;
  private String title;
  private String detail;
}
