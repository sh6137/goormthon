package com.good.hareubang.tool.server.web.service.impl;


import com.good.hareubang.tool.server.domain.User;
import com.good.hareubang.tool.server.domain.UserDetail;
import com.good.hareubang.tool.server.web.repository.UserDetailRepository;
import com.good.hareubang.tool.server.web.repository.UserRepository;
import com.good.hareubang.tool.server.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    @Override
    public void test() {

    }

    @Override
    public List<User> getUserList(String l) {
        if (l == null) {
            return userRepository.findAll();
        }else{
            long userNum = Long.valueOf(l);
            Optional<User> user = userRepository.findById(userNum);
            List<User> userList;
            if(user.isPresent()){
                userList = userRepository.findAllById(user.get());
            }else{
                userList = userRepository.findAll();
            }

            return userList;
        }
    }

    @Override
    public List<UserDetail> getUserDetailList(String user) {
        if (user == null) {
            return userDetailRepository.findAll();
        }else{
            long userNum = Long.valueOf(user);
            Optional<User> userOptional = userRepository.findById(userNum);
            List<UserDetail> userList;
            if(userOptional.isPresent()){
                userList = userDetailRepository.findAllByUser(userOptional.get());
            }else{
                userList = userDetailRepository.findAll();
            }

            return userList;
        }
    }
}
