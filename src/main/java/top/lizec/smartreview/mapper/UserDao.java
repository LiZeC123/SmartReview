package top.lizec.smartreview.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.lizec.smartreview.entity.User;

@Mapper
public interface UserDao {

    User findByUserName(String username);

    Long insertUser(User user);
}
