package evajan.mainService.devices.mapper;

import evajan.mainService.devices.dto.DeviceTypeDto;
import evajan.mainService.devices.dto.NewDeviceTypeDto;
import evajan.mainService.devices.model.DeviceType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeviceTypeMapper {
    DeviceType toModel(NewDeviceTypeDto newDeviceTypeDto);

    DeviceType toModel(DeviceTypeDto deviceTypeDto);

    DeviceTypeDto toDto(DeviceType deviceType);

    List<DeviceTypeDto> toDtoList(List<DeviceType> list);
}
