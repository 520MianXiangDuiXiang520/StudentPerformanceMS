package top.junebao.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
    public String id;
    public String name;
    public String sex;
    public String tel;
    public String place;
    public String password;

    public User(String id, String name, String sex, String tel, String place, String password) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.tel = tel;
        this.place = place;
        this.password = password;
    }

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public User(){}

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", tel='" + tel + '\'' +
                ", place='" + place + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
