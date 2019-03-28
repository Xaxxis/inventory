package id.xaxxis.inventory.dao.master.user;

import id.xaxxis.inventory.entity.master.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends PagingAndSortingRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByUserId(String userId);
    List<User> findAll();
}
