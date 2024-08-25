package org.lisongyan.rpc.core.register.impl;
import com.alibaba.fastjson.JSON;
import org.lisongyan.rpc.core.cache.RpcSericeMetaCache;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.register.Register;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisRegister implements Register {
    private JedisPool jedisPool;

    private String prefix = "rpc";
    private String address = "127.0.0.1";
    private int port = 6379;
    private String passwod;

    public RedisRegister(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        jedisPool = new JedisPool(poolConfig,address,port);

    }

    public Jedis getJedis(){
        Jedis jedis = jedisPool.getResource();
        if (!ObjectUtils.isEmpty(passwod)){
            jedis.auth(passwod);
        }
        return jedis;
    }
    @Override
    public void registerInstance(ServiceMeta serviceMeta) {

        String key = getKey(serviceMeta);

        Jedis jedis = getJedis();
        String script = "redis.call('SADD', KEYS[1], ARGV[1])\n" +
                "redis.call('EXPIRE', KEYS[1], ARGV[2])";
        List<String> value = new ArrayList<>();
        value.add(JSON.toJSONString(serviceMeta));
        value.add(String.valueOf(10000));

        jedis.eval(script, Collections.singletonList(key),value);


        jedis.close();
    }

    private String getKey(ServiceMeta serviceMeta) {
        return prefix + ":"+ serviceMeta.getServiceName();
    }


    @Override
    public void deAllRegisterInstance(Set<ServiceMeta> serviceMetaSet) {

        Jedis jedis = getJedis();
        serviceMetaSet.forEach(serviceMeta -> {
            String key = buildKey(serviceMeta.getServiceName());
            jedis.srem(key,JSON.toJSONString(serviceMeta));
        });
        jedis.close();

    }

    @Override
    public void deRegisterInstance(ServiceMeta serviceMeta) {

    }



    public String buildKey(String serviceNameKey){
        return prefix + ":" + serviceNameKey;
    }
    @Override
    public List<ServiceMeta> subscribeInstance(String serviceNameKey) {
        String s = prefix + ":" + serviceNameKey;
        Jedis jedis = getJedis();
        Set<String> list = jedis.smembers(s);
        jedis.close();
        return list.stream().map(o -> JSON.parseObject(o, ServiceMeta.class)).collect(Collectors.toList());
    }
}
