package ru.practicum.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.comment.model.ChangedBy;
import util.Constants;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDto {
    private Long id;
    private String owner;
    private String text;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime created;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime updated;
    private ChangedBy changedBy;
}
