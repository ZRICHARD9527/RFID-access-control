package com.hasakiii.rfid.controller;

import com.alibaba.fastjson.JSONObject;
import com.hasakiii.rfid.entity.User;
import com.hasakiii.rfid.result.ResultFailure;
import com.hasakiii.rfid.result.ResultModel;
import com.hasakiii.rfid.result.ResultSuccess;
import com.hasakiii.rfid.service.AdminService;
import com.hasakiii.rfid.service.UserService;
import com.hasakiii.rfid.util.JwtUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/11/23 12:10
 * @Description:
 **/
@Controller
@ResponseBody
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminService adminService;
    @Resource
    UserService userService;

    @PostMapping("/login")
    public ResultModel login(@RequestBody JSONObject jsonObject) {
        String name = String.valueOf(jsonObject.get("name"));
        String password = String.valueOf(jsonObject.get("password"));
        Integer a_id = adminService.login(name, password);

        if (a_id == null) {
            return new ResultFailure("登录失败");
        } else {
            Map<String, String> payload = new HashMap<>();
            payload.put("a_id", String.valueOf(a_id));
            payload.put("a_name", name);
            String token = JwtUtils.getToken(payload);
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            return new ResultSuccess("登录成功", data);
        }

    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @GetMapping("/getUsers")
    public ResultModel getAllUser(@RequestParam(value = "page") Integer page, @RequestParam(value = "size") Integer size) {
        Map<String, Object> data = new HashMap<>();
        data.put("users", adminService.getAllUser(page, size));
        data.put("total", adminService.getTotal());
        return new ResultSuccess("", data);
    }

    /**
     * 搜索用户
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/search")
    public ResultModel search(@RequestBody JSONObject jsonObject) {
        String name = String.valueOf(jsonObject.get("name"));
        return new ResultSuccess("", adminService.search(name));
    }

    /**
     * 修改用户
     */

    @PostMapping("update")
    public ResultModel upDate(@RequestBody JSONObject jsonObject) {
        String stu_id = String.valueOf(jsonObject.get("stu_id"));
        String name = String.valueOf(jsonObject.get("name"));
        String major = String.valueOf(jsonObject.get("major"));
        Boolean activity = (Boolean) jsonObject.get("activity");
        if (adminService.update(stu_id, name, major, activity)) {
            return new ResultSuccess("");
        } else {
            return new ResultFailure("");
        }

    }

    /**
     * 添加用户
     */
    @PostMapping("/addUser")
    public ResultModel addUser(@RequestBody JSONObject jsonObject) {

        String u_id = String.valueOf(jsonObject.get("u_id"));
        String stu_id = String.valueOf(jsonObject.get("stu_id"));
        String name = String.valueOf(jsonObject.get("name"));
        String major = String.valueOf(jsonObject.get("major"));
        String g_id = String.valueOf(jsonObject.get("g_id"));

        User user = new User(u_id, stu_id, name, major);

        if (adminService.addUser(user, g_id)) {
            return new ResultSuccess("添加成功");
        } else {
            return new ResultFailure("添加失败");
        }

    }

    /**
     * 删除
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/delete")
    public ResultModel delete(@RequestBody JSONObject jsonObject) {
        String stu_id = String.valueOf(jsonObject.get("stu_id"));
        if (adminService.delete(stu_id)) {
            return new ResultSuccess("删除成功");
        } else {
            return new ResultFailure("删除失败");
        }
    }

    /**
     * 获取管理记录
     *
     * @return
     */
    @GetMapping("/getLog")
    public ResultModel getManageLog() {
        Object data = adminService.getManageLog();
        return new ResultSuccess("", data);
    }

    /**
     * 一段时间内的出行排行
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/rank")
    public ResultModel rank(@RequestBody JSONObject jsonObject) {
        Object data = userService.rank(Integer.parseInt(String.valueOf(jsonObject.get("day"))));
        return new ResultSuccess("", data);
    }


}
