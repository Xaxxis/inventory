package id.xaxxis.inventory.entity.inventory;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
public class InventoryId implements Serializable {

    @Column(name = "inv_id", length = 100)
    private String invId;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "location_id")
    private String locationId;

    @Column(name = "outlet_id")
    private String outletId;

    public InventoryId() {
    }

    public InventoryId( String itemId, String locationId, String outletId) {
        this.itemId = itemId;
        this.locationId = locationId;
        this.outletId = outletId;
    }
}
