package id.xaxxis.inventory.entity.master.location;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@Table(name = "master_outlet")
@EqualsAndHashCode
public class Outlet {

    @Id
    @Column(name = "outlet_id", length = 50, unique = true)
    private String outletId;

    @Column(name = "outlet_name", length = 50, unique = true)
    private String outletName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private MasterLocation masterLocation;

    public Outlet() {
    }

    public Outlet(String outletId, String outletName, MasterLocation masterLocation) {
        this.outletId = outletId;
        this.outletName = outletName;
        this.masterLocation = masterLocation;
    }

}
