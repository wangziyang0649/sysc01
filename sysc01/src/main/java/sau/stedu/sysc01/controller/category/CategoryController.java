package sau.stedu.sysc01.controller.category;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sau.stedu.sysc01.api.UserLoginToken;
import sau.stedu.sysc01.model.Category;
import sau.stedu.sysc01.model.RespBean;
import sau.stedu.sysc01.service.category.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/game")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @UserLoginToken
    @GetMapping("/getCategoryInfos")
    public PageInfo getCategoryInfos(String keyWords, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return categoryService.getCategoryInfos(keyWords,pageNum,pageSize);
    }

    @UserLoginToken
    @GetMapping("/getCategoryInfoid")
    public PageInfo getCategoryInfid(Long id, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return categoryService.getCategoryInfoid(id,pageNum,pageSize);
    }

    @UserLoginToken
    @GetMapping("/find")
    public PageInfo find(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return categoryService.find(pageNum,pageSize);
    }

    @UserLoginToken
    @GetMapping("/findme")
    public PageInfo findme(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return categoryService.findme(pageNum,pageSize);
    }

    @UserLoginToken
    @PostMapping("/add")
    public RespBean addgame(String gamename, String begintime1,String endtime1){
        long time = Long.parseLong(begintime1);
        Timestamp timestamp = new Timestamp(time);
        Date begintime = new Date(time);
        long time2 = Long.parseLong(endtime1);
        Timestamp timestamp2 = new Timestamp(time2);
        Date endtime = new Date(time2);
        Category category = new Category(gamename,begintime,endtime);
        return categoryService.addgame(category);
    }
}
