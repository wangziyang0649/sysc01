package sau.stedu.sysc01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sau.stedu.sysc01.mapper.MenuMapper;
import sau.stedu.sysc01.model.Menu;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;
    public List<Menu> getMenuRoles() {
        return menuMapper.getMenuRoles();
    }
}