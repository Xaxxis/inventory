package id.xaxxis.inventory.dao.master.user;

import id.xaxxis.inventory.entity.master.user.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {
    List<Role> findAll();
    Role findByType(String type);
    Role findByRoleId(String roleId);
}
