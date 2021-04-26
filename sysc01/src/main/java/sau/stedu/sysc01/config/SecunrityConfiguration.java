package sau.stedu.sysc01.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import sau.stedu.sysc01.model.RespBean;
import sau.stedu.sysc01.model.User;
import sau.stedu.sysc01.service.TokenService;
import sau.stedu.sysc01.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecunrityConfiguration extends WebSecurityConfigurerAdapter{
    @Autowired
    UserService service;
    @Autowired
    TokenService tokenService;
    @Autowired
    MyAccession myAccession;
    @Autowired
    MyFilterInvocation myFilterInvocation;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();//密码加密
    }

    @Override
    public void configure(WebSecurity web) throws Exception { // 忽略哪些接口
        web.ignoring().antMatchers("/hello").antMatchers("/doLogin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // 请求权限
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(myFilterInvocation);
                        o.setAccessDecisionManager(myAccession);
                        return o;
                    }
                })
                .and()
                .formLogin() // 配置登录有关的内容
                .loginPage("/doLogin") // 未登录的响应接口
                .loginProcessingUrl("/login") // 登录接口
                .usernameParameter("username") // 用户名参数
                .passwordParameter("password")// 用户密码参数
                .successHandler(new AuthenticationSuccessHandler() {// 登录成功的处理器
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setCharacterEncoding("utf-8");
                        resp.setContentType("application/json;charset=utf-8");
                        // 获取登录者信息
                        User user = (User) (authentication.getPrincipal());
                        String token = tokenService.getToken(user);

                        RespBean respBean = RespBean.success1(200, "登录成功",user,token);
                        resp.getWriter().println(new ObjectMapper().writeValueAsString(respBean));
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {// 登录失败的处理器
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setCharacterEncoding("utf-8");
                        resp.setContentType("application/json;charset=utf-8");
                        RespBean respBean = null;
                        if (e instanceof BadCredentialsException) {
                            respBean = RespBean.error(500, "用户名或密码错误，请重新确认");
                        } else if (e instanceof LockedException) {
                            respBean = RespBean.error(500, "账户被锁定，请联系管理员");
                        } else if (e instanceof DisabledException) {
                            respBean = RespBean.error(500, "账户被禁用，请联系管理员");
                        } else if (e instanceof CredentialsExpiredException) {
                            respBean = RespBean.error(500, "账户密码过期，请联系管理员");
                        } else if (e instanceof AccountExpiredException) {
                            respBean = RespBean.error(500, "账户过期，请联系管理员");
                        } else {
                            respBean = RespBean.error(500, "未知错误，请联系管理员");
                        }
                        resp.getWriter().println(new ObjectMapper().writeValueAsString(respBean));
                    }
                })
                .permitAll() // 接口放行
                .and()
                .logout() // 注销接口配置
                .logoutUrl("/logout")// 注销接口
                .logoutSuccessUrl("/login")
                .deleteCookies()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setCharacterEncoding("utf-8");
                        resp.setContentType("application/json;charset=utf-8");
                        RespBean respBean = RespBean.success(200, "注销成功");
                        resp.getWriter().println(new ObjectMapper().writeValueAsString(respBean));

                    }
                })
                .permitAll()
                .and()
                .csrf().disable();// 防止csrf攻击
    }
}
