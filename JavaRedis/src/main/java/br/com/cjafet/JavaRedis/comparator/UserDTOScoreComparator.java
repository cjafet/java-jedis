package br.com.cjafet.JavaRedis.comparator;

import br.com.cjafet.JavaRedis.model.UserDTO;
import java.util.Comparator;

public class UserDTOScoreComparator implements Comparator<UserDTO> {

    @Override
    public int compare(UserDTO firstUser, UserDTO secondUser) {
        return (int) (secondUser.getScore() - firstUser.getScore());
    }
}
