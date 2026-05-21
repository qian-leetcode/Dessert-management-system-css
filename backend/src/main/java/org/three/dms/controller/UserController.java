package org.three.dms.controller;

import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.FlashMapManager;
import org.three.dms.entity.User;
import org.three.dms.service.UserService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> list(){
        log.info("调用/api/user/list");
        return userService.list();
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String,String> user){
        String username = user.get("username");
        String password = user.get("password");
        log.info("{}尝试登录",username);
        List<User> temp_user_list = userService.list();
        boolean val = false;
        for (User u : temp_user_list){
            if (u.getUsername().equals(username) && u.getPassword().equals(password)){
                val = true;
                break;
            }
        }
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

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String,String> user){

        String username = user.get("username");
        String password = user.get("password");
        String encrypted_password = BCrypt.hashpw(password,BCrypt.gensalt());
//        userService
//        username, password,re_password, name , gender , phone , position, hire_date
        User temp_user = new User();
        temp_user.setName(user.get("name"));
        temp_user.setGender(user.get("gender"));
        temp_user.setPhone(user.get("phone"));
        temp_user.setUsername(user.get("username"));
        temp_user.setPassword(encrypted_password);
        temp_user.setPosition(user.get("position"));
        temp_user.setActive(false);
        temp_user.setHire_date(LocalDate.parse(user.get("hire_date")));
        temp_user.setShift(user.get("shift"));
        int pos = userService.insert_user(temp_user);
        Map<String,Object> res = new HashMap<>();
        if(pos == 1){
            res.put("code",200);
//            data.put();
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

    @GetMapping("/username")
    public List<String> get_username(){ return userService.get_username(); }
}
