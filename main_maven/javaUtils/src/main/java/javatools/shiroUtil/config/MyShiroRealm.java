package javatools.shiroUtil.config;

import javatools.shiroUtil.entity.CmSysMenuInfoEntity;
import javatools.shiroUtil.entity.CmSysRoleInfoEntity;
import javatools.shiroUtil.entity.CmUserBaseInfoEntity;
import javatools.shiroUtil.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class MyShiroRealm extends AuthorizingRealm {

    @Resource(name = "userService")
    private UserService userService;

    //权限信息，包括添加角色以及菜单权限    controller里面有几个注释就会执行几次
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //如果身份认证的时候没有传入User对象，这里只能取到userName
        //也就是SimpleAuthenticationInfo构造的时候第一个参数传递需要User对象
        CmUserBaseInfoEntity cmUserBaseInfoEntity = (CmUserBaseInfoEntity) principalCollection.getPrimaryPrincipal();

        for (CmSysRoleInfoEntity role : cmUserBaseInfoEntity.getUserRoleList()) {
            authorizationInfo.addRole(role.getRoleId().toString());
            for (CmSysMenuInfoEntity p : role.getRoleMenuList()) {
                authorizationInfo.addStringPermission(p.getMenuPermit());
            }
        }
        return authorizationInfo;
    }

    //主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()，参数authenticationToken值：" + authenticationToken);
        // 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户的输入的账号.
        String userAccount = (String) authenticationToken.getPrincipal();
        System.out.println("authenticationToken.getCredentials()：" + authenticationToken.getCredentials());
        //通过username从数据库中查找 User对象.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        CmUserBaseInfoEntity user = userService.findUserByUserAccount(userAccount);
        System.out.println("----->>user = " + user);
        if (user == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(
                user,//这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
                user.getUserPwd(),      //密码
                //ByteSource.Util.bytes(user.getCredentialsSalt()),   //salt=username+salt
                getName()       //realm name
        );
    }
}
