package top.lizec.smartreview.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.lizec.smartreview.entity.User;

@Mapper
public interface UserMapper {

    User findByUserName(String username);
}
