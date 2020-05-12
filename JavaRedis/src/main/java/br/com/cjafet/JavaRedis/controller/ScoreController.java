package br.com.cjafet.JavaRedis.controller;

import br.com.cjafet.JavaRedis.model.UserDTO;
import br.com.cjafet.JavaRedis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    private final RedisService redisService;

    @Autowired
    public ScoreController(RedisService redisService) {
        this.redisService = redisService;
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
        return redisService.NumberOfMembers();
    }

}
