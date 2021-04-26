package sau.stedu.sysc01.mapper;

import org.apache.ibatis.annotations.Param;
import sau.stedu.sysc01.model.Category;

import java.util.List;

public interface CategoryMapper {
    List<Category> getCategoryInfos(String keyWords);
    List<Category> getCategoryInfoid(Long id);
    List<Category> find();
    List<Category> findme(Long id);
    int addgame(Category category);
    int checkCateRepeat(Category category);
}
