package sau.stedu.sysc01.mapper;

import org.apache.ibatis.annotations.Mapper;
import sau.stedu.sysc01.model.Role;
import sau.stedu.sysc01.model.User;

import java.util.List;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    User loadUserByUsername(String username);
    // 根据用户id查询角色
    List<Role> getRolesById(Long id);


}