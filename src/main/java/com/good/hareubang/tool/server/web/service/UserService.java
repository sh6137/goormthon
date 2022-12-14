package com.good.hareubang.tool.server.web.service;

import com.good.hareubang.tool.server.domain.User;
import com.good.hareubang.tool.server.domain.UserDetail;

import java.util.List;

public interface UserService {
    void test();

    List<User> getUserList(String l);


    List<UserDetail> getUserDetailList(String l);
}
