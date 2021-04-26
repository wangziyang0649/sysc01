package sau.stedu.sysc01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import sau.stedu.sysc01.model.Menu;
import sau.stedu.sysc01.model.Role;
import sau.stedu.sysc01.service.MenuService;

import java.util.Collection;
import java.util.List;

@Component
/**
 * 根据 访问的 url  返回 需要的角色
 */
public class MyFilterInvocation implements FilterInvocationSecurityMetadataSource {
    @Autowired
    MenuService service;
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // 获取访问的 url
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        // 获取所有权限和对应角色的关系
        List<Menu> menus =  service.getMenuRoles();
        // 循环比较
        for (Menu menu : menus) {
            if (antPathMatcher.match(menu.getPattern(), requestUrl)) {
                // 获取 角色信息
                List<Role> roles = menu.getRoles();
                String strArr[] = new String[roles.size()];
                int index = 0;
                for (Role role : roles) {
                    strArr[index] = role.getRname();
                    index++;
                }
                // 返回 需要的角色
                return SecurityConfig.createList(strArr);
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}