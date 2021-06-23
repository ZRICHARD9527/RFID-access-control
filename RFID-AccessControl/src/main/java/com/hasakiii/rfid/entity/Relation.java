package com.hasakiii.rfid.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/11/23 15:20
 * @Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relation {
    private Integer r_id;
    private String g_id;
    private String u_id;
    private Boolean activity;

    public Relation(String g_id, String uId) {
        this.g_id = g_id;
        this.u_id = uId;
        this.activity = true;
    }
}
