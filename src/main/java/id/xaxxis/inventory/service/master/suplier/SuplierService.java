package id.xaxxis.inventory.service.master.suplier;

import id.xaxxis.inventory.entity.master.suplier.Suplier;
import id.xaxxis.inventory.entity.master.suplier.SuplierType;

import java.util.List;
import java.util.Optional;

public interface SuplierService {
    List<Suplier> findAll();
    List<Suplier> findAllByMasterLocation(String locationId);

    Suplier findBySuplierId(String suplierId);
    Suplier findBySuplierPhone(String suplierPhone);

    Suplier createSuplier(Suplier suplier);
    Suplier saveSuplier(Suplier suplier);

    List<SuplierType> findAllSuplierType();
    Optional<SuplierType> findSuplierTypeById(String id);
    SuplierType findSuplierTypeByType(String type);

}
