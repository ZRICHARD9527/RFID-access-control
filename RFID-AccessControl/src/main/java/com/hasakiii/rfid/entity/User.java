package com.hasakiii.rfid.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/11/22 18:16
 * @Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)//用来去除数据中的空值

public class User {
    private String uId;
    private String stuId;
    private String password;
    private String name;
    private String major;
    private String avatar;
    private Boolean activity;

    public User(String u_id, String stu_id, String name, String major) {
        this.uId = u_id;
        this.stuId = stu_id;
        this.password = stu_id;
        this.name = name;
        this.major = major;
    }

    public User(String stu_id, String name, String major, boolean activity) {
        this.stuId = stu_id;
        this.name = name;
        this.major = major;
        this.activity = activity;
    }
}
