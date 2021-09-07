package top.lizec.smartreview.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.annotation.Resource;

import top.lizec.smartreview.entity.User;
import top.lizec.smartreview.entity.UserDetail;
import top.lizec.smartreview.mapper.UserDao;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 系统使用用户邮箱作为用户标识
        User user = userDao.findByUserEmail(email);

        return Optional.ofNullable(user)
                .map(UserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }
}
