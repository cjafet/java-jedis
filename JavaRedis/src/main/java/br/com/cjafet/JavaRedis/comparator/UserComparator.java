package br.com.cjafet.JavaRedis.comparator;

import br.com.cjafet.JavaRedis.model.User;
import java.util.Comparator;

public class UserComparator implements Comparator<User> {

    @Override
    public int compare(User firstUser, User secondUser) {
        return (int) (secondUser.getScore() - firstUser.getScore());
    }
}
