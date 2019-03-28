package id.xaxxis.inventory.service.master.user;

import id.xaxxis.inventory.entity.master.user.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User findByUsername(String username);
    User findByUserId(String userId);
    User findByEmail(String email);
    User saveUser(User user);
    User createUser(User user);

    boolean checkUserExist(String username, String email);
    boolean checkEmailExist(String email);
    boolean checkUsernameExist(String username);
    boolean checkOldPassword(String oldPassword, String password);




}
