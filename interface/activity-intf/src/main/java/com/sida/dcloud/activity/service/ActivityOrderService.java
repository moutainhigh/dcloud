package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.ActivityInfo;
import com.sida.dcloud.activity.po.ActivityOrder;
import com.sida.dcloud.activity.vo.ActivityInfoVo;
import com.sida.dcloud.activity.vo.ActivityOrderVo;
import com.sida.xiruo.xframework.service.IBaseService;

public interface ActivityOrderService extends IBaseService<ActivityOrder> {
    Page<ActivityOrderVo> findPageList(ActivityOrder po);
}