package com.zomo.photo.service.imp;

import com.zomo.photo.VO.UserVo;
import com.zomo.photo.common.Const;
import com.zomo.photo.common.ServiceResponse;
import com.zomo.photo.entity.User;
import com.zomo.photo.repository.UserRepository;
import com.zomo.photo.service.IUserService;
import com.zomo.photo.utils.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.spi.ProviderUtil;

@Service
public class UserServiceImp implements IUserService{
    @Autowired
    private UserRepository userRepository;


    @Override
    public ServiceResponse login(String username, String password) {
        String md5Password=MD5Util.MD5EncodeUtf8(password);
        User user =userRepository.findByUsernameAndPassword(username,md5Password);
        if (user==null){
            return ServiceResponse.createByErrorMessage("username or password error");
        }
        UserVo userVo=new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return ServiceResponse.createBySuccess(userVo);
    }

    @Override
    public ServiceResponse register(User user) {
        ServiceResponse response=checkedUsername(user.getUsername());
        if (!response.isSuccess()){
            return response;
        }
        String md5Password=MD5Util.MD5EncodeUtf8(user.getPassword());
        user.setPassword(md5Password);

        user.setRoleId(Const.Role.USER.getCode());

        User newUser=userRepository.save(user);
        if (newUser==null){
            return ServiceResponse.createByErrorMessage("register failed");
        }
        return ServiceResponse.createBySuccess(newUser,"success");
    }

    @Override
    public ServiceResponse checkedUsername(String username) {
        Integer count=userRepository.countByUsername(username);
        if (count==0){
            return ServiceResponse.createBySuccess();
        }
        return ServiceResponse.createByErrorMessage("username is exist");
    }

    @Override
    public ServiceResponse registerManager(User user) {
        ServiceResponse response=checkedUsername(user.getUsername());
        if (!response.isSuccess()){
            return response;
        }
        String md5Password=MD5Util.MD5EncodeUtf8(user.getPassword());
        user.setPassword(md5Password);

        user.setRoleId(Const.Role.MANAGER.getCode());

        User newUser=userRepository.save(user);
        if (newUser==null){
            return ServiceResponse.createByErrorMessage("register failed");
        }
        return ServiceResponse.createBySuccess(newUser,"success");
    }

}
