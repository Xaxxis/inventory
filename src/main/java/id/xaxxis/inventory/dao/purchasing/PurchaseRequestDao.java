package id.xaxxis.inventory.dao.purchasing;

import id.xaxxis.inventory.entity.purchasing.PurchaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRequestDao extends JpaRepository<PurchaseRequest, String> {

    @Override
    long count();
}
