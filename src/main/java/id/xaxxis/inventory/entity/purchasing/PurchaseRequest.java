package id.xaxxis.inventory.entity.purchasing;

import id.xaxxis.inventory.entity.master.location.MasterLocation;
import id.xaxxis.inventory.entity.master.user.User;
import id.xaxxis.inventory.enums.RequestStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "purchase_request")
public class PurchaseRequest {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "purchase_req_id")
    private String purchaseReqId;

    @Column(name = "remarks", length = 200)
    private String remarks;

    @Temporal(TemporalType.DATE)
    @Column(name = "use_date")
    private Date useDate;

    @GenericGenerator(
            name = "assigned-identity",
            strategy = "id.xaxxis.inventory.utils.PRNumberGenerator"
    )
    @GeneratedValue(
            generator = "assigned-identity",
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "pr_number", nullable = false, length = 50)
    private String purchaseRequestNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private MasterLocation masterLocation;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", length = 30)
    private RequestStatus requestStatus;

    @OneToMany(mappedBy = "purchaseRequest", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PurchaseRequestItem> requestItemList = new ArrayList<>();

    @Version
    @Column(name = "version")
    private Integer version;
}
