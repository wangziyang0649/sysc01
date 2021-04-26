package sau.stedu.sysc01.service.category;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sau.stedu.sysc01.config.AuthenticationInterceptor;
import sau.stedu.sysc01.mapper.CategoryMapper;
import sau.stedu.sysc01.model.Category;
import sau.stedu.sysc01.model.RespBean;
import sau.stedu.sysc01.model.User;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CategoryService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    AuthenticationInterceptor authenticationInterceptor;
    public PageInfo getCategoryInfos(String keyWords, int pageNum, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = categoryMapper.getCategoryInfos(keyWords);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public PageInfo getCategoryInfoid(Long id, int pageNum, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = categoryMapper.getCategoryInfoid(id);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public PageInfo find(int pageNum, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = categoryMapper.find();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public PageInfo findme(int pageNum, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        User user = authenticationInterceptor.dfs();
        Long id = user.getId();
        List<Category> list = categoryMapper.findme(id);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public RespBean addgame(Category category){
        int num = categoryMapper.checkCateRepeat(category);
        if (num != 0) {
            return RespBean.error(500, "新增栏目名称重复，请重新填写");
        }else{
//            category.setCreateTime(new Date());
            int i = categoryMapper.addgame(category);
            if (i == 0) {
                return RespBean.error(500, "新增栏目失败");
            }else{
                return RespBean.success(200, "新增栏目成功");
            }
        }
    }
}
