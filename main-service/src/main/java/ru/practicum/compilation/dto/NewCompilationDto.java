package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Value
@Builder
public class NewCompilationDto {
    List<Long> events;
    boolean pinned;
    @Size(min = 1, max = 50)
    @NotBlank
    String title;
}