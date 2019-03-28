package id.xaxxis.inventory.entity.master.item;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table(name = "unit")
public class UnitItem {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "unit_id", unique = true, nullable = false)
    private String unitId;

    @Column(name = "unit_name", length = 10, unique = true, nullable = false)
    @NotBlank
    private String unitName;

    @Column(name = "description", length = 50)
    private String unitDescription;

}
