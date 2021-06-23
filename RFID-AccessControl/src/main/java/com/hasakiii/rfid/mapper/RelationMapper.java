package com.hasakiii.rfid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hasakiii.rfid.entity.Relation;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RelationMapper extends BaseMapper<Relation> {
    @Select("select u_id from `relation` where g_id=#{g_id} and activity =#{activity}")
    List<String> getU_id(String g_id, boolean activity);
}
