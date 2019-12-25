package com.example.store.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.store.entity.User;
import com.example.store.service.UserService;
import com.example.store.util.ConstantUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 罗蕾
 * @version 1.0
 * @date 2019/11/05 20:49
 * @description
 **/
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进行授权");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        //授权
        if (ConstantUtils.ADMIN.equals(user.getUserType())){
            authorizationInfo.addRole("admin");
        }else if (ConstantUtils.SALES.equals(user.getUserType())){
            authorizationInfo.addRole("sales");
        }
        return authorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进行认证");
        String userName = (String) authenticationToken.getPrincipal();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        User dbUser = userService.getOne(queryWrapper);
        if (dbUser == null){
            //用户不存在
            throw new UnknownAccountException();
        }
        if (ConstantUtils.NOTACTIVE.equals(dbUser.getValidFlag())){
            throw new DisabledAccountException();
        }
        if (ConstantUtils.LOCKED.equals(dbUser.getValidFlag())){
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(dbUser,dbUser.getPassword(), ByteSource.Util.bytes(dbUser.getUserName()),getName());
    }
}
