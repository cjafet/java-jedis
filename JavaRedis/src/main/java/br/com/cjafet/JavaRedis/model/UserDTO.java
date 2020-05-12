package br.com.cjafet.JavaRedis.model;


public class UserDTO {
    private String userName;
    private Integer score;
    private Long position;

    public UserDTO(String userName, Integer score, Long position) {
        this.userName = userName;
        this.score = score;
        this.position = position;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getScore() {
        return score;
    }

    public Long getPosition() {
        return position;
    }

}
