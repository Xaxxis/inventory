package id.xaxxis.inventory.entity.purchasing.so;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.xaxxis.inventory.entity.BaseEntity;
import id.xaxxis.inventory.entity.master.location.MasterLocation;
import id.xaxxis.inventory.entity.master.user.User;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "request_revision")
@Entity
public class RequestRevision extends BaseEntity {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String revisionid;

    @Column(name = "pr_number", nullable = false, unique = true, length = 100)
    private String prNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "use_date", nullable = false)
    private Date useDate;

    @Column(name = "remarks", length = 200)
    private String remarks;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_user_id")
    private User requestBy;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revision_user_id")
    private User revisionBy;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "address"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private MasterLocation masterLocation;

    @JsonIgnore
    @OneToMany(mappedBy = "requestRevision", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RequestRevisionDetail> requestRevisionDetailList = new ArrayList<>();

    @Version
    @Column(name = "version")
    private Integer version;


}
