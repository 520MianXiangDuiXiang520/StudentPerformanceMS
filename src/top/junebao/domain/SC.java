package top.junebao.domain;

public class SC {
    private int id;
    private Student student;
    private Course course;
    private String scStatus;
    private float score;

    @Override
    public String toString() {
        return "SC{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", scStatus='" + scStatus + '\'' +
                ", score=" + score +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getScStatus() {
        return scStatus;
    }

    public void setScStatus(String scStatus) {
        this.scStatus = scStatus;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
