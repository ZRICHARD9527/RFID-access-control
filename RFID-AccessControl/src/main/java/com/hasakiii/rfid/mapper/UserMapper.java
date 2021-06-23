package com.hasakiii.rfid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hasakiii.rfid.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/11/23 11:13
 * @Description:
 **/

public interface UserMapper extends BaseMapper<User> {
    @Select("select u_id from `user` where stu_id=#{stu_id}")
    String getUID(String stu_id);

    @Select("SELECT\n" +
            "\tu.`name`,\n" +
            "\tio.u_id,\n" +
            "\tu.major,\n" +
            "\tu.stu_id,\n" +
            "\tu.avatar,\n" +
            "\tCOUNT( io.u_id ) number \n" +
            "FROM\n" +
            "\t`io_log` io\n" +
            "\tLEFT JOIN `user` u ON u.u_id = io.u_id \n" +
            "WHERE\n" +
            "\tio.time >= NOW() - INTERVAL #{day} DAY \n" +
            "GROUP BY\n" +
            "\tio.u_id \n" +
            "ORDER BY\n" +
            "\tnumber DESC ")
    List<Map<String, Object>> rank(Integer day);

}
