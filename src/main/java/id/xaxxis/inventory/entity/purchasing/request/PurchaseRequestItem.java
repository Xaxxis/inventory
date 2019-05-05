package id.xaxxis.inventory.entity.purchasing.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.xaxxis.inventory.entity.BaseEntity;
import id.xaxxis.inventory.entity.inventory.Inventory;
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
    @JoinColumn(name = "inv_id", nullable = false)
    private Inventory inventory;

    @Column(name = "req_qty",  nullable = false, length = 7)
    @Digits(integer = 5, fraction = 1)
    private Integer qtyReq;

    @Column(name = "rev_qty",  nullable = false, length = 7)
    @Digits(integer = 5, fraction = 1)
    private Integer qtyRev;

    @Column(name = "item_remarks", length = 100)
    private String itemRemarks;

    public PurchaseRequestItem() {
    }

    public PurchaseRequestItem(PurchaseRequest purchaseRequest, Inventory inventory, @Digits(integer = 5, fraction = 1) Integer qtyReq, @Digits(integer = 5, fraction = 1) Integer qtyRev, String itemRemarks) {
        this.purchaseRequest = purchaseRequest;
        this.inventory = inventory;
        this.qtyReq = qtyReq;
        this.qtyRev = qtyRev;
        this.itemRemarks = itemRemarks;
    }
}
