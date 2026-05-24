package org.three.dms.controller;

import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.three.dms.common.UserDataInfo;
import org.three.dms.entity.User;
import org.three.dms.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.min;

@Slf4j
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    // 获取用户信息列表
    @GetMapping("/list")
    public List<User> list(){
        log.info("调用/api/user/list");
        return userService.list();
    }

    // 账号登录
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String,String> user){
        String username = user.get("username");
        String password = user.get("password");
        log.info("{}尝试登录",username);
        // 获取加密密码
        String t_password = userService.get_password(username);
        boolean val = BCrypt.checkpw(password,t_password);
        Map<String,Object> res = new HashMap<>();
        Map<String,Object> data = new HashMap<>();

        if(val){
            String token = java.util.UUID.randomUUID().toString().replace("-", "");
            res.put("code",200);
            data.put("token",token);
            res.put("data",data);
            res.put("msg" ,"登录成功");
            log.info("{}登录成功",username);
        }
        else {
            res.put("code",500);
            res.put("msg","账号或密码错误");
            log.info("{}登录失败",username);
        }
        return res;
    }

    // 进行账号注册
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String,String> user){

        String username = user.get("username");
        String password = user.get("password");
        String encrypted_password = BCrypt.hashpw(password,BCrypt.gensalt());

        User temp_user = new User();
        temp_user.setName(user.get("name"));
        temp_user.setGender(user.get("gender"));
        temp_user.setPhone(user.get("phone"));
        temp_user.setUsername(user.get("username"));
        temp_user.setPassword(encrypted_password);
        temp_user.setPosition(user.get("position"));
        temp_user.setHire_date(LocalDate.parse(user.get("hire_date")));
        temp_user.setShift(user.get("shift"));
        int pos = userService.insert_user(temp_user);
        Map<String,Object> res = new HashMap<>();
        if(pos == 1){
            res.put("code",200);
            res.put("msg", "用户添加成功");
            log.info("添加新用户{}",username);
        }
        else {
            res.put("code",500);
            res.put("msg" , "用户添加失败");
            log.info("{}添加失败",username);
        }
        return res;
    }

    // 获取数据库中的用户名
    @GetMapping("/username")
    public List<String> get_username(){ return userService.get_username(); }

    // 获取用户个人信息
    @PostMapping("/information")
    public UserDataInfo get_information(@RequestBody Map<String , String> mp){
        List<User> begin_user_list = userService.list();
        Integer page_size = Integer.valueOf(mp.get("page_size"));
        Integer page_num = Integer.valueOf(mp.get("page_num"));
        String name = mp.get("name");
        String gender = mp.get("gender");
        String phone = mp.get("phone");
        String username = mp.get("username");
        String position = mp.get("position");
        String hire_date = mp.get("hire_date");
        String shift = mp.get("shift");

        UserDataInfo res = new UserDataInfo();
        List<User> temp_user_list = new ArrayList<>();
        for (User begin_user : begin_user_list){
            if(begin_user.getName().contains(name) &&
                begin_user.getGender().contains(gender) &&
                    begin_user.getPhone().contains(phone) &&
                    begin_user.getUsername().contains(username) &&
                    begin_user.getPosition().contains(position) &&
                    begin_user.getHire_date().toString().contains(hire_date) &&
                    begin_user.getShift().contains(shift)
            ){
                temp_user_list.add(begin_user);
            }
        }
        res.setCode(200);
        res.setMsg("success");
        int total = temp_user_list.size();
        res.setTotal(total);
        for (int i = page_size *(page_num - 1); i < min((long) page_size *(page_num - 1) + page_size , total); i++) {
            res.add(temp_user_list.get(i));
        }
        return res;
    }

    // 删除用户
    @PostMapping("/delete")
    public Map<String, Object> deleteUser(@RequestBody Map<String, String> map) {
        Integer id = Integer.parseInt(map.get("id"));
        int result = userService.delete_user(id);

        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("code", 200);
            response.put("msg", "删除用户成功");
        } else {
            response.put("code", 500);
            response.put("msg", "删除用户失败");
        }
        return response;
    }

    // 修改用户（不修改主键）
    @PostMapping("/update")
    public Map<String, Object> updateUser(@RequestBody Map<String, String> map) {
        Integer id = Integer.parseInt(map.get("id"));
        String name = map.get("name");
        String gender = map.get("gender");
        String phone = map.get("phone");
        String username = map.get("username");
        String password = map.get("password");
        String position = map.get("position");
        Integer active = Integer.parseInt(map.get("active"));
        LocalDate hireDate = LocalDate.parse(map.get("hireDate"));
        String shift = map.get("shift");

        int result = userService.update_user(id, name, gender, phone, username, password, position, active, hireDate, shift);

        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("code", 200);
            response.put("msg", "修改用户成功");
        } else {
            response.put("code", 500);
            response.put("msg", "修改用户失败");
        }
        return response;
    }
}
