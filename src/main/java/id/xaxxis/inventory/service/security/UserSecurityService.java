package id.xaxxis.inventory.service.security;

import id.xaxxis.inventory.dao.master.user.UserDao;
import id.xaxxis.inventory.entity.master.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserSecurityService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userDao.findByUsername(username);
        if(null == user){
            LOG.warn("Username " +username+ " tidak ditemukan");
            throw new UsernameNotFoundException("Username " + username +" tidak ditemukan");
        }
        return user;
    }
}
