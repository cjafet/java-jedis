package br.com.cjafet.JavaRedis.service;

import br.com.cjafet.JavaRedis.comparator.UserScoreComparator;
import br.com.cjafet.JavaRedis.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;

@Service
public class RedisService {

    private final String KEY = "leaderboard";
    private Jedis jedis;
    private String host = System.getenv("host");
    private String port = System.getenv("port");
    private String password = System.getenv("password");

    @Autowired
    public RedisService() {
        jedis = new Jedis("192.168.99.100", 6379);
        jedis.auth("2wsxXSW@");
    }

    public Integer AddPoints(String userName, Integer points) {

        Integer newUserScore;
        Long member = this.IsMember(userName);

        if(member != null) {
            Integer userScore = this.GetUserScore(userName);
            newUserScore = userScore + points;
        } else {
            newUserScore = points;
        }

        this.InsertOrUpdateUserScore(newUserScore, userName);

        return this.GetUserScore(userName);
    }

    public Set<String> List() {
        return jedis.zrange(KEY, 0, 2);
    }

    public Map<String, List<UserDTO>> ListWithScore() {

        Set<String> members = jedis.zrangeByScore(KEY, "0", "20000");

        List<UserDTO> leaderBoard = new ArrayList<>();

        for (String member : members) {
            Integer score = this.GetUserScore(member);
            Long position = this.GetUserPosition(member);

            UserDTO user = new UserDTO(member, score, position);

            leaderBoard.add(user);
        }

        UserScoreComparator userScoreComparator = new UserScoreComparator();
        leaderBoard.sort(userScoreComparator);

        Map<String, List<UserDTO>> highScores = new HashMap<>();
        highScores.put("highscores", leaderBoard);

        return highScores;

    }

    public Map<String, Integer> GetUserData(String userName) {
        Map<String, Integer> user = new HashMap<String, Integer>();

        Integer userScore = this.GetUserScore(userName);
        user.put("score", userScore);

        Long userRank = this.GetUserPosition(userName);
        user.put("position", userRank.intValue());

        return user;
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
}
