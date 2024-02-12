package evajan.mainService.consumers.storage;

import evajan.mainService.consumers.model.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
}
