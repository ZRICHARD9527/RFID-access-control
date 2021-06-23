package com.hasakiii.rfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/11/22 18:21
 * @Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @TableId(type = IdType.AUTO)
    private Integer aId;
    private String aName;
    private String password;

}
