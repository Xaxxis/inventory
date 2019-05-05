package id.xaxxis.inventory.dto.purchasing.pr;

import id.xaxxis.inventory.entity.purchasing.request.PurchaseRequestItem;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseRequestItemDto {
    private List<PurchaseRequestItem> purchaseRequestItemList;

}
