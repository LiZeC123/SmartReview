package top.lizec.smartreview.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLIntegrityConstraintViolationException;

import top.lizec.smartreview.entity.User;

@Mapper
public interface UserDao {

    User findByUserEmail(String email);

    Long insertUser(User user) throws SQLIntegrityConstraintViolationException;
}
