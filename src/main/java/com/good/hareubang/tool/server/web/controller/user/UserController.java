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

    //유저 목록
    @ResponseBody
    @GetMapping("/user/list")
    public HashMap<String, Object> userList(HttpServletRequest request) {
        String userNum =  request.getParameter("user");
        HashMap<String, Object> responseHash = new HashMap<>();
        List<User> userList = userService.getUserList(userNum);
        responseHash.put("userList", userList);
        return responseHash;
    }

    //유저 디테일 리스트
    @ResponseBody
    @GetMapping("/user/detail-list")
    public HashMap<String, Object> userDetailList(HttpServletRequest request) {
        String userNum =  request.getParameter("user");
        HashMap<String, Object> responseHash = new HashMap<>();
        List<UserDetail> userDetailList = userService.getUserDetailList(userNum);
        responseHash.put("userDetailList", userDetailList);
        return responseHash;
    }

    //유저 디테일
    @ResponseBody
    @GetMapping("/user/detail-one")
    public HashMap<String, Object> userDetailOne(HttpServletRequest request) {
        String id =  request.getParameter("id");
        HashMap<String, Object> responseHash = new HashMap<>();
        UserDetail userDetail = userService.getUserDetailOne(id);
        responseHash.put("userDetail", userDetail);
        return responseHash;
    }

    @ResponseBody
    @GetMapping("/user/select")
    public HashMap<String, Object> userSelect(HttpServletRequest request) {
        String userName =  request.getParameter("userName");
        String phone = request.getParameter("phone");
        String lati = request.getParameter("lati");
        String longti = request.getParameter("longti");
        User user = userService.select(userName,phone,lati,longti);
        HashMap<String, Object> responseHash = new HashMap<>();
        responseHash.put("userDetail", user);

        return responseHash;
    }

}
