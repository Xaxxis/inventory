package id.xaxxis.inventory.entity.master.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import id.xaxxis.inventory.entity.master.location.MasterLocation;
import id.xaxxis.inventory.entity.master.location.Outlet;
import id.xaxxis.inventory.security.Authority;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "user_id")
    private String userId;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "username", nullable = false, length = 100)
    @NotEmpty(message = "*Username tidak boleh kosong")
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "*Password tidak boleh kosong")
    private String password;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    @Email(message = "*Mohon masukan format email dengan benar")
    @NotEmpty(message = "*Email tidak boleh kosong")
    private String email;

    @Column(name = "phone", length = 15)
    private String phoneNumber;

    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private MasterLocation masterLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outlet_id")
    private Outlet outlet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "User_Role", joinColumns = { @JoinColumn(name = "user_id")},
            inverseJoinColumns = { @JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(ur -> authorities.add(new Authority("ROLE_" + ur.getType())));
        return authorities;
    }

    @Column(name = "account_non_expired")
    private boolean isAccountNonExpired = true;

    @Column(name = "account_non_locked")
    public boolean isAccountNonLocked = true;

    @Column(name = "credential_non_expired")
    public boolean isCredentialsNonExpired = true;

    @Column(name = "active")
    public boolean enabled;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
