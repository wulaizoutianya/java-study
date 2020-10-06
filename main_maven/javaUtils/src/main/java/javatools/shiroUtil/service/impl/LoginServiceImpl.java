package javatools.shiroUtil.service.impl;

import javatools.shiroUtil.entity.CmSysRoleInfoEntity;
import javatools.shiroUtil.entity.CmUserBaseInfoEntity;
import javatools.shiroUtil.entity.LoginResult;
import javatools.shiroUtil.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Override
    public CmUserBaseInfoEntity addUser(CmUserBaseInfoEntity user) {
        return null;
    }

    @Override
    public CmSysRoleInfoEntity addRole(CmSysRoleInfoEntity role) {
        return null;
    }

    @Override
    public LoginResult userLogin(String userAccount, String password) {
        LoginResult loginResult = new LoginResult();
        if (userAccount == null || userAccount.isEmpty()) {
            loginResult.setLogin(false);
            loginResult.setResult("用户名为空");
            return loginResult;
        }
        String msg;
        // 1、获取Subject实例对象
        Subject currentUser = SecurityUtils.getSubject();
        // 2、判断当前用户是否登录
        /*if (currentUser.isAuthenticated() == false) {

        }*/
        // 3、将用户名和密码封装到UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(userAccount, password);
        try {           // 4、认证
            currentUser.login(token);   //传到MyAuthorizingRealm类中的方法进行认证
            Session session = currentUser.getSession();
            session.setAttribute("userAccount", userAccount);
            loginResult.setLogin(true);
            return loginResult;
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            msg = "UnknownAccountException -- > 账号不存在：";
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            msg = "IncorrectCredentialsException -- > 密码不正确：";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            e.printStackTrace();
            msg = "用户验证失败";
        }
        loginResult.setLogin(false);
        loginResult.setResult(msg);
        return loginResult;
    }

    @Override
    public void userLogout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

}
