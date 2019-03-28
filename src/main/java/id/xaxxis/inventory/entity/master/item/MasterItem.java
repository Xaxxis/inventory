package id.xaxxis.inventory.entity.master.item;

import id.xaxxis.inventory.entity.inventory.Inventory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "master_item")
@EqualsAndHashCode
public class MasterItem {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "item_id")
    private String itemId;

    @Column(name = "item_barcode", length = 100, unique = true)
    private String itemBarcode;

    @Column(name = "item_name", length = 100)
    private String itemName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryItem categoryItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitItem unitItem;

    @OneToMany(
            mappedBy = "masterItem",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Inventory> inventories;

    public MasterItem() {
    }

    public MasterItem(String itemBarcode, String itemName, CategoryItem categoryItem, UnitItem unitItem, Set<Inventory> inventories) {
        this.itemBarcode = itemBarcode;
        this.itemName = itemName;
        this.categoryItem = categoryItem;
        this.unitItem = unitItem;
        this.inventories = inventories;
    }
}
