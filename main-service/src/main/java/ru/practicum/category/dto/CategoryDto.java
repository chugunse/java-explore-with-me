package ru.practicum.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder
@AllArgsConstructor
public class CategoryDto {
    Long id;
    @Size(max = 50)
    @NotBlank
    String name;
}