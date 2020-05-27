package br.com.cjafet.JavaRedis.controller;

import br.com.cjafet.JavaRedis.model.User;
import br.com.cjafet.JavaRedis.model.UserDTO;
import br.com.cjafet.JavaRedis.service.LeaderboardService;
import br.com.cjafet.JavaRedis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    private final RedisService redisService;
    private final LeaderboardService leaderboardService;

    @Autowired
    public ScoreController(RedisService redisService, LeaderboardService leaderboardService) {
        this.redisService = redisService;
        this.leaderboardService = leaderboardService;
    }

    @PostMapping("/user")
    public boolean addUser(@RequestBody User user) {
        return leaderboardService.addOrUpdateUser(user);

    }

    @GetMapping("/users")
    public SortedSet<User> listUsers() {
        return leaderboardService.listUsers();
    }

    @GetMapping("/user/{userName}")
    public Integer listUser(@PathVariable String userName) {
        return leaderboardService.GetUserScore(userName);
    }

    @GetMapping("/users/members")
    public int getNumberOfMembers() {
        return leaderboardService.GetNumberOfMembers();
    }


    @PostMapping("/")
    public Integer setScore(@RequestBody UserDTO userDTO) {
        return redisService.AddPoints(userDTO.getUserName(), userDTO.getScore());

    }

    @GetMapping("/")
    public Map<String, List<UserDTO>> getLeaderboard() {
        return redisService.ListWithScore();
    }

    @GetMapping("/{userName}")
    public Integer getUserScore(@PathVariable String userName) {
        return redisService.GetUserScore(userName);
    }

    @GetMapping("/rank/{userName}")
    public Map<String, Integer> getUserRank(@PathVariable String userName) {
        return redisService.GetUserData(userName);
    }

    @GetMapping("/members")
    public Long getNumberOfUsers() {
        return redisService.GetNumberOfMembers();
    }

}
