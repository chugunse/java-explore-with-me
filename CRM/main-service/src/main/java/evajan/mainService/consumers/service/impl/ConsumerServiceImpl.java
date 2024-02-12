package evajan.mainService.consumers.service.impl;

import evajan.mainService.consumers.dto.FullConsumerDto;
import evajan.mainService.consumers.dto.NewConsumerDto;
import evajan.mainService.consumers.mapper.ConsumerMapper;
import evajan.mainService.consumers.model.Consumer;
import evajan.mainService.consumers.service.ConsumerService;
import evajan.mainService.consumers.storage.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {
    private final ConsumerRepository repository;
    private final ConsumerMapper consumerMapper;

    @Override
    public FullConsumerDto addConsumer(NewConsumerDto newConsumerDto) {
        Consumer consumer = repository.save(consumerMapper.newConsumerToConsumer(newConsumerDto));
        log.info("клиент сохраненный в базу {}", consumer);
        return consumerMapper.toFullConsumerDto(consumer);
    }

    @Override
    public FullConsumerDto updateConsumer(NewConsumerDto newConsumerDto) {
        Consumer consumer = repository.save(consumerMapper.newConsumerToConsumer(newConsumerDto));
        log.info("клиент сохраненный/обновлен в базе {}", consumer);
        return consumerMapper.toFullConsumerDto(consumer);
    }
}
