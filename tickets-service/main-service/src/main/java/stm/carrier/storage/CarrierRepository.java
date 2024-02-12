package stm.carrier.storage;

import stm.carrier.dto.CarrierDto;
import stm.carrier.model.Carrier;

import java.util.List;
import java.util.Optional;

public interface CarrierRepository {
    Carrier save(Carrier model);
    Carrier getById(int Id);

    void deleteById(int id);
    List<Carrier> getAll();

    Carrier update(Carrier carrier);

}
