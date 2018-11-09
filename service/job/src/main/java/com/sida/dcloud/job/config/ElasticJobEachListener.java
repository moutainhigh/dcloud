package com.sida.dcloud.job.config;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.sida.dcloud.job.util.JobUtil;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ElasticJobEachListener implements com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener {
    public static final String REDIS_KEY_JOB_REMOVABLE = "REDIS_KEY_JOB_REMOVABLE";
    @Autowired
    private JobUtil jobUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {

    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        //任务结束后直接删除
        JSONObject json = (JSONObject) JSONObject.parse(shardingContexts.getJobParameter());
        if(!json.getBoolean("isloop")) {
            Optional.ofNullable(redisUtil.getOneByMapKey(REDIS_KEY_JOB_REMOVABLE, shardingContexts.getJobName())).map(obj -> (Boolean)obj).ifPresent(removable -> {
                if(removable) {
                    jobUtil.dropJob(json.getString("jobId"));
                }
            });
        }
    }
}
