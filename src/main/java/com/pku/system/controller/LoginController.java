package com.pku.system.controller;

import com.pku.system.model.User;
import com.pku.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value="注册登录注销",tags = {"注册登录注销API"},description = "描述信息")
@Controller
public class LoginController {
    //自动注入业务层的userService类
    @Autowired
    UserService userService;

    @ApiOperation(value = "注册", notes = "注册notes", produces = "application/json")
    @RequestMapping(value="/login",method = RequestMethod.GET)
    @ResponseBody
    public String login(User user, HttpServletRequest request){
        //调用login方法来验证是否是注册用户
        boolean loginType = userService.login(user.getUsername(),user.getPassword());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();
        if(loginType){
            //如果验证通过,则将用户信息传到前台
            request.setAttribute("user",user);
            request.getSession().setAttribute("user",user);
            jsonData.put("user",user);
            jsonData.put("judge","0");
        }else{
            jsonData.put("judge","-1");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 注销
     * @param request
     * @return
     */
    @ApiOperation(value = "注销", notes = "注销notes", produces = "application/json")
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
//        try {
//            //退出
//            SecurityUtils.getSubject().logout();
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
        request.getSession().removeAttribute("user");
        return "redirect:/login";
    }

}
