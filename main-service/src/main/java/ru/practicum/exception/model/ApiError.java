package ru.practicum.exception.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import util.Constants;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private String message;
    private String reason;
    private String status;
    @DateTimeFormat(pattern = Constants.DATE_TIME_PATTERN)
    private String timestamp;
}
