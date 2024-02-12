package evajan.mainService.devices.dto;

import lombok.*;

@ToString
@Setter
@Getter
public class DeviceDto {
    private Long id;
    private DeviceTypeDto deviceTypeDto;
    private String brand;
    private String model;
    private String alternate;
}
