package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.client.OperationClient;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.lock.redis.RedisLock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by
 * 2017/10/27.
 */
@RestController
@RequestMapping("test")
@Api(description = "测试")
public class TestController extends BaseController {
    private final static Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private OperationClient operationClient;

    @PostMapping("load")
    @ApiOperation("加载初始数据")
    public Object load(@RequestBody @ApiParam("param")String param) {
        LOG.info("Param is {}", param);


        return toResult();
    }

    @GetMapping("redis")
    @ApiOperation("操作redis数据")
    public Object redis() {
        redisUtil.set("name", "Xiruo");
        LOG.info("Value for name which in redis is {}", redisUtil.get("name"));

        redisUtil.putInMap("abcdefg", null);
        return toResult();
    }

    @GetMapping("dlock")
    @ApiOperation("分布式锁")
    @RedisLock
    public Object dlock() {
        LOG.info("This is distributed lock test...");
        return toResult();
    }

    @GetMapping("operation")
    @ApiOperation("运营feign调用测试")
    @RedisLock
    public Object operation() {
        LOG.info("This is operation feign invoking test...");
        return toResult(operationClient.findCommonUserById("200"));
    }
}
