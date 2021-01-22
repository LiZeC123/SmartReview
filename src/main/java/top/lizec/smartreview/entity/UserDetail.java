package top.lizec.smartreview.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetail implements UserDetails {
    private final User user;
    private final List<GrantedAuthority> authorities;

    public UserDetail(User user) {
        this.user = user;
        this.authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // 系统采取邮箱作为用户名进行认证, 实际的用户名仅用于显示
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnable();
    }

    public Integer getUserId() {
        return user.getId();
    }
}
