package stm.ticket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import stm.ticket.dto.TicketDto;
import stm.ticket.service.TicketService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static stm.util.Constants.DATE_TIME_PATTERN;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getAllTickets(@RequestParam(required = false)
                                             @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeStart,
                                         @RequestParam(required = false)
                                         @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeEnd,
                                         @RequestParam(required = false) String departurePoint,
                                         @RequestParam(required = false) String destinationPoint,
                                         @RequestParam(required = false) String carrier,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                         @Positive @RequestParam(defaultValue = "10") Integer size){
        ticketService.getAllTickets(rangeStart, rangeEnd, departurePoint, destinationPoint, carrier, from, size);
    }

//    @GetMapping
}
