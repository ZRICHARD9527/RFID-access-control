package com.hasakiii.rfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/11/23 0:10
 * @Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IoLog {
    @TableId(type = IdType.AUTO)
    private Integer ioId;
    private String uId;
    private Date time;
    private String gId;

    public IoLog(String g_id, String u_id, Date date) {
        this.gId = g_id;
        this.uId = u_id;
        this.time = date;
    }
}
