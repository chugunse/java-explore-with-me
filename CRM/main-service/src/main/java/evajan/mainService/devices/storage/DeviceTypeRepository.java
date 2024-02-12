package evajan.mainService.devices.storage;

import evajan.mainService.devices.model.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> {
}
