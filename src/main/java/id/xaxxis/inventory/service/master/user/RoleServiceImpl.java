package id.xaxxis.inventory.service.master.user;

import id.xaxxis.inventory.dao.master.user.RoleDao;
import id.xaxxis.inventory.entity.master.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role findByType(String type) {
        return roleDao.findByType(type);
    }

    @Override
    public Role findByRoleId(String roleId) {
        return roleDao.findByRoleId(roleId);
    }
}
