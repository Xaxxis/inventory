package id.xaxxis.inventory.service.purchasing.order;

import id.xaxxis.inventory.dao.purchasing.order.PurchaseOrderDao;
import id.xaxxis.inventory.dao.purchasing.order.PurchaseOrderDetailDao;
import id.xaxxis.inventory.entity.purchasing.order.PurchaseOrder;
import id.xaxxis.inventory.entity.purchasing.order.PurchaseOrderDetail;
import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequest;
import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequestItem;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderDao purchaseOrderDao;
    private final PurchaseOrderDetailDao purchaseOrderDetailDao;

    public PurchaseOrderServiceImpl(PurchaseOrderDao purchaseOrderDao, PurchaseOrderDetailDao purchaseOrderDetailDao){
        this.purchaseOrderDao = purchaseOrderDao;
        this.purchaseOrderDetailDao = purchaseOrderDetailDao;
    }

    @Override
    public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder) {
        return purchaseOrderDao.save(purchaseOrder);
    }

    @Override
    public PurchaseOrderDetail savePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail) {
        return purchaseOrderDetailDao.save(purchaseOrderDetail);
    }

    @Override
    public void createPO(PurchaseRequest purchaseRequest, PurchaseRequestItem purchaseRequestItem){
        PurchaseOrder newPo =  new PurchaseOrder();
        newPo.setPoNumber(generatePONumber());


    }

    @Override
    public String generatePONumber() {
        String previx = "ss";
        return previx;
    }
}
