package br.com.cjafet.JavaRedis.service;

import br.com.cjafet.JavaRedis.client.RedisClient;
import br.com.cjafet.JavaRedis.comparator.UserScoreComparator;
import br.com.cjafet.JavaRedis.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RedisService {

    private RedisClient redisClient;

    @Autowired
    public RedisService(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    public Integer AddPoints(String userName, Integer points) {

        Integer newUserScore;
        Long member = redisClient.IsMember(userName);

        if(member != null) {
            Integer userScore = redisClient.GetUserScore(userName);
            newUserScore = userScore + points;
        } else {
            newUserScore = points;
        }

        redisClient.InsertOrUpdateUserScore(newUserScore, userName);

        return redisClient.GetUserScore(userName);
    }

    public Map<String, List<UserDTO>> ListWithScore() {

        Set<String> members = redisClient.getMembersByRank();

        List<UserDTO> leaderBoard = new ArrayList<>();

        for (String member : members) {
            Integer score = redisClient.GetUserScore(member);
            Long position = redisClient.GetUserPosition(member);

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

        Integer userScore = redisClient.GetUserScore(userName);
        user.put("score", userScore);

        Long userRank = redisClient.GetUserPosition(userName);
        user.put("position", userRank.intValue());

        return user;
    }

    public Long GetNumberOfMembers() {
        return redisClient.NumberOfMembers();
    }

    public Integer GetUserScore(String userName) {
        Integer userScore;

        try {
            userScore = redisClient.GetUserScore(userName);
        } catch (Exception e) {
            userScore = null;
        }

        return userScore;
    }


}
