package id.xaxxis.inventory.entity.master.suplier;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "suplier_type")
public class SuplierType {

    @Id
    @GeneratedValue(generator = "uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "type_name", unique = true, nullable = false)
    private String type;

    public SuplierType() {
    }

    public SuplierType(String type) {
        this.type = type;
    }
}
