package top.lizec.smartreview.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.lizec.smartreview.entity.User;
import top.lizec.smartreview.entity.UserDetail;
import top.lizec.smartreview.mapper.UserDao;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserServer implements UserDetailsService {

    @Resource
    private UserDao userDao;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 系统使用用户邮箱作为用户标识
        User user = userDao.findByUserEmail(email);

        return Optional.ofNullable(user)
                .map(UserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }

    public Optional<User> createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userDao.insertUser(user);
            return Optional.of(user);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }
}
