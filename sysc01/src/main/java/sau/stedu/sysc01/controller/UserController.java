package sau.stedu.sysc01.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import sau.stedu.sysc01.api.UserLoginToken;
import sau.stedu.sysc01.config.TokenUtils;
import sau.stedu.sysc01.model.RespBean;
import sau.stedu.sysc01.model.User;

import java.lang.annotation.Target;

@RestController
@CrossOrigin
public class UserController {
    @GetMapping("/root/hello")
    public String hello1() {
        return "hello root";
    }

    @GetMapping("/admin/hello")
    public String hello2() {
        return "hello admin";
    }

    @GetMapping("/teacher/hello")
    public String hello3() {
        return "hello teacher";
    }

    @GetMapping("/student/hello")
    public String hello4() {
        return "hello student";
    }

    @GetMapping("/login")
    public RespBean hello5() {
        return RespBean.error(500,"请先登录！");
    }

    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }

    @GetMapping("/referee/hello")
    public String hello6() {
        return "hello referee";
    }

    @GetMapping("/test/hello")
    public String hello7() {
        return "hello referee";
    }
}
