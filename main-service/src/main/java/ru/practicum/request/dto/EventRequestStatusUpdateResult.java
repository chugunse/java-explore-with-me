package ru.practicum.request.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests; //подтвержденные запросы
    private List<ParticipationRequestDto> rejectedRequests; // отклоненные запросы
}
