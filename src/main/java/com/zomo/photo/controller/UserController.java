package com.zomo.photo.controller;

import com.zomo.photo.VO.UserVo;
import com.zomo.photo.common.Const;
import com.zomo.photo.common.ServiceResponse;
import com.zomo.photo.entity.User;
import com.zomo.photo.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * 登录功能
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    public ServiceResponse login(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 HttpSession session){
        if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            return ServiceResponse.createByErrorMessage("username or password can't be empty");
        }
        ServiceResponse response=userService.login(username,password);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return ServiceResponse.createBySuccessMessage("success");
    }

    /**
     * 登出功能
     * @param session
     * @return
     */

    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    public ServiceResponse logout(HttpSession session){
        UserVo userVo= (UserVo) session.getAttribute(Const.CURRENT_USER);
        if (userVo!=null){
            session.removeAttribute(Const.CURRENT_USER);
            return ServiceResponse.createBySuccessMessage("logout success");
        }
        return ServiceResponse.createByErrorMessage("server exception");
    }

    /**
     * 普通用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    public ServiceResponse register(User user){
        if (user==null){
            return ServiceResponse.createByErrorMessage("parameter error");
        }
        if (StringUtils.isEmpty(user.getUsername())){
            return ServiceResponse.createByErrorMessage("username can't be empty");
        }
        if (StringUtils.isEmpty(user.getPassword())){
            return ServiceResponse.createByErrorMessage("password can't be empty");
        }
        return userService.register(user);
    }

    /**
     * 经理注册
     * @param user
     * @return
     */
    @RequestMapping(value = "registerManager.do",method = RequestMethod.POST)
    public ServiceResponse registerManager(User user){
        if (user==null){
            return ServiceResponse.createByErrorMessage("parameter error");
        }
        if (StringUtils.isEmpty(user.getUsername())){
            return ServiceResponse.createByErrorMessage("username can't be empty");
        }
        if (StringUtils.isEmpty(user.getPassword())){
            return ServiceResponse.createByErrorMessage("password can't be empty");
        }
        return userService.registerManager(user);
    }

    /**
     * 获取登录用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info,do",method = RequestMethod.GET)
    public ServiceResponse getUserInfo(HttpSession session){
        UserVo userVo= (UserVo) session.getAttribute(Const.CURRENT_USER);
        if (userVo!=null){
            return ServiceResponse.createBySuccess(userVo);
        }
        return ServiceResponse.createByErrorMessage("not logged in,please try again");
    }

    @RequestMapping(value = "check_username",method = RequestMethod.POST)
    public ServiceResponse checkUsername(@RequestParam(value = "username") String username){
        return userService.checkedUsername(username);
    }
}
