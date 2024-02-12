package evajan.mainService.consumers.service;

import evajan.mainService.consumers.dto.FullConsumerDto;
import evajan.mainService.consumers.dto.NewConsumerDto;
import evajan.mainService.consumers.model.Consumer;

public interface ConsumerService {
    FullConsumerDto addConsumer(NewConsumerDto newConsumerDto);

    FullConsumerDto updateConsumer(NewConsumerDto newConsumerDto);
}
