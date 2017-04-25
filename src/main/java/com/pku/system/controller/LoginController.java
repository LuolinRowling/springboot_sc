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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiangdongyu on 2017/4/25.
 */
@Api(value="注册登录注销",tags = {"注册登录注销API"},description = "描述信息")
@RestController
public class LoginController {
    //自动注入业务层的userService类
    @Autowired
    UserService userService;

    /**
     * 登录
     * @param user
     * @param request
     * @return
     */
    @ApiOperation(value = "注册", notes = "注册notes", produces = "application/json")
    @RequestMapping(value="/login", method= RequestMethod.GET)
    public @ResponseBody
    String login(User user, HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();//使用shiro
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();
        try {
            subject.login(token);
            User userComplete = userService.selectByName(user.getUsername());
            request.setAttribute("user",user);
            request.getSession().setAttribute("user",userComplete);
            request.getSession().setAttribute("message",NewWebSocket.wSocketMessageListCenter.size());
            jsonData.put("judge","0");
        }catch (Exception e){
            //这里将异常打印关闭是因为如果登录失败的话会自动抛异常
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
    @RequestMapping(value="/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/login";
    }

}
