package com.good.hareubang.tool.server.web.controller.user;

import com.good.hareubang.tool.server.domain.User;
import com.good.hareubang.tool.server.domain.UserDetail;
import com.good.hareubang.tool.server.web.service.UserService;
import com.good.hareubang.tool.server.web.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/user/list")
    public HashMap<String, Object> userList(HttpServletRequest request) {
        String userNum =  request.getParameter("user");
        HashMap<String, Object> responseHash = new HashMap<>();
        List<User> userList = userService.getUserList(userNum);
        responseHash.put("userList", userList);
        return responseHash;
    }

    @ResponseBody
    @GetMapping("/user/detail-list")
    public HashMap<String, Object> userDetailList(HttpServletRequest request) {
        String userNum =  request.getParameter("user");
        HashMap<String, Object> responseHash = new HashMap<>();
        List<UserDetail> userDetailList = userService.getUserDetailList(userNum);
        responseHash.put("userDetailList", userDetailList);
        return responseHash;
    }
}
