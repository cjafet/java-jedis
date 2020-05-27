package br.com.cjafet.JavaRedis.model;

public class User implements Comparable<User> {
    private int userId;
    private String userName;
    private String email;
    private String password;
    private int age;
    private int score;
    private int position;
    private boolean isValid;

    public User(String userName, int score) {
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(User u) {
        if(this.userName.equals(u.userName)) {
            return 0;
        }
        return 1;
    }
}






