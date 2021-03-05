package com.example.hive.kerberos.provider.bean;

import java.util.Date;

public class UserInfoDO {
    private Integer user_id;
    private String username;
    private String pwd;
    private String email;
    private Integer grade;
    private Date createtime;

    public UserInfoDO(){}

    public UserInfoDO(String username) {
        this.username = username;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", email='" + email + '\'' +
                ", grade=" + grade +
                ", createtime=" + createtime +
                '}';
    }
}
