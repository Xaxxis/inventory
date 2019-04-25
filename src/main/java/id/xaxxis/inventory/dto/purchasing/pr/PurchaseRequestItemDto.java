package id.xaxxis.inventory.dto.purchasing.pr;

import id.xaxxis.inventory.entity.purchasing.pr.PurchaseRequestItem;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseRequestItemDto {
    private List<PurchaseRequestItem> purchaseRequestItemList;

}
