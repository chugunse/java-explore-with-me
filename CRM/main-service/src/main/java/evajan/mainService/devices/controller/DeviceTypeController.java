package evajan.mainService.devices.controller;

import evajan.mainService.devices.dto.DeviceTypeDto;
import evajan.mainService.devices.dto.NewDeviceTypeDto;
import evajan.mainService.devices.service.DeviceTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device-type")
@RequiredArgsConstructor
@Slf4j
public class DeviceTypeController {
    private final DeviceTypeService deviceTypeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceTypeDto addDeviceType(@RequestBody NewDeviceTypeDto dto){
        log.info("добавили новый тип девайса {}", dto);
        return deviceTypeService.addNewDeviceType(dto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceTypeDto patchDeviceType(@RequestBody NewDeviceTypeDto dto,
                                         @PathVariable long id){
        log.info("обновили тип девайса {}", dto);
        return deviceTypeService.patchDeviceType(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeviceType(@PathVariable long id){
        log.info("удаляем тип девайса с id={}", id);
        deviceTypeService.deleteDeviceType(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DeviceTypeDto> getAllDeviceType(){
        log.info("запросили все типы девайсов");
        return deviceTypeService.getAllDeviceType();
    }
}
