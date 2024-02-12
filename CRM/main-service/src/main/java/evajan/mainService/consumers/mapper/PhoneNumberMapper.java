package evajan.mainService.consumers.mapper;

import evajan.mainService.consumers.dto.PhoneNumberDto;
import evajan.mainService.consumers.model.PhoneNumber;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneNumberMapper {
    PhoneNumber toPhoneNumber(PhoneNumberDto phoneNumberDto);
    PhoneNumberDto toPhoneNumberDto(PhoneNumber phoneNumber);
}
