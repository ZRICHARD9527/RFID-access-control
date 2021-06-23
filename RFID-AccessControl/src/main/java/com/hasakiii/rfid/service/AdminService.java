package com.hasakiii.rfid.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hasakiii.rfid.entity.Admin;
import com.hasakiii.rfid.entity.ManageLog;
import com.hasakiii.rfid.entity.Relation;
import com.hasakiii.rfid.entity.User;
import com.hasakiii.rfid.mapper.AdminMapper;
import com.hasakiii.rfid.mapper.ManageLogMapper;
import com.hasakiii.rfid.mapper.RelationMapper;
import com.hasakiii.rfid.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/11/23 12:39
 * @Description:
 **/

@Service
public class AdminService {

    @Resource
    AdminMapper adminMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    RelationMapper relationMapper;
    @Resource
    ManageLogMapper manageLogMapper;
    @Resource
    HttpServletRequest request;

    /**
     * 管理员登录
     *
     * @param name
     * @param password
     * @return
     */
    public Integer login(String name, String password) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a_name", name)
                .eq("password", password)
                .select("a_id");
        Admin admin = adminMapper.selectOne(queryWrapper);
        if (admin != null) {
            return admin.getAId();
        } else {
            return null;
        }
    }


    /**
     * 获取所有用户
     *
     * @return
     */
    public List<Map<String, Object>> getAllUser(Integer page, Integer size) {
        return adminMapper.getUsers(page - 1, page * size);
    }

    /**
     * 获取人数
     *
     * @return
     */
    public Integer getTotal() {
        return userMapper.selectCount(null);
    }

    /**
     * 通过姓名搜索
     *
     * @param name
     * @return
     */
    public List<User> search(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        manageLog("查找", "查找内容：      " + name);
        return userMapper.selectList(queryWrapper);
    }

    /**
     * 更新
     *
     * @param stu_id
     * @param name
     * @param major
     * @param activity
     * @return
     */
    public boolean update(String stu_id, String name, String major, boolean activity) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("stu_id", stu_id)
                .set("name", name)
                .set("major", major)
                .set("activity", activity);
        int r = userMapper.update(null, updateWrapper);

        if (r != 0) {
            manageLog("更新用户", "用户 :      学号 : " + stu_id + "   姓名 : " + name);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 添加用户
     *
     * @param user
     * @param g_id
     * @return
     */
    public boolean addUser(User user, String g_id) {
        int r1 = userMapper.insert(user);
        int r2 = relationMapper.insert(new Relation(g_id, user.getUId()));
        if ((r1 + r2) == 2) {
            manageLog("添加用户", "用户 :      学号 : " + user.getStuId() + "   姓名 : " + user.getName());
        }
        return (r1 + r2) == 2;
    }

    /**
     * 删除用户
     *
     * @param stu_id
     * @return
     */
    public boolean delete(String stu_id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_id", stu_id)
                .select("name", "u_id");
        User user = userMapper.selectOne(queryWrapper);
        String name = user.getName();

        Map<String, Object> map = new HashMap<>();
        map.put("stu_id", stu_id);
        int r = userMapper.deleteByMap(map);

        //删除关系
        Map<String, Object> map1 = new HashMap<>();
        map1.put("u_id", user.getUId());
        relationMapper.deleteByMap(map1);
        if (r != 0) {
            manageLog("删除用户", "用户 :      学号 : " + stu_id + "   姓名 : " + name);
        }
        return r != 0;
    }

    /**
     * 插入管理记录
     *
     * @param event
     * @param obj
     * @return
     */
    public boolean manageLog(String event, String obj) {
        Integer aId = Integer.parseInt(request.getAttribute("a_id").toString());
        String aName = String.valueOf(request.getAttribute("a_name"));
        int r = manageLogMapper.insert(new ManageLog(aId, aName, event, obj));
        return r != 0;
    }

    /**
     * 获取管理日志
     *
     * @return
     */
    public List<ManageLog> getManageLog() {
        QueryWrapper<ManageLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return manageLogMapper.selectList(queryWrapper);
    }

    /**
     * 获取出行记录排行
     *
     * @param day 时间范围
     * @return
     */
    public List<Map<String, Object>> rank(Integer day) {
        return userMapper.rank(day);
    }


}
