package br.com.cjafet.JavaRedis.client;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Set;

@Component
public class RedisClient {

    private final String KEY = "leaderboard";
    private Jedis jedis;
    private String host = System.getenv("host");
    private String port = System.getenv("port");
    private String password = System.getenv("password");

    public RedisClient() {
        jedis = new Jedis(host, Integer.parseInt(port));
        jedis.auth(password);
    }

    public Long InsertOrUpdateUserScore(Integer userScore, String userName) {
        return jedis.zadd(KEY, userScore, userName);
    }

    public Integer GetUserScore(String userName) {
        return jedis.zscore(KEY, userName).intValue();
    }

    public long GetUserPosition(String userName) {
        return jedis.zrevrank(KEY, userName) + 1;

    }

    public Long NumberOfMembers() {
        return jedis.zcount(KEY, "0", "100000");

    }

    public Long IsMember(String userName) {
        return jedis.zrank(KEY, userName);
    }

    public Set<String> getMembersByRank() {
        return jedis.zrangeByScore(KEY, "0", "20000");
    }

    public void RemoveUser(String userName) {
        jedis.zrem(KEY, userName);
    }
}
