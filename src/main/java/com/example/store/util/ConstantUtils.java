package com.example.store.util;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 罗蕾
 * @version 1.0
 * @date 2019/11/5 23:27
 * @description
 **/
public class ConstantUtils {
    public static final String UNKNOWN = "-1";
    public static final String NOTACTIVE = "0";
    public static final String ACTIVE = "1";
    public static final String LOCKED = "2";
    public static final Integer ERROR = 0;
    public static final Integer SUCCESS = 1;
    public static final Integer FAILED = 2;
    public static final String ADMIN = "0";
    public static final String SALES = "1";
    public static final String CUSTOMER = "2";
    public static final Map<String,String> ROLE_MAP = new HashMap<>();
    static {
        ROLE_MAP.put(ConstantUtils.ADMIN,"admin");
        ROLE_MAP.put(ConstantUtils.SALES,"sales");
        ROLE_MAP.put(ConstantUtils.CUSTOMER,"customer");
    }

    /**
     * 随机获得主键
     * @return
     */
    public static synchronized String getUniqueKey() {
        //取前8位为用户主键
        return UUID.randomUUID().toString().substring(0,8);
    }

    /**
     * 密码加密
     * @param userPwd
     * @return
     */
    public static String getPassword(String userPwd,String salt){
        return new SimpleHash("MD5",userPwd,salt,1024).toHex();
    }
}
