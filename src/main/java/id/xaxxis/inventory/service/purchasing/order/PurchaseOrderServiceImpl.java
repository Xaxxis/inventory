package id.xaxxis.inventory.service.purchasing.order;

import id.xaxxis.inventory.dao.purchasing.order.PurchaseOrderDao;
import id.xaxxis.inventory.dao.purchasing.order.PurchaseOrderDetailDao;
import id.xaxxis.inventory.dao.purchasing.request.PurchaseRequestDao;
import id.xaxxis.inventory.entity.purchasing.order.PurchaseOrder;
import id.xaxxis.inventory.entity.purchasing.order.PurchaseOrderDetail;
import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequest;
import id.xaxxis.inventory.enums.RequestStatus;
import id.xaxxis.inventory.service.purchasing.request.PurchaseRequestService;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderDao purchaseOrderDao;
    private final PurchaseOrderDetailDao purchaseOrderDetailDao;
    private final PurchaseRequestService purchaseRequestService;
    private final PurchaseRequestDao purchaseRequestDao;

    public PurchaseOrderServiceImpl(PurchaseOrderDao purchaseOrderDao, PurchaseOrderDetailDao purchaseOrderDetailDao,
                                    PurchaseRequestService purchaseRequestService, PurchaseRequestDao purchaseRequestDao){
        this.purchaseOrderDao = purchaseOrderDao;
        this.purchaseOrderDetailDao = purchaseOrderDetailDao;
        this.purchaseRequestService = purchaseRequestService;
        this.purchaseRequestDao = purchaseRequestDao;
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
    public DataTablesOutput<PurchaseOrder> findAll(DataTablesInput input) {
        return purchaseOrderDao.findAll(input);
    }

    @Override
    @Transactional
    public void createPO(String id){
        PurchaseRequest purchaseRequest = purchaseRequestService.findByPrId(id);
        Optional<PurchaseOrder> po = purchaseOrderDao.findPurchaseOrderByPrNumber(purchaseRequest.getPurchaseRequestNumber());
        if(purchaseRequest.getRequestStatus().getValue() <= 2 && !po.isPresent() ){
            purchaseRequest.setRequestStatus(RequestStatus.DIPROSES);
            purchaseRequestDao.save(purchaseRequest);
            PurchaseOrder newPo =  new PurchaseOrder();
            newPo.setOutlet(purchaseRequest.getOutlet());
            newPo.setPoNumber(generatePONumber(purchaseRequest.getPurchaseRequestNumber()));
            newPo.setPrNumber(purchaseRequest.getPurchaseRequestNumber());
            newPo.setRemarks(purchaseRequest.getRemarks());
            newPo.setUser(purchaseRequest.getUser());
            newPo.setRequestStatus(RequestStatus.DIPROSES);
            newPo.setDiscount(BigDecimal.ZERO);
            newPo.setGrandTotal(BigDecimal.ZERO);
            purchaseOrderDao.save(newPo);
            for(int i=0; i < purchaseRequest.getRequestItemList().size(); i++){
                PurchaseOrderDetail newPoDetail = new PurchaseOrderDetail();
                newPoDetail.setInventory(purchaseRequest.getRequestItemList().get(i).getInventory());
                newPoDetail.setItemRemark(purchaseRequest.getRequestItemList().get(i).getItemRemarks());
                newPoDetail.setPurchaseOrder(newPo);
                newPoDetail.setItemPrice(BigDecimal.ZERO);
                newPoDetail.setQuantity(purchaseRequest.getRequestItemList().get(i).getQtyRev());
                newPoDetail.setSubTotal(newPoDetail.getItemPrice().multiply(BigDecimal.valueOf(newPoDetail.getQuantity())));
                purchaseOrderDetailDao.save(newPoDetail);
            }
        }

    }

    @Override
    public String generatePONumber(String prNumber) {
        String prefix = "PO";
        String poPrefix = prNumber.substring(prNumber.lastIndexOf("PR") +1);
        return prefix + poPrefix;
    }

    @Override
    public Optional<PurchaseOrder> findById(String id) {
        return purchaseOrderDao.findById(id);
    }
}
