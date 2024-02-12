package stm.exception.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import stm.util.Constants;

@Getter
@AllArgsConstructor  //возможно убрать
@Builder
public class ApiError {
    private String message;
    private String reason;
    private String status;
    @DateTimeFormat(pattern = Constants.DATE_TIME_PATTERN)
    private String timestamp;
}
