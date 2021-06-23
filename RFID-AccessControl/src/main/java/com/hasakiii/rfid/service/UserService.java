package com.hasakiii.rfid.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hasakiii.rfid.entity.IoLog;
import com.hasakiii.rfid.entity.Relation;
import com.hasakiii.rfid.entity.User;
import com.hasakiii.rfid.mapper.IoLogMapper;
import com.hasakiii.rfid.mapper.RelationMapper;
import com.hasakiii.rfid.mapper.UserMapper;
import com.hasakiii.rfid.result.ResultFailure;
import com.hasakiii.rfid.result.ResultModel;
import com.hasakiii.rfid.result.ResultSuccess;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/11/23 12:21
 * @Description:
 **/
@Service
public class UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    RelationMapper relationMapper;
    @Resource
    IoLogMapper ioLogMapper;

    /**
     * 登录
     *
     * @param stu_id
     * @param password
     * @return
     */
    public Map<String, Object> login(String stu_id, String password) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //查询学号和密码符合条件的记录
        queryWrapper.select("name")
                .eq("stu_id", stu_id)
                .eq("password", password);
//                .eq("activity", true);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return null;
        } else {
            map.put("name", user.getName());
            return map;
        }
    }

    /**
     * 挂失
     *
     * @param stu_id
     * @return
     */
    public boolean loss(String stu_id) {
        //将用户取消激活
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("stu_id", stu_id)
                .set("activity", false);
        int rows = userMapper.update(null, updateWrapper);

        //获取用户门卡，并取消激活
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("stu_id", stu_id);
        User user = userMapper.selectOne(userQueryWrapper);
        String u_id = user.getUId();

        UpdateWrapper<Relation> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.eq("u_id", u_id)
                .set("activity", false);
        relationMapper.update(null, updateWrapper1);
        //改变行不为0则挂失成功
        return rows != 0;
    }

    /**
     * 修改密码
     *
     * @param password
     * @param stu_id
     * @return
     */
    public boolean revise(String stu_id, String password) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("stu_id", stu_id)
                .set("password", password);
        Integer rows = userMapper.update(null, updateWrapper);
        if (rows != 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 添加头像
     *
     * @param file
     * @return
     */
    public ResultModel addPic(MultipartFile file, String stu_id, String filePath) {

        //获取文件名
        String fileName = file.getOriginalFilename();
        //上传文件名改为文件名加时间,用以唯一标记
        String newName = System.currentTimeMillis() + fileName;

        //新建文件，来判断目录是否存在
        File file1 = new File(filePath, fileName);
        if (!file1.getParentFile().exists()) {
            //不存在目录就新建
            file1.getParentFile().mkdirs();
        }
        //写入文件
        try {
            file.transferTo(new File(filePath + File.separator + newName));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultFailure("添加失败");
        }

        //设置访问路径
        String path = "/img/" + newName;
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("stu_id", stu_id)
                .set("avatar", path);

        int rows = userMapper.update(null, updateWrapper);

        if (rows != 1) {
            return new ResultFailure("添加失败");
        } else {
            return new ResultSuccess("添加成功");
        }
    }

    public User userInfo(String stu_id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_id", stu_id)
        .select("stu_id","name","major","activity","avatar");
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 本地后台请求是否存在用户
     *
     * @param g_id
     * @param u_id
     * @return
     */
    public boolean isExist(String g_id, String u_id) {
        QueryWrapper<Relation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("g_id", g_id)
                .eq("u_id", u_id)
                .eq("activity", true);
        int row = relationMapper.selectCount(queryWrapper);
        if (row != 0) {
            //存在则返回true,并添加记录
            ioLog(g_id, u_id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取无效用户id
     *
     * @param g_id
     * @return
     */
    public List<String> getU_id(String g_id) {
        return relationMapper.getU_id(g_id, false);
    }

    /**
     * 记录出入信息
     *
     * @param g_id
     * @param u_id
     * @return
     */
    public boolean ioLog(String g_id, String u_id) {
        IoLog ioLog = new IoLog(g_id, u_id, new Date());
        System.out.println("******************************\n" + ioLog.toString());
        int row = ioLogMapper.insert(ioLog);
        return row != 0;
    }


    /**
     * 用户出入信息
     *
     * @param stu_id
     * @return
     */
    public List<Map<String, Object>> ioInfo(Integer day, String stu_id) {

        String u_id = userMapper.getUID(stu_id);

        return ioLogMapper.ioInfo(day, u_id);
    }

    /**
     * 返回排行数据
     *
     * @return
     */
    public List<Map<String, Object>> rank(Integer day) {
        return userMapper.rank(day);
    }


}
