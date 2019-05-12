package id.xaxxis.inventory.entity.master.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "master_location")
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MasterLocation {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "location_id")
    private String locationId;

    @Column(name = "location_name", length = 50, unique = true, nullable = false)
    private String locationName;

    @Column(name = "address", length = 100)
    private String address;

//    @OneToMany(
//            mappedBy = "masterLocation",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    private Set<Inventory> inventory;

//    @OneToMany(
//            fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL,
//            mappedBy = "masterLocation")
//    private Set<User> user;

    public MasterLocation() {
    }

    public MasterLocation(String locationName, String address) {
        this.locationName = locationName;
        this.address = address;
    }
}
