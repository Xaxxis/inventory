package id.xaxxis.inventory.entity.master.suplier;

import id.xaxxis.inventory.entity.master.location.MasterLocation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "master_suplier")
public class Suplier {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "suplier_id", length = 100, unique = true)
    private String suplierId;

    @Column(name = "name", length = 100)
    private String suplierName;

    @Column(name = "pic", length = 30)
    private String suplierPic;

    @Column(name = "phone", length = 20)
    private String suplierPhone;

    @Column(name = "address", length = 150)
    private String address;

    @Column(name = "active")
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private MasterLocation masterLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private SuplierType suplierType;

    public Suplier() {
    }

    public Suplier(String suplierName, String suplierPic, String suplierPhone, String address, MasterLocation masterLocation, SuplierType suplierType) {
        this.suplierName = suplierName;
        this.suplierPic = suplierPic;
        this.suplierPhone = suplierPhone;
        this.address = address;
        this.masterLocation = masterLocation;
        this.suplierType = suplierType;
    }
}
