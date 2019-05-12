package id.xaxxis.inventory.service.master.user;

import id.xaxxis.inventory.dao.master.user.UserDao;
import id.xaxxis.inventory.entity.master.location.MasterLocation;
import id.xaxxis.inventory.entity.master.location.Outlet;
import id.xaxxis.inventory.entity.master.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG  = LoggerFactory.getLogger(UserService.class);

    private final UserDao userDao;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User createUser(User user) {
        User localUser = userDao.findByUsername(user.getUsername());
        if(localUser != null){
            LOG.info("Username {} sudah digunakan, proses registrasi tidak dapat dilanjutkan" + user.getUsername());
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            localUser = userDao.save(user);
        }
        return localUser;
    }



    @Override
    public boolean checkUserExist(String username, String email) {
        return checkEmailExist(email) || checkUsernameExist(username);
    }

    @Override
    public boolean checkEmailExist(String email) {
        return null != findByEmail(email);
    }

    @Override
    public boolean checkUsernameExist(String username) {
        return null != findByUsername(username);
    }

    @Override
    public boolean checkOldPassword(String oldPassword, String password) {
        if(passwordEncoder.matches(oldPassword,password)){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkOutletEqualLocation(Outlet outlet, MasterLocation masterLocation) {
        return outlet.getMasterLocation().equals(masterLocation);
    }

    @Override
    public boolean hasRole(String roles) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getRoles().toString().contains(roles);
    }


    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findByUserId(String userId) {
        return userDao.findByUserId(userId);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }


}
