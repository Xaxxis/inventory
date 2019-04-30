package id.xaxxis.inventory.dao.purchasing;

import id.xaxxis.inventory.entity.purchasing.pr.PurchaseRequestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PurchaseRequestItemDao extends JpaRepository<PurchaseRequestItem, String> {

    PurchaseRequestItem deletePurchaseRequestItemByRequestItemId(String itemId);

    void deletePurchaseRequestItemsByRequestItemId(String id);


}
