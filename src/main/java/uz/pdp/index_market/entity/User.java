package uz.pdp.index_market.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "users")
public class User implements UserDetails {
    @Id
    private UUID id = UUID.randomUUID();


    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @CreationTimestamp
    private Timestamp created_at;

    @ManyToOne
    private UserRole userRole;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;


    //=====================METHODS FOR WEB USER==========================================================================================================================================================================================================================================================================================================================================================================================================================================================================================


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.getName());
        authorities.add(grantedAuthority);
        return authorities;

    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
