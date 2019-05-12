package id.xaxxis.inventory.service.purchasing.order;

import id.xaxxis.inventory.entity.purchasing.order.PurchaseOrder;
import id.xaxxis.inventory.entity.purchasing.order.PurchaseOrderDetail;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.Optional;

public interface PurchaseOrderService {

    PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder);
    PurchaseOrderDetail savePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail);

    DataTablesOutput<PurchaseOrder> findAll(DataTablesInput input);

    void createPO(String id);

    String generatePONumber(String prNumber);

    Optional<PurchaseOrder> findById(String id);

}
