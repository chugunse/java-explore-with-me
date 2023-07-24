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
public class EventFullDto {
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    LocalDateTime createdOn; // Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    String description; //Полное описание события
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    LocalDateTime eventDate; //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    Long id;
    UserShortDto initiator; //Пользователь (краткая информация)
    LocationDto location; //Широта и долгота места проведения события
    boolean paid; // Нужно ли оплачивать участие
    int participantLimit; // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    LocalDateTime publishedOn; //Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    boolean requestModeration; // Нужна ли пре-модерация заявок на участие
    String state; // example: PUBLISHED, Список состояний жизненного цикла события
    String title; // example: Знаменитое шоу 'Летающая кукуруза' Заголовок
    Long views; // Количество просмотров события

}
