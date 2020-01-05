package top.junebao.domain;

public class StudentClass {
    private int id;
    private String className;
    private String magor;

    @Override
    public String toString() {
        return "StudentClass{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", magor='" + magor + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
