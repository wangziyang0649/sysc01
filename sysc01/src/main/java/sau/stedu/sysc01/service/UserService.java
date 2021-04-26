package sau.stedu.sysc01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sau.stedu.sysc01.mapper.UserMapper;
import sau.stedu.sysc01.model.Role;
import sau.stedu.sysc01.model.User;

import java.util.List;

@SuppressWarnings("ALL")
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<Role> roles = userMapper.getRolesById(user.getId());
        user.setRoles(roles);
        return user;
    }
}
