package stm.trip.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import stm.carrier.mapper.CarrierMapper;
import stm.route.mapper.RouteMapper;
import stm.trip.dto.TripFullDto;
import stm.trip.dto.TripNewDto;
import stm.trip.dto.TripPublicDto;
import stm.trip.model.Trip;

import java.util.List;

@Mapper(componentModel = "spring", uses = RouteMapper.class)
public interface TripMapper {
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "dateTime", dateFormat = "yyyy-MM-dd HH:mm")
    Trip toModel(TripNewDto dto);

    @Mapping(target = "dateTime", dateFormat = "yyyy-MM-dd HH:mm")
    TripFullDto toFullDto(Trip model);
    List<TripFullDto> toFullDtoList(List<Trip> list);
    TripPublicDto toPublicDto(Trip trip);
}
