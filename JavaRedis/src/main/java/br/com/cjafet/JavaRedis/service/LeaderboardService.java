package br.com.cjafet.JavaRedis.service;

import br.com.cjafet.JavaRedis.model.User;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    private SortedSet<User> leaderBoard = new TreeSet<>(Comparator.comparing(User::getScore)).descendingSet();

    public  LeaderboardService() {

    }

    public boolean addOrUpdateUser(User user) {

        List<User> list = leaderBoard.stream().filter(u -> u.getUserName().equals(user.getUserName())).collect(Collectors.toList());

        if (!list.isEmpty()) {
            leaderBoard.remove(list.get(0));
        }
        leaderBoard.add(new User(user.getUserName(), user.getScore()));

        return true;
    }

    public SortedSet<User> listUsers() {
        return leaderBoard;
    }

    public Integer GetUserScore(String userName) {
        User user = leaderBoard.stream().filter(u -> u.getUserName().equals(userName)).collect(Collectors.toList()).get(0);
        return user.getScore();
    }

    public int GetNumberOfMembers() {
        return leaderBoard.size();
    }


}
