package top.lizec.smartreview.mapper;

import java.sql.SQLIntegrityConstraintViolationException;

import top.lizec.smartreview.entity.User;

public interface UserDao {

    User findByUserEmail(String email);

    Long insertUser(User user) throws SQLIntegrityConstraintViolationException;
}
