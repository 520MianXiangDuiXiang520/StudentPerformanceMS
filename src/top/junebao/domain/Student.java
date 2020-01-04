package top.junebao.domain;

public class Student extends User {
    public String studentClass;
    public String magor;

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getMagor() {
        return magor;
    }

    public void setMagor(String magor) {
        this.magor = magor;
    }

    public static void main(String[] args) {

    }



    public Student(){}

    public Student(String id, String password){
        super(id, password);
    }

    public Student(String id, String name, String sex, String studentClass,
                   String magor, String tel, String place, String password) {
        super(id, name,sex,tel, place, password);
        this.studentClass = studentClass;
        this.magor = magor;
    }

    @Override
    public String toString() {
        return "Student Class(" + this.name + ")";
    }
}
