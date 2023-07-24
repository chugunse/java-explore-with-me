package ru.practicum.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import ru.practicum.util.validator.CastomDataTime;

import javax.validation.constraints.*;

@Value
@Getter
@Builder
public class NewEventDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private Long category;
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "неверный формат даты")
    @CastomDataTime(message = "время на которые намечено событие не может быть раньше," +
            " чем через два часа от текущего момента", delay = 2)
    private String eventDate;
    private LocationDto location;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
}