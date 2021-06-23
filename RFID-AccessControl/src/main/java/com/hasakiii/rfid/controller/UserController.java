package com.hasakiii.rfid.controller;

import com.alibaba.fastjson.JSONObject;
import com.hasakiii.rfid.result.ResultFailure;
import com.hasakiii.rfid.result.ResultModel;
import com.hasakiii.rfid.result.ResultSuccess;
import com.hasakiii.rfid.service.UserService;
import com.hasakiii.rfid.util.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/11/23 12:10
 * @Description:
 **/
@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;
    @Resource
    HttpServletRequest request;
    @Value("${file.upload.path}")
    String filePath;


    /**
     * 用户登录
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/login")
    public ResultModel login(@RequestBody JSONObject jsonObject) {
        String stu_id = String.valueOf(jsonObject.get("stu_id"));
        String password = String.valueOf(jsonObject.get("password"));
        Map<String, Object> data = userService.login(stu_id, password);

        if (data == null) {
            return new ResultFailure("登录失败");
        } else {
            Map<String, String> payload = new HashMap<>();
            payload.put("stu_id", String.valueOf(stu_id));
            String token = JwtUtils.getToken(payload);
            data.put("token", token);
            return new ResultSuccess("登录成功", data);
        }
    }

    /**
     * 挂失
     *
     * @return
     */
    @PostMapping("/loss")
    public ResultModel loss() {
        boolean loss = userService.loss(String.valueOf(request.getAttribute("stu_id")));
        if (loss) {
            return new ResultSuccess("挂失成功");
        } else {
            return new ResultFailure("挂失失败,请联系管理员");
        }
    }

    /**
     * 修改密码
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/password")
    public ResultModel revise(@RequestBody JSONObject jsonObject) {
        String password = String.valueOf(jsonObject.get("password"));
        boolean revise = userService.revise(String.valueOf(request.getAttribute("stu_id")), password);
        if (revise) {
            return new ResultSuccess("修改成功");
        } else {
            return new ResultFailure("修改失败,请联系管理员");
        }
    }

    /**
     * 用户出勤信息
     * 最近2天
     *
     * @return
     */
    @PostMapping("/ioInfo")
    public ResultModel ioInfo(@RequestBody JSONObject jsonObject) {
        Integer day = Integer.valueOf(String.valueOf(jsonObject.get("day")));
        Object data = userService.ioInfo(day, String.valueOf(request.getAttribute("stu_id")));
        return new ResultSuccess("", data);
    }

    /**
     * 3天出勤数据排名
     *
     * @return
     */
    @PostMapping("/rank")
    public ResultModel rank(@RequestBody JSONObject jsonObject) {

        Object data = userService.rank(Integer.parseInt(String.valueOf(jsonObject.get("day"))));
        return new ResultSuccess("", data);
    }

    /**
     * 上传用户头像
     *
     * @param file
     * @return
     */
    @PostMapping("/addPic")
    public ResultModel addPicFile(@RequestParam("img") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResultFailure("emptyFile");
        }
        return userService.addPic(file, String.valueOf(request.getAttribute("stu_id")), filePath);
    }

    @GetMapping("/userInfo")
    public ResultModel userInfo() {
        String stu_id = String.valueOf(request.getAttribute("stu_id"));
        Object data = userService.userInfo(stu_id);
        return new ResultSuccess("", data);
    }

    /**
     * ****************************************************************************************************************************************
     */

    /**
     * 一级后台请求是否存在用户
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/isExist")
    public ResultModel isExist(@RequestBody JSONObject jsonObject) {
        String g_id = String.valueOf(jsonObject.get("g_id"));
        String u_id = String.valueOf(jsonObject.get("u_id"));

        if (userService.isExist(g_id, u_id)) {
            return new ResultSuccess("访问成功");
        } else {
            return new ResultFailure("访问失败");
        }
    }

    /**
     * 所有失效id
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/getInvalid")
    public ResultModel getInvalid(@RequestBody JSONObject jsonObject) {
        String g_id = String.valueOf(jsonObject.get("g_id"));
        Object Data = userService.getU_id(g_id);
        return new ResultSuccess("", Data);
    }

    /**
     * 记录出入信息
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/ioLog")
    public ResultModel ioLog(@RequestBody JSONObject jsonObject) {
        String g_id = String.valueOf(jsonObject.get("g_id"));
        String u_id = String.valueOf(jsonObject.get("u_id"));

        System.out.println("**************************************\n" + jsonObject.toString());
        if (userService.ioLog(g_id, u_id)) {
            return new ResultSuccess("访问成功");
        } else {
            return new ResultFailure("访问失败");
        }

    }

    @PostMapping("/test")
    public ResultModel test(@RequestBody JSONObject jsonObject) {
        System.out.println(jsonObject.toString());
        return new ResultSuccess("访问成功");
    }

}
