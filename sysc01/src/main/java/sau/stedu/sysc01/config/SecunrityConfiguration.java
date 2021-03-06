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
        return new BCryptPasswordEncoder();//????????????
    }

    @Override
    public void configure(WebSecurity web) throws Exception { // ??????????????????
        web.ignoring().antMatchers("/hello").antMatchers("/doLogin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // ????????????
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
                .formLogin() // ???????????????????????????
                .loginPage("/doLogin") // ????????????????????????
                .loginProcessingUrl("/login") // ????????????
                .usernameParameter("username") // ???????????????
                .passwordParameter("password")// ??????????????????
                .successHandler(new AuthenticationSuccessHandler() {// ????????????????????????
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setCharacterEncoding("utf-8");
                        resp.setContentType("application/json;charset=utf-8");
                        // ?????????????????????
                        User user = (User) (authentication.getPrincipal());
                        String token = tokenService.getToken(user);

                        RespBean respBean = RespBean.success1(200, "????????????",user,token);
                        resp.getWriter().println(new ObjectMapper().writeValueAsString(respBean));
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {// ????????????????????????
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setCharacterEncoding("utf-8");
                        resp.setContentType("application/json;charset=utf-8");
                        RespBean respBean = null;
                        if (e instanceof BadCredentialsException) {
                            respBean = RespBean.error(500, "??????????????????????????????????????????");
                        } else if (e instanceof LockedException) {
                            respBean = RespBean.error(500, "????????????????????????????????????");
                        } else if (e instanceof DisabledException) {
                            respBean = RespBean.error(500, "????????????????????????????????????");
                        } else if (e instanceof CredentialsExpiredException) {
                            respBean = RespBean.error(500, "???????????????????????????????????????");
                        } else if (e instanceof AccountExpiredException) {
                            respBean = RespBean.error(500, "?????????????????????????????????");
                        } else {
                            respBean = RespBean.error(500, "?????????????????????????????????");
                        }
                        resp.getWriter().println(new ObjectMapper().writeValueAsString(respBean));
                    }
                })
                .permitAll() // ????????????
                .and()
                .logout() // ??????????????????
                .logoutUrl("/logout")// ????????????
                .logoutSuccessUrl("/login")
                .deleteCookies()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setCharacterEncoding("utf-8");
                        resp.setContentType("application/json;charset=utf-8");
                        RespBean respBean = RespBean.success(200, "????????????");
                        resp.getWriter().println(new ObjectMapper().writeValueAsString(respBean));

                    }
                })
                .permitAll()
                .and()
                .csrf().disable();// ??????csrf??????
    }
}
