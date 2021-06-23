package com.hasakiii.rfid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hasakiii.rfid.entity.IoLog;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface IoLogMapper extends BaseMapper<IoLog> {
    @Select("SELECT\n" +
            "\tio.u_id,\n" +
            "\tu.`name`,\n" +
            "\tu.stu_id,\n" +
            "\tu.major,\n" +
            "\tio.time,\n" +
            "\tg.team_name \n" +
            "FROM\n" +
            "\t`io_log` io\n" +
            "\tLEFT JOIN `gate` g ON io.g_id = g.g_id\n" +
            "\tLEFT JOIN `user` u ON u.u_id = io.u_id \n" +
            "WHERE\n" +
            "\tio.time >= NOW() - INTERVAL #{day} DAY \n" +
            "\tAND io.u_id = #{u_id} \n" +
            "\tORDER BY io.time DESC"
    )
    List<Map<String, Object>> ioInfo(Integer day, String u_id);
}
