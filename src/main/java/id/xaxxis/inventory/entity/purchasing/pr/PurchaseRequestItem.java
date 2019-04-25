package id.xaxxis.inventory.entity.purchasing.pr;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.xaxxis.inventory.entity.BaseEntity;
import id.xaxxis.inventory.entity.master.item.MasterItem;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;

@Entity
@Table(name = "purchase_request_item")
@Data
public class PurchaseRequestItem extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id")
    private String requestItemId;

    @ManyToOne
    @JoinColumn(name = "req_id")
    private PurchaseRequest purchaseRequest;

    @JsonManagedReference
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private MasterItem masterItems;

    @Column(name = "qty",  nullable = false, length = 7)
    @Digits(integer = 5, fraction = 1)
    private Integer quantity;

    @Column(name = "item_remarks", length = 100)
    private String itemRemarks;

    public PurchaseRequestItem() {
    }

    public PurchaseRequestItem(PurchaseRequest purchaseRequest, MasterItem masterItems, @Digits(integer = 5, fraction = 1) Integer quantity, String itemRemarks) {
        this.purchaseRequest = purchaseRequest;
        this.masterItems = masterItems;
        this.quantity = quantity;
        this.itemRemarks = itemRemarks;
    }
}
