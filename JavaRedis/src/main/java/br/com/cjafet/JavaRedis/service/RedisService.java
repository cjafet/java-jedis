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

    @Autowired
    public RedisService() {
        String host = System.getenv("host");
        String port = System.getenv("port");
        String password = System.getenv("password");

        jedis = new Jedis(host, Integer.parseInt(port));
        jedis.auth(password);
    }

    public Integer AddPoints(String userName, Integer points) {
        Integer userScore = this.GetUserScore(userName);

        Integer newUserScore = userScore != null
                ? userScore + points
                : points;

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

}
