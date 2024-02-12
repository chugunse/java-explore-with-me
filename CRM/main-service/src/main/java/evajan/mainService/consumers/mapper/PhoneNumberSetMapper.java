package evajan.mainService.consumers.mapper;

import evajan.mainService.consumers.dto.PhoneNumberDto;
import evajan.mainService.consumers.model.PhoneNumber;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = PhoneNumberMapper.class)
public interface PhoneNumberSetMapper {
    Set<PhoneNumber> toModelSet(Set<PhoneNumberDto> dtos);

    Set<PhoneNumberDto> toDtoSet(Set<PhoneNumber> models);
}
