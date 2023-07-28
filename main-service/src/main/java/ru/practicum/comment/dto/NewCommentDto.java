package ru.practicum.comment.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class NewCommentDto {
    Long id;
    @NotBlank
    @Size(min = 1, max = 3000)
    String text;
}
