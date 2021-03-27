package top.lizec.smartreview.mapper;

import top.lizec.smartreview.entity.User;

import java.sql.SQLIntegrityConstraintViolationException;

public interface UserDao {

    User findByUserEmail(String email);

    Long insertUser(User user) throws SQLIntegrityConstraintViolationException;

    User getUserNameById(Integer id);
}
