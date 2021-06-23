package com.hasakiii.rfid.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/11/22 23:59
 * @Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gate {
    private Integer g_id;
    private String teamName;
}
