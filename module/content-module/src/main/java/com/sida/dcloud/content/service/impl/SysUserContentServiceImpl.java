package com.sida.dcloud.content.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sida.dcloud.auth.po.SysUser;
import com.sida.dcloud.content.dao.SysUserContentMapper;
import com.sida.dcloud.content.po.SysUserContent;
import com.sida.dcloud.content.service.SysUserContentService;
import com.sida.dcloud.content.vo.SysUserContentVo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class SysUserContentServiceImpl extends BaseServiceImpl<SysUserContent> implements SysUserContentService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserContentServiceImpl.class);

    @Autowired
    private SysUserContentMapper sysUserContentMapper;

    @Override
    public IMybatisDao<SysUserContent> getBaseDao() {
        return sysUserContentMapper;
    }

    @RedisLock
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateUserList(List<SysUser> userList) {
        userList.forEach(user -> updateUser(user));
        return 0;
    }

    @RedisLock
    @Override
    public int updateUser(SysUser user) {
        return sysUserContentMapper.updateByUserPrimaryKey(user);
    }

    @Override
    public Page<SysUserContentVo> findPageList(SysUserContent po) {
        PageHelper.startPage(po.getP(),po.getS());
        List<SysUserContentVo> voList = sysUserContentMapper.selectVoList(po);
        return (Page) voList;
    }

    @Override
    public int inertDto(Map<String, String> map) {
        return sysUserContentMapper.insertDto(map);
    }

    @Override
    public int updateMobile(Map<String, String> map) {
        return sysUserContentMapper.updateMobile(map);
    }

    @Override
    public int updateUserInfo(Map<String, String> map) {
        return sysUserContentMapper.updateUserInfo(map);
    }
}