package org.lisongyan.rpc.core;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testjedis() {

        Jedis jedis = new Jedis("localhost", 6379);
        //如果返回 pong就代表链接成功
        log.info("jedis.ping():" + jedis.ping());
        //设置key0的值为 123456
        jedis.set("key0", "123456");
        //返回数据类型String
        log.info("jedis.type(key0): " + jedis.type("key0"));
        //取得值
        log.info("jedis.get(key0): " + jedis.get("key0"));
        //Key是否存在
        log.info("jedis.exists(key0):" + jedis.exists("key0"));
        //返回Key的长度
        log.info("jedis.strlen(key0): " + jedis.strlen("key0"));
        //返回截取字符串，范围"0,-1" 表示截取全部
        log.info("jedis.getrange(key0): " +
                jedis.getrange("key0", 0, -1));
        //返回截取字符串，范围"1,4" 表示区间[1,4]
        log.info("jedis.getrange(key0): " +
                jedis.getrange("key0", 1, 4));
        //追加字符串
        log.info("jedis.append(key0): " +
                jedis.append("key0","appendStr"));
        log.info("jedis.get(key0): " + jedis.get("key0"));

        //重命名
        jedis.rename("key0", "key0_new");
        //判断Key是否存在
        log.info("jedis.exists(key0): " + jedis.exists("key0"));
        //批量插入
        jedis.mset("key1", "val1", "key2", "val2", "key3", "100");
        //批量取出
        log.info("jedis.mget(key1,key2,key3): " +
                jedis.mget("key1", "key2", "key3"));
        //删除
        log.info("jedis.del(key1): " + jedis.del("key1"));
        log.info("jedis.exists(key1): " + jedis.exists("key1"));
        //取出旧值并设置新值
        log.info("jedis.getSet(key2): " +
                jedis.getSet("key2", "value3"));
        //自增1
        log.info("jedis.incr(key3): " + jedis.incr("key3"));
        //自增15
        log.info("jedis.incrBy(key3): " + jedis.incrBy("key3", 15));
        //自减1
        log.info("jedis.decr(key3): " + jedis.decr("key3"));
        //自减15
        log.info("jedis.decrBy(key3): " +
                jedis.decrBy("key3", 15));
        //浮点数加
        log.info("jedis.incrByFloat(key3): " +
                jedis.incrByFloat("key3",1.1));

        //返回0，只有在Key不存在的时候才设置
        log.info("jedis.setnx(key3): " +
                jedis.setnx("key3", "existVal"));
        log.info("jedis.get(key3): " + jedis.get("key3"));//3.1

        //只有Key都不存在的时候才设置，这里返回 null
        log.info("jedis.msetnx(key2,key3): "
                + jedis.msetnx("key2", "exists1", "key3", "exists2"));
        log.info("jedis.mget(key2,key3): " +
                jedis.mget("key2", "key3"));

        //设置key，2 秒后失效
        jedis.setex("key4", 2, "2 seconds is invalid");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //2 seconds is invalid
        log.info("jedis.get(key4): " + jedis.get("key4"));

        jedis.set("key6", "123456789");
        //下标从0开始，从第三位开始，用新值覆盖旧值
        jedis.setrange("key6", 3, "abcdefg");
        //返回：123abcdefg
        log.info("jedis.get(key6): " + jedis.get("key6"));

        //返回所有匹配的键
        log.info("jedis.get(key*): " +
                jedis.keys("key*")); jedis.close();

    }

    public void test4(){
        Jedis jedis = new Jedis("localhost");
        jedis.del("config");
        //设置Hash的 Field-Value对：ip=127.0.0.1
        jedis.hset("config", "ip", "127.0.0.1");
        //取得Hash的 Field关联的Value
        log.info("jedis.hget(): " + jedis.hget("config", "ip"));

        //取得类型：Hash
        log.info("jedis.type(): " + jedis.type("config"));

        //批量添加 field-value 对，参数为java map
        Map<String, String> configFields = new HashMap<String, String>();
        configFields.put("port", "8080");
        configFields.put("maxalive", "3600");
        configFields.put("weight", "1.0");
        //执行批量添加
        jedis.hmset("config", configFields);

        //批量获取：取得全部 field-value 对，返回 Java map映射表
        log.info("jedis.hgetAll(): " + jedis.hgetAll("config"));
        //批量获取：取得部分 Field对应的Value，返回Java map
        log.info("jedis.hmget(): " +
                jedis.hmget("config", "ip", "port"));

        //浮点数加: 类似于String的incrByFloat
        jedis.hincrByFloat("config", "weight", 1.2);
        log.info("jedis.hget(weight): " +
                jedis.hget("config", "weight"));
        //获取所有的Key
        log.info("jedis.hkeys(config): " + jedis.hkeys("config"));
        //获取所有的val
        log.info("jedis.hvals(config): " + jedis.hvals("config"));

        //获取长度
        log.info("jedis.hlen(): " + jedis.hlen("config"));
        //判断Field是否存在
        log.info("jedis.hexists(weight): " +
                jedis.hexists("config",  "weight"));
        //删除一个Field
        jedis.hdel("config", "weight");
        log.info("jedis.hexists(weight): " +
                jedis.hexists("config", "weight"));
        jedis.close();
    }

    public void test3(){
        Jedis jedis = new Jedis("localhost");
        log.info("jedis.ping (): " + jedis.ping());

        jedis.del("salary");
        Map<String, Double> members = new HashMap<String, Double>();
        members.put("u01", 1000.0);
        members.put("u02", 2000.0);
        members.put("u03", 3000.0);


        members.put("u04", 13000.0);
        members.put("u05", 23000.0);
        //批量添加元素，类型为Java map映射表
        jedis.zadd("salary", members);
        //获取类型ZSet
        log.info("jedis.type(): " + jedis.type("salary"));
        //获取集合元素的个数
        log.info("jedis.zcard(): " + jedis.zcard("salary"));
        //按照下标[起,止]遍历元素
        log.info("jedis.zrange(): " +
                jedis.zrange("salary", 0, -1));
        //按照下标[起,止]倒序遍历元素
        log.info("jedis.zrevrange(): " +
                jedis.zrevrange("salary", 0, -1));

        //按照分数（薪资）[起,止]遍历元素
        log.info("jedis.zrangeByScore(): " +
                jedis.zrangeByScore("salary",1000, 10000));
        //按照薪资[起,止]遍历元素，带分数返回
        Set<Tuple> res0 = jedis.zrangeByScoreWithScores(
                "salary", 1000, 10000);
        for (Tuple temp : res0) {
            log.info("Tuple.get(): " + temp.getElement() + " -> " +
                    temp.getScore());
        }
        //按照分数[起,止]倒序遍历元素
        log.info("jedis.zrevrangeByScore(): "
                + jedis.zrevrangeByScore("salary", 1000, 4000));
        //获取元素[起,止]分数区间的元素数量
        log.info("jedis.zcount(): " +
                jedis.zcount("salary", 1000, 4000));

        //获取元素score值：薪资
        log.info("jedis.zscore(): " + jedis.zscore("salary", "u01"));
        //获取元素的下标
        log.info("jedis.zrank(u01): " + jedis.zrank("salary", "u01"));
        //倒序获取元素的下标
        log.info("jedis.zrevrank(u01): " + jedis.zrevrank("salary",
                "u01"));
        //删除元素
        log.info("jedis.zrem(): " +
                jedis.zrem("salary", "u01", "u02"));
        //删除元素，通过下标范围
        log.info("jedis.zremrangeByRank(): " +
                jedis.zremrangeByRank("salary", 0, 1));
        //删除元素，通过分数范围
        log.info("jedis.zremrangeByScore(): "
                + jedis.zremrangeByScore("salary", 20000, 30000));
        //按照下标[起,止]遍历元素
        log.info("jedis.zrange(): " + jedis.zrange("salary", 0, -1));

        Map<String, Double> members2 = new HashMap<String, Double>();
        members2.put("u11", 1136.0);
        members2.put("u12", 2212.0);
        members2.put("u13", 3324.0);
        //批量添加元素
        jedis.zadd("salary", members2);
        //增加指定分数
        log.info("jedis.zincrby(10000): " +
                jedis.zincrby("salary", 10000, "u13"));
        //按照下标[起,止]遍历元素
        log.info("jedis.zrange(): " + jedis.zrange("salary", 0, -1));

        jedis.close();

    }
}
