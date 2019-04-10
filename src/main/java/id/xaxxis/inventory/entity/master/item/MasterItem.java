package id.xaxxis.inventory.entity.master.item;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "master_item")
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

//    @OneToMany(
//            mappedBy = "masterItem",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    private Set<Inventory> inventories;

    public MasterItem() {
    }

    public MasterItem(String itemBarcode, String itemName, CategoryItem categoryItem, UnitItem unitItem) {
        this.itemBarcode = itemBarcode;
        this.itemName = itemName;
        this.categoryItem = categoryItem;
        this.unitItem = unitItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MasterItem that = (MasterItem) o;
        return itemId.equals(that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
}
