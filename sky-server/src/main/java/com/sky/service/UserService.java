package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;


public interface UserService {
    User userlogin(UserLoginDTO userLoginDTO);


}
