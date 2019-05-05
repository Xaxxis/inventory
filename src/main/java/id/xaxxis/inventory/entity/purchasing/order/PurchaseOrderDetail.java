package id.xaxxis.inventory.entity.purchasing.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.xaxxis.inventory.entity.inventory.Inventory;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "purchase_order_detail")
public class PurchaseOrderDetail {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", length = 80)
    private String poDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private PurchaseOrder purchaseOrder;

    @JsonManagedReference
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inv_id", nullable = false)
    private Inventory inventory;

    @Column(name = "quantity",  nullable = false, length = 7)
    @Digits(integer = 5, fraction = 1)
    private Integer quantity;

    @Column(name = "item_price", nullable = false)
    private BigDecimal itemPrice;

    @Column(name = "sub_total", nullable = false)
    private BigDecimal subTotal;

    @Column(name = "remarks", length = 50)
    private String itemRemark;

    @Transient
    public BigDecimal getSubTotalPrice(){
        return BigDecimal.valueOf(getQuantity()).multiply(getSubTotal());
    }

}
