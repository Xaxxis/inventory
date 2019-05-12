package id.xaxxis.inventory.entity.purchasing.request;

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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "purchase_request")
public class PurchaseRequest extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "purchase_req_id")
    private String purchaseReqId;

    @Column(name = "remarks", length = 200)
    private String remarks;

    @Temporal(TemporalType.DATE)
    @Column(name = "use_date", nullable = false)
    private Date useDate;

    @Column(name = "pr_number", nullable = false, length = 50, unique = true)
    private String purchaseRequestNumber;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "address"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outlet_id")
    private Outlet outlet;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", length = 30)
    private RequestStatus requestStatus;

    @JsonBackReference
    @OneToMany(mappedBy = "purchaseRequest", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<PurchaseRequestItem> requestItemList = new ArrayList<>();

    @Version
    @Column(name = "version")
    private Integer version;

    @Transient
    private Integer totalItem;

    @PostLoad
    public void qtyOfItem(){
        this.totalItem = requestItemList.size();
    }


}
