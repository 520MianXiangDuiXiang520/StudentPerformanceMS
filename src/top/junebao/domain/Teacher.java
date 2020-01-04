package top.junebao.domain;

public class Teacher extends User {
    private String dept;
    private String degree;
    private String jobTitle;

    public Teacher(){}

    public Teacher(String id, String password) {
        super(id, password);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "dept='" + dept + '\'' +
                ", degree='" + degree + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", tel='" + tel + '\'' +
                ", place='" + place + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
