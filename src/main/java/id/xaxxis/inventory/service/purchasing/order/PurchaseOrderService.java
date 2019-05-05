package id.xaxxis.inventory.service.purchasing.order;

import id.xaxxis.inventory.entity.purchasing.order.PurchaseOrder;
import id.xaxxis.inventory.entity.purchasing.order.PurchaseOrderDetail;
import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequest;
import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequestItem;

public interface PurchaseOrderService {

    PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder);
    PurchaseOrderDetail savePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail);


    void createPO(PurchaseRequest purchaseRequest, PurchaseRequestItem purchaseRequestItem);

    String generatePONumber();


}
