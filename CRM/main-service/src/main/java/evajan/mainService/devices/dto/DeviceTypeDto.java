package evajan.mainService.devices.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
@Setter
@Getter
@AllArgsConstructor
public class DeviceTypeDto {
    Long id;
    @Size(max = 50)
    @NotBlank
    String deviceType;
}
