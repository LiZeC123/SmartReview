package top.lizec.smartreview.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

import javax.annotation.Resource;

import top.lizec.smartreview.entity.User;
import top.lizec.smartreview.entity.UserDetail;
import top.lizec.smartreview.mapper.UserDao;
import top.lizec.smartreview.utils.TokenUtils;

@Service
public class UserService {

    @Resource
    AuthenticationManager auManager;
    @Resource
    private UserDao userDao;
    @Resource
    private PasswordEncoder passwordEncoder;

    public Optional<String> login(String email, String password) {
        try {
            Authentication au = auManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = ((UserDetail) au.getPrincipal()).getUser();
            return Optional.ofNullable(TokenUtils.createToken(user));
        } catch (BadCredentialsException e) {
            return Optional.empty();
        }
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
