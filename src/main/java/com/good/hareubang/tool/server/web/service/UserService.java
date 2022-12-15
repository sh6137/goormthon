package com.good.hareubang.tool.server.web.service;

import com.good.hareubang.tool.server.domain.User;
import com.good.hareubang.tool.server.domain.FoodDetail;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void test();

    List<User> getUserList(String l);


    List<FoodDetail> getUserDetailList(String l);

    FoodDetail getUserDetailOne(String id);

    User select(String id, String phone);

    List<FoodDetail> getNotMine(String userNum);

    List<FoodDetail> getAll();

    List<FoodDetail> expiration(String userNum);
}
