package id.xaxxis.inventory.entity.purchasing.so;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.xaxxis.inventory.entity.BaseEntity;
import id.xaxxis.inventory.entity.master.item.MasterItem;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Digits;

@Data
@Table(name = "req_revision_detail")
@Entity
public class RequestRevisionDetail extends BaseEntity {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String revDetailId;

    @ManyToOne
    @JoinColumn(name = "req_revision_id")
    private RequestRevision requestRevision;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private MasterItem masterItems;

    @Column(name = "qty",  nullable = false, length = 7)
    @Digits(integer = 5, fraction = 1)
    private Integer quantity;

    @Column(name = "item_remarks", length = 100)
    private String itemRemarks;

    @Version
    @Column(name = "version", length = 3)
    private Integer version;



}
