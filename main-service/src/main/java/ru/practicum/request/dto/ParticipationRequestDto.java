package ru.practicum.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import util.Constants;

import java.time.LocalDateTime;

@Value
@Builder
public class ParticipationRequestDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    LocalDateTime created;
    Long event;
    Long id;
    Long requester;
    String status;

}
