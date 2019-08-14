package id.xaxxis.inventory.entity.purchasing.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.xaxxis.inventory.entity.BaseEntity;
import id.xaxxis.inventory.entity.master.location.Outlet;
import id.xaxxis.inventory.entity.master.user.User;
import id.xaxxis.inventory.enums.RequestStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "purchase_order")
public class PurchaseOrder extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", length = 80)
    private String poId;

    @Column(name = "po_number", length = 30, nullable = false, updatable = false)
    private String poNumber;

    @Column(name = "pr_number", length = 30, nullable = false, updatable = false, unique = true)
    private String prNumber;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outlet_id")
    private Outlet outlet;

    @JsonBackReference
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<PurchaseOrderDetail> purchaseOrderDetails = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", length = 30)
    private RequestStatus requestStatus;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "grand_total")
    private BigDecimal grandTotal;

    @Column(name = "remarks", length = 150)
    private String remarks;

    @Transient
    private Integer totalItems;


    @PostLoad
    public void calcTotalItems(){
        this.totalItems = purchaseOrderDetails.size();
    }



}
