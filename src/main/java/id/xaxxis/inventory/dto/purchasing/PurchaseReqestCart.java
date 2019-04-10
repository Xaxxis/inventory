package id.xaxxis.inventory.dto.purchasing;

import lombok.Data;

import java.io.Serializable;

@Data
public class PurchaseReqestCart implements Serializable {

    private String itemId;

    private String quantity;

    private String itemRemarks;

}
