package id.xaxxis.inventory.service.master.user;


import id.xaxxis.inventory.entity.master.user.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findByType(String type);
    Role findByRoleId(String roleId);
}
