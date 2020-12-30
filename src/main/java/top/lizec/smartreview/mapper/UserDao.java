package top.lizec.smartreview.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.lizec.smartreview.entity.User;

import java.sql.SQLIntegrityConstraintViolationException;

@Mapper
public interface UserDao {

    User findByUserName(String username);

    User findByUserEmail(String email);

    Long insertUser(User user) throws SQLIntegrityConstraintViolationException;
}
