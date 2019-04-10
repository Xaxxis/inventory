package id.xaxxis.inventory.dao.purchasing;

import id.xaxxis.inventory.entity.purchasing.PurchaseRequestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRequestItemDao extends JpaRepository<PurchaseRequestItem, String> {

}
