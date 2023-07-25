package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.users.dto.UserShortDto;
import util.Constants;

import java.time.LocalDateTime;

@Value
@Builder
public class EventShortDto {
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    LocalDateTime eventDate;
    Long id;
    UserShortDto initiator;
    boolean paid;
    String title;
    Long views;
}
