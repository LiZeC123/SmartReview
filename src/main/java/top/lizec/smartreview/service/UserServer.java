package top.lizec.smartreview.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.lizec.smartreview.entity.User;
import top.lizec.smartreview.mapper.UserMapper;

import javax.annotation.Resource;

@Service
public class UserServer implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        System.out.println(user);
        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));

        return user;
    }

//    public Long createUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        throw new UnsupportedOperationException("");
//    }
}
