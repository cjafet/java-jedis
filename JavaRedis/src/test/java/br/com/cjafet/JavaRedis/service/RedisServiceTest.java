package br.com.cjafet.JavaRedis.service;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.*;

class RedisServiceTest {

    private static String host = System.getenv("host");
    private static String port = System.getenv("port");
    private static String password = System.getenv("password");
    private static Jedis jedis = new Jedis(host, Integer.parseInt(port));
    private static final String KEY = "leaderboard";

    @BeforeAll
    public static void setup() {
        jedis.auth(password);
        jedis.zrem(KEY, "TestUser");
    }

    @Test
    void addPointsToNull() {
        RedisService redisService = new RedisService();

        redisService.AddPoints("TestUser", 200);

        assertEquals(200, redisService.GetUserScore("TestUser"));

    }

    @Test
    void addPointsShouldUpdateScore() {
        RedisService redisService = new RedisService();

        redisService.AddPoints("TestUser", 200);

        assertEquals(400, redisService.GetUserScore("TestUser"));
    }

    @Test
    void list() {
    }

    @Test
    void listWithScore() {
    }

    @Test
    void getUserData() {
    }

    @Test
    void insertOrUpdateUserScore() {
    }

    @Test
    void getUserScore() {
    }

    @Test
    void getUserPosition() {
    }

    @Test
    void numberOfMembers() {
    }
}