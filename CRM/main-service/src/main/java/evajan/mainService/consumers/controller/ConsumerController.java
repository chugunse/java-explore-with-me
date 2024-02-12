package evajan.mainService.consumers.controller;

import evajan.mainService.consumers.dto.FullConsumerDto;
import evajan.mainService.consumers.dto.NewConsumerDto;
import evajan.mainService.consumers.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/consumers")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ConsumerController {
    private final ConsumerService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FullConsumerDto addConsumer(@RequestBody @Validated NewConsumerDto newConsumerDto) {
        log.info("добавили клиента {}", newConsumerDto);
        return service.addConsumer(newConsumerDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public FullConsumerDto putConsumer(@RequestBody @Validated NewConsumerDto newConsumerDto) {
        log.info("обновили клиента {}", newConsumerDto);
        return service.updateConsumer(newConsumerDto);
    }
}
