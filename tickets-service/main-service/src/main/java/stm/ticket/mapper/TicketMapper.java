package stm.ticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import stm.route.mapper.RouteMapper;
import stm.ticket.dto.TicketDto;
import stm.ticket.dto.TicketNewDto;
import stm.ticket.model.Ticket;

@Mapper(componentModel = "spring", uses = RouteMapper.class)
public interface TicketMapper {
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "dateTime", dateFormat = "yyyy-MM-dd HH:mm")
    Ticket toModel(TicketNewDto dto);
    TicketDto toTicketDto(Ticket model);
}
