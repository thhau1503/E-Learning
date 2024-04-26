package com.e_learning;

public class NguoiDung {
    private Integer ID_User;
    private String HoTen;
    private Integer Point;
    private String Email;
    private String SDT;
    private String Username;
    private String Password;

    public NguoiDung() {
    }

    public NguoiDung(Integer ID_User, String HoTen, Integer Point, String Email, String SDT, String Username, String Password) {
        this.ID_User = ID_User;
        this.HoTen = HoTen;
        this.Point = Point;
        this.Email = Email;
        this.SDT = SDT;
        this.Username = Username;
        this.Password = Password;
    }

    public Integer getID_User() {
        return ID_User;
    }

    public void setID_User(Integer ID_User) {
        this.ID_User = ID_User;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public Integer getPoint() {
        return Point;
    }

    public void setPoint(Integer Point) {
        this.Point = Point;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getPassword() {
        return Password;
    }

    public String getUsername() {
        return Username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
