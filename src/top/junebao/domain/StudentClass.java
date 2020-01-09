package top.junebao.domain;

public class StudentClass {
    private String className;
    private String magor;
    private String school;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "StudentClass{" +
                ", className='" + className + '\'' +
                ", magor='" + magor + '\'' +
                '}';
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMagor() {
        return magor;
    }

    public void setMagor(String magor) {
        this.magor = magor;
    }
}
