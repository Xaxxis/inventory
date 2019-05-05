package id.xaxxis.inventory.dao.purchasing.order;

import id.xaxxis.inventory.entity.purchasing.order.PurchaseOrderDetail;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderDetailDao extends DataTablesRepository<PurchaseOrderDetail, String> {
}
