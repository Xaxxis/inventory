package id.xaxxis.inventory.entity.master.user;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "TYPE", length = 15, unique = true, nullable = false)
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(roleId, role.roleId) &&
                Objects.equals(type, role.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, type);
    }

}
