package org.three.dms.controller;

import jakarta.annotation.Resource;
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
    @Resource
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
        Map<String,Object> res = new HashMap<>();
        try {
            String username = user.get("username");
            String password = user.get("password");
            log.info("{}尝试登录",username);
            // 获取加密密码
            String t_password = userService.get_password(username);
            boolean val = BCrypt.checkpw(password,t_password);
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
        }
        catch (Exception e){
            res.put("code",500);
            res.put("msg","账号或密码错误");
            log.info("{}登录失败",e.getMessage());
        }
        return res;
    }

    // 进行账号注册
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String,String> user){
        Map<String,Object> res = new HashMap<>();
        try {
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
        }
        catch (Exception e){
            res.put("code",500);
            res.put("msg" , "用户添加失败");
            log.info("{}",e.getMessage());
        }
        return res;
    }

    // 获取数据库中的用户名
    @GetMapping("/username")
    public List<String> get_username(){ return userService.get_username(); }

    // 获取用户个人信息
    @PostMapping("/information")
    public UserDataInfo get_information(@RequestBody Map<String , String> mp){
        UserDataInfo res = new UserDataInfo();
        try {
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

            log.info("查询用户列表 - 入参: page_num={}, page_size={}, name={}, username={}", page_num, page_size, name, username);
            log.info("查询用户总条数: {}", begin_user_list.size());
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

            log.info("用户条件筛选后条数: {}", temp_user_list.size());

            res.setCode(200);
            res.setMsg("success");
            int total = temp_user_list.size();
            res.setTotal(total);
            for (int i = page_size *(page_num - 1); i < min((long) page_size *(page_num - 1) + page_size , total); i++) {
                res.add(temp_user_list.get(i));
            }
            log.info("用户列表查询成功, 返回条数: {}", total);
        }
        catch (Exception e){
            res.setCode(200);
            res.setMsg(e.getMessage());
            log.error("查询用户列表异常: {}", e.getMessage(), e);
        }
        return res;
    }

    // 删除用户
    @PostMapping("/delete")
    public Map<String, Object> deleteUser(Integer id) {
        Map<String, Object> res = new HashMap<>();
        try {
            log.info("删除用户 - 入参: id={}", id);
            int result = userService.delete_user(id);
            log.info("删除用户结果: result={}", result);
            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "删除用户成功");
                log.info("用户删除成功, id={}", id);
            } else {
                res.put("code", 500);
                res.put("msg", "删除用户失败");
                log.warn("用户删除失败, id={}", id);
            }
        }
        catch (Exception e){
            res.put("code", 500);
            res.put("msg", e.getMessage());
            log.error("删除用户异常: {}", e.getMessage(), e);
        }
        return res;
    }

    // 修改用户
    @PostMapping("/update")
    public Map<String, Object> updateUser(@RequestBody Map<String, String> map) {
        Map<String, Object> res = new HashMap<>();
        try {
            Integer id = Integer.parseInt(map.get("id"));
            String name = map.get("name");
            String gender = map.get("gender");
            String phone = map.get("phone");
            String username = map.get("username");
            String password = map.get("password");
            String encrypted_password = BCrypt.hashpw(password,BCrypt.gensalt());
            String position = map.get("position");
            LocalDate hireDate = LocalDate.parse(map.get("hire_date"));
            String shift = map.get("shift");

            log.info("更新用户 - 入参: id={}, name='{}', username={}", id, name, username);

            int result = userService.update_user(id, name, gender, phone, username, encrypted_password, position,hireDate, shift);
            log.info("更新用户结果: result={}", result);

            if (result > 0) {
                res.put("code", 200);
                res.put("msg", "修改用户成功");
                log.info("用户修改成功, id={}", id);
            } else {
                res.put("code", 500);
                res.put("msg", "修改用户失败");
                log.warn("用户修改失败, id={}", id);
            }
        }
        catch (Exception e){
            res.put("code", 500);
            res.put("msg", e.getMessage());
            log.error("修改用户异常: {}", e.getMessage(), e);
        }
        return res;
    }
}
