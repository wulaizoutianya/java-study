package javatools.shiroUtil.web;

import javatools.shiroUtil.entity.CmSysRoleInfoEntity;
import javatools.shiroUtil.entity.CmUserBaseInfoEntity;
import javatools.shiroUtil.entity.LoginResult;
import javatools.shiroUtil.service.LoginService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource(name = "loginService")
    private LoginService loginService;

    /**
     * POST登录
     *
     * @param user user
     * @return ok
     */
    @PostMapping(value = "/userLogin")
    public String userLogin(@RequestBody CmUserBaseInfoEntity user) {
        System.out.println("entry userLogin");
        // 添加用户认证信息
        LoginResult loginResult = loginService.userLogin(user.getUserAccount(), user.getUserPwd());
        if (loginResult.isLogin()) {
            // 进行验证，这里可以捕获异常，然后返回对应信息
            return "userLogin ok!";
        } else {
            return loginResult.getResult();
        }
    }

    /**
     * 添加用户
     *
     * @param user user
     * @return String
     */
    @PostMapping(value = "/addUser")
    public String addUser(@RequestBody CmUserBaseInfoEntity user) {
        System.out.println("entry addUser");
        user = loginService.addUser(user);
        return "addUser is ok! \n" + user;
    }

    /**
     * 添加角色
     *
     * @param role role
     * @return String
     */
    @PostMapping(value = "/addRole")
    public String addRole(@RequestBody CmSysRoleInfoEntity role) {
        System.out.println("entry addRole");
        role = loginService.addRole(role);
        return "addRole is ok! \n" + role;
    }

    /**
     * 注解的使用
     *
     * @return String
     */
    @RequiresRoles("1")
    //@RequiresPermissions("create")
    @GetMapping(value = "/create")
    public String create() {
        return "Create success!";
    }

    @GetMapping(value = "/index")
    public String index() {
        System.out.println("entry index");
        return "index page!";
    }

    @GetMapping(value = "/error")
    public String error() {
        System.out.println("entry error");
        return "error page!";
    }

    @GetMapping(value = "/userLogout")
    public String logout() {
        System.out.println("entry userLogout");
        return "userLogout";
    }
}
