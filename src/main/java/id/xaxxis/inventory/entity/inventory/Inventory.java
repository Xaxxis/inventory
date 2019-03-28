package id.xaxxis.inventory.entity.inventory;

import id.xaxxis.inventory.entity.master.item.MasterItem;
import id.xaxxis.inventory.entity.master.location.MasterLocation;
import id.xaxxis.inventory.entity.master.location.Outlet;
import id.xaxxis.inventory.entity.master.suplier.Suplier;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "inventory")
@Data
@EqualsAndHashCode
public class Inventory {
    @EmbeddedId
    private InventoryId inventoryId = new InventoryId();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("itemId")
    private MasterItem masterItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("locationId")
    private MasterLocation masterLocation;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("outletId")
    private Outlet outlet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suplier_id")
    private Suplier suplier;

    @Column(name = "cost_price", length = 8)
    private BigDecimal costPrice;

    @Column(name = "sell_price", length = 8)
    private BigDecimal sellPrice;

    @Column(name = "on_stock", length = 5, nullable = false)
    private Integer stock;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public Inventory() {
    }

    public Inventory(InventoryId inventoryId, MasterItem masterItem, MasterLocation masterLocation, Outlet outlet, BigDecimal costPrice, BigDecimal sellPrice, Integer stock, Boolean isActive) {
        this.inventoryId = inventoryId;
        this.masterItem = masterItem;
        this.masterLocation = masterLocation;
        this.outlet = outlet;
        this.costPrice = costPrice;
        this.sellPrice = sellPrice;
        this.stock = stock;
        this.isActive = isActive;
    }

}
