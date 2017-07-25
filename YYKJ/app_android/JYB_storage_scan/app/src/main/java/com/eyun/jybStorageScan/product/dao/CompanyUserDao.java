package com.eyun.jybStorageScan.product.dao;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.jybStorageScan.product.entity.CompanyUser;

/**
 * Created by Administrator on 2017/3/7.
 */

public interface CompanyUserDao  {

    /**
     * 根据用户名和密码登录登录
     */
    boolean login(String userName, String pwd);

    /**
     * 根据用户名获取用户信息
     */
    CompanyUser getUserInfo(String userId);
}