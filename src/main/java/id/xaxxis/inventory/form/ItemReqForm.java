package id.xaxxis.inventory.form;

import id.xaxxis.inventory.entity.purchasing.pr.PurchaseRequestItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ItemReqForm implements Serializable {

    private List<PurchaseRequestItem> purchaseRequestItemList;


}
