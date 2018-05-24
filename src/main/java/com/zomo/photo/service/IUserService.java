package com.zomo.photo.service;

import com.zomo.photo.common.ServiceResponse;
import com.zomo.photo.entity.User;

public interface IUserService {

    ServiceResponse login(String username,String password);

    ServiceResponse register(User user);

    ServiceResponse checkedUsername(String username);

    ServiceResponse registerManager(User user);


}
