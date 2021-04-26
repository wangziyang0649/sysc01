package sau.stedu.sysc01.mapper;

import org.apache.ibatis.annotations.Mapper;
import sau.stedu.sysc01.model.Menu;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> getMenuRoles();
}