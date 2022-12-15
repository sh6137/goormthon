package com.good.hareubang.tool.server.web.controller.user;

import com.good.hareubang.tool.server.domain.FoodDetail;
import com.good.hareubang.tool.server.domain.User;
import com.good.hareubang.tool.server.web.repository.FoodDetailRepository;
import com.good.hareubang.tool.server.web.repository.UserRepository;
import com.good.hareubang.tool.server.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    private final FoodDetailRepository foodDetailRepository;

    //유저 목록
    @ResponseBody
    @GetMapping("/user/list")
    public HashMap<String, Object> userList(HttpServletRequest request) {
        String userNum = request.getParameter("user");
        HashMap<String, Object> responseHash = new HashMap<>();
        List<User> userList = userService.getUserList(userNum);
        responseHash.put("userList", userList);
        return responseHash;
    }

    //유저 디테일 리스트
    @ResponseBody
    @GetMapping("/user/food/detail-list")
    public HashMap<String, Object> foodDetailList(HttpServletRequest request) {
        String userNum = request.getParameter("user");
        HashMap<String, Object> responseHash = new HashMap<>();
        List<FoodDetail> foodDetailList = userService.getUserDetailList(userNum);
        List<FoodDetail> foodDetailListNotMine = userService.getNotMine(userNum);
        List<FoodDetail> foodDetailListAll = userService.getAll();
        responseHash.put("foodDetailList", foodDetailList);
        responseHash.put("foodDetailListNotMine", foodDetailListNotMine);
        responseHash.put("foodDetailListAll", foodDetailListAll);

        return responseHash;
    }

    @ResponseBody
    @GetMapping("/user/food/detail-list-sort")
    public HashMap<String, Object> foodDetailListSort(HttpServletRequest request) {
        String userNum = request.getParameter("user");
        String lati = request.getParameter("lati");
        String longti = request.getParameter("longti");

        HashMap<String, Object> responseHash = new HashMap<>();
        List<FoodDetail> foodDetailListNotMine = userService.getNotMine(userNum);
        List<FoodDetail> foodDetailExpiration = userService.expiration(userNum);
        List<FoodDetail> foodDetaillocation = userService.location(userNum, lati, longti);
        responseHash.put("foodDetailListNotMine", foodDetailListNotMine);
        responseHash.put("foodDetailExpiration", foodDetailExpiration);
        responseHash.put("foodDetaillocation", foodDetaillocation);

        return responseHash;
    }

    //유저 디테일
    @ResponseBody
    @GetMapping("/user/food/detail-one")
    public HashMap<String, Object> userDetailOne(HttpServletRequest request) {
        String id = request.getParameter("id");
        HashMap<String, Object> responseHash = new HashMap<>();
        FoodDetail foodDetail = userService.getUserDetailOne(id);
        responseHash.put("userDetail", foodDetail);
        return responseHash;
    }

    @ResponseBody
    @GetMapping("/user/food/detail-check")
    public HashMap<String, Object> userDetailCheck(HttpServletRequest request) {
        String id = request.getParameter("id");
        HashMap<String, Object> responseHash = new HashMap<>();
        FoodDetail foodDetail = userService.updateFoodDetail(id);
        responseHash.put("userDetail", foodDetail);
        return responseHash;
    }

    @ResponseBody
    @PostMapping("/user/select")
    public HashMap<String, Object> userSelect(@RequestBody User user) {
        String userName = user.getUserName();
        String phone = user.getPhone();
        User showUser = userService.select(userName, phone);
        HashMap<String, Object> responseHash = new HashMap<>();
        responseHash.put("user", showUser);
        return responseHash;
    }

    @ResponseBody
    @PostMapping("/user/add-food")
    public HashMap<String, Object> createBoard(
            @RequestParam("files") MultipartFile files,
            @RequestParam("id") String id,
            @RequestParam("item") String item,
            @RequestParam("talkUrl") String talkUrl,
            @RequestParam("lati") String lati,
            @RequestParam("longti") String longti,
            @RequestParam("expirationTime") String expirationTime,
            @RequestParam("buyTime") String buyTime
    ) throws Exception {
        HashMap<String, Object> responseHash = new HashMap<>();
        if (!files.isEmpty()) {
//            String fullPath = "/Users/sonhyeongho/Documents/GitHub/goormthon/images/" + files.getOriginalFilename();
            String fullPath = "/workspace/stoneserver/" + files.getOriginalFilename();
            files.transferTo(new File(fullPath));
            String str = expirationTime + " 00:00:00";
            String str2 = buyTime + " 00:00:00";
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
            LocalDateTime buydateTime = LocalDateTime.parse(str2, formatter);

            Optional<User> user = userRepository.findById(Long.valueOf(id));
            LocalDateTime localDateTime = LocalDateTime.now();
            FoodDetail foodDetail = FoodDetail.builder()
                    .user(user.get())
                    .item(item)
                    .talkUrl(talkUrl)
                    .createTime(localDateTime)
                    .expirationTime(dateTime)
                    .buyTime(buydateTime)
                    .lati(Double.valueOf(lati))
                    .longti(Double.valueOf(longti))
                    .savedPath(fullPath)
                    .build();
            foodDetailRepository.save(foodDetail);
            responseHash.put("foodDetail", foodDetail);
        }

        return responseHash;
    }


    @GetMapping(value = "workspace/stoneserver/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> userSearch(@PathVariable("imagename") String imagename) throws IOException {
//        InputStream imageStream = new FileInputStream("/Users/sonhyeongho/Documents/GitHub/goormthon/images/" + imagename);
        InputStream imageStream = new FileInputStream(imagename);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
    }


}
