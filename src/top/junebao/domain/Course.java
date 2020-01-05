package top.junebao.domain;

public class Course {
    private String id;
    private String name;
    private float cScore;
    private int cTime;

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cScore=" + cScore +
                ", cTime=" + cTime +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCScore() {
        return cScore;
    }

    public void setCScore(float cScore) {
        this.cScore = cScore;
    }

    public int getCTime() {
        return cTime;
    }

    public void setCTime(int cTime) {
        this.cTime = cTime;
    }
}
