package top.junebao.domain;

public class Student extends User {
    public String getStudent_class() {
        return student_class;
    }

    public void setStudent_class(String student_class) {
        this.student_class = student_class;
    }

    public String getMagor() {
        return magor;
    }

    public void setMagor(String magor) {
        this.magor = magor;
    }

    public static void main(String[] args) {

    }

    public String student_class;
    public String magor;

    public Student(){}

    public Student(String id, String password){
        super(id, password);
    }

    public Student(String id, String name, String sex, String student_class,
                   String magor, String tel, String place, String password) {
        super(id, name,sex,tel, place, password);
        this.student_class = student_class;
        this.magor = magor;
    }

    @Override
    public String toString() {
        return "Student Class(" + this.name + ")";
    }
}
