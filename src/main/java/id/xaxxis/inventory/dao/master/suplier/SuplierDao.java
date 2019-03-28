package id.xaxxis.inventory.dao.master.suplier;

import id.xaxxis.inventory.entity.master.suplier.Suplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SuplierDao extends JpaRepository<Suplier, String> {
    @Query(value = "SELECT * FROM master_suplier AS ms ORDER BY ms.suplier_id",nativeQuery = true)
    List<Suplier> findAll();
    List<Suplier> findAllByMasterLocation_LocationId(String locationId);

    Suplier findBySuplierId(String suplierId);
    Suplier findBySuplierPhone(String suplierPhone);
}
