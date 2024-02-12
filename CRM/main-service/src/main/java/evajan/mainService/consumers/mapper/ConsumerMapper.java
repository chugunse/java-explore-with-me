package evajan.mainService.consumers.mapper;

import evajan.mainService.consumers.dto.FullConsumerDto;
import evajan.mainService.consumers.dto.NewConsumerDto;
import evajan.mainService.consumers.model.Consumer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PhoneNumberSetMapper.class)
public interface ConsumerMapper {
    Consumer toConsumer(FullConsumerDto fullConsumerDto);

    @Mapping(source = "phoneNumberDtos", target = "phoneNumbers")
    Consumer newConsumerToConsumer(NewConsumerDto newConsumerDto);

    @Mapping(source = "phoneNumbers", target = "phoneNumberDtos")
    FullConsumerDto toFullConsumerDto(Consumer consumer);
}
