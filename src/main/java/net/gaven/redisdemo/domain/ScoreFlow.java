package net.gaven.redisdemo.domain;

public class ScoreFlow {
    private Long id;

    private Long score;

    private Integer userId;

    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", score=").append(score);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append("]");
        return sb.toString();
    }

    public ScoreFlow(Long score, Integer userId, String userName) {
        this.score = score;
        this.userId = userId;
        this.userName = userName;
    }

    public ScoreFlow(Long id, Long score, Integer userId, String userName) {
        this.id = id;
        this.score = score;
        this.userId = userId;
        this.userName = userName;
    }
}