package id.xaxxis.inventory.service.purchasing.pr;

import id.xaxxis.inventory.entity.master.item.MasterItem;
import id.xaxxis.inventory.entity.purchasing.PurchaseRequest;
import id.xaxxis.inventory.entity.purchasing.PurchaseRequestItem;

import java.util.Map;

public interface PurchaseRequestService {

    void addItem(MasterItem masterItem);

    void removeItem(MasterItem masterItem);

    void updateItem(MasterItem masterItem, Integer qty);

    Map<MasterItem, Integer> getItemInCart();

    void createPurchaseRequest(PurchaseRequest purchaseRequest, PurchaseRequestItem purchaseRequestItem);

    String generatePrNumber();

}
