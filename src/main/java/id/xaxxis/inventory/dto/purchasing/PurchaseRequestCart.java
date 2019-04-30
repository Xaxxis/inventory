package id.xaxxis.inventory.dto.purchasing;

import id.xaxxis.inventory.entity.inventory.Inventory;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class PurchaseRequestCart implements Serializable {

    private Inventory inventory;

    private Integer qtyReq;

    private Integer qtyRev;

    private String itemRemarks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseRequestCart that = (PurchaseRequestCart) o;
        return Objects.equals(inventory, that.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventory);
    }
}
