package id.xaxxis.inventory.dao.master.suplier;

import id.xaxxis.inventory.entity.master.suplier.SuplierType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuplierTypeDao extends JpaRepository<SuplierType, String> {
    @Query(value = "SELECT * FROM suplier_type AS st ORDER BY st.id", nativeQuery =  true)
    List<SuplierType> findAll();

    Optional<SuplierType> findById(String id);

    SuplierType findByType(String type);
}
