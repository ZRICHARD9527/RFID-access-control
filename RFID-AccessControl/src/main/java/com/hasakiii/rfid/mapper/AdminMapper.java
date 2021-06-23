package com.hasakiii.rfid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hasakiii.rfid.entity.Admin;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select stu_id as stuId, name, major, avatar,activity from `user` limit #{start} ,#{end}")
    List<Map<String, Object>> getUsers(Integer start, Integer end);
}
