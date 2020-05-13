package br.com.cjafet.JavaRedis.service;

import br.com.cjafet.JavaRedis.client.RedisClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class RedisServiceTest {


    private static RedisClient redisClient;
    private static RedisService redisService;


    @BeforeAll
    public static void setup() {
        redisClient = new RedisClient();
        redisService = new RedisService(redisClient);
        redisClient.RemoveUser("TestUser");
    }

    @Test
    void addPointsToNull() {

        redisService.AddPoints("TestUser", 200);

        assertEquals(200, redisClient.GetUserScore("TestUser"));

    }

    @Test
    void addPointsShouldUpdateScore() {

        redisService.AddPoints("TestUser", 200);

        assertEquals(400, redisClient.GetUserScore("TestUser"));
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