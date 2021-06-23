package com.hasakiii.rfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/11/25 9:49
 * @Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageLog {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer aId;
    private String aName;
    private String event;
    private String obj;

    public ManageLog(Integer aId, String aName, String event, String obj) {
        this.aId = aId;
        this.aName = aName;
        this.event = event;
        this.obj = obj;
    }
}
