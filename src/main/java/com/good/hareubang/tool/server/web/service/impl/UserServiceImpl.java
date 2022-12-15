package com.good.hareubang.tool.server.web.service.impl;


import com.good.hareubang.tool.server.domain.FoodDetail;
import com.good.hareubang.tool.server.domain.User;
import com.good.hareubang.tool.server.web.repository.FoodDetailRepository;
import com.good.hareubang.tool.server.web.repository.UserRepository;
import com.good.hareubang.tool.server.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FoodDetailRepository foodDetailRepository;

    private final ServletContext servletContext;

    @Override
    public void test() {

    }

    @Override
    public List<User> getUserList(String l) {
        if (l == null) {
            return userRepository.findAll();
        } else {
            long userNum = Long.valueOf(l);
            Optional<User> user = userRepository.findById(userNum);
            List<User> userList;
            if (user.isPresent()) {
                userList = userRepository.findAllById(user.get());
            } else {
                userList = userRepository.findAll();
            }

            return userList;
        }
    }

    @Override
    public List<FoodDetail> getUserDetailList(String user) {
        if (user == null) {
            return foodDetailRepository.findAll();
        } else {
            long userNum = Long.valueOf(user);
            Optional<User> userOptional = userRepository.findById(userNum);
            List<FoodDetail> userList;
            if (userOptional.isPresent()) {
                userList = foodDetailRepository.findAllByUser(userOptional.get());
            } else {
                userList = foodDetailRepository.findAll();
            }

            return userList;
        }
    }

    @Override
    public FoodDetail getUserDetailOne(String id) {
        long userNum = Long.valueOf(id);
        Optional<FoodDetail> userDetail = foodDetailRepository.findById(userNum);
        return userDetail.get();
    }

    @Override
    public User select(String userName, String phone) {
        User user = userRepository.findByUserNameAndPhone(userName, phone);
        if (user == null) {
            user = User.builder()
                    .userName(userName)
                    .phone(phone)
                    .build();
            userRepository.save(user);
            user = userRepository.findByUserNameAndPhone(user.getUserName(), user.getPhone());
        }
        return user;
    }

    @Override
    public List<FoodDetail> getNotMine(String userNum) {
        if (userNum == null) {
            List<FoodDetail> foodList = foodDetailRepository.findAll();
            return foodList;
        } else {
            Optional<User> user = userRepository.findById(Long.valueOf(userNum));
            List<FoodDetail> foodList = foodDetailRepository.findAll();
            List<FoodDetail> foodListNotMine = foodList.stream().filter(f -> !f.getUser().equals(user.get())).collect(Collectors.toList());
            return foodListNotMine;
        }
    }

    @Override
    public List<FoodDetail> getAll() {
        List<FoodDetail> foodList = foodDetailRepository.findAll(Sort.by(Sort.Direction.DESC, "createTime"));
        return foodList;
    }

    @Override
    public List<FoodDetail> expiration(String userNum) {
        List<FoodDetail> foodList = foodDetailRepository.findAll(Sort.by(Sort.Direction.DESC, "expirationTime"));
        return foodList;
    }

    @Override
    public List<FoodDetail> location(String userNum, String lati, String longti) {

        Optional<User> user = userRepository.findById(Long.valueOf(userNum));
        List<FoodDetail> foodList = foodDetailRepository.findAll();
        List<FoodDetail> foodListNotMine = foodList.stream().filter(f -> !f.getUser().equals(user.get())).collect(Collectors.toList());
        for (int i = 0; i < foodListNotMine.size(); i++) {
            double km = getDistanceBetweenPointsNew(Double.valueOf(lati), Double.valueOf(longti),
                    foodListNotMine.get(i).getLati(), foodListNotMine.get(i).getLongti());
            foodListNotMine.get(i).setMet(String.valueOf(km));
        }
        List<FoodDetail> foodListNotMineUnderKm = foodListNotMine.stream().filter(f -> Double.valueOf(f.getMet()) <= 1000).collect(Collectors.toList());

        return foodListNotMineUnderKm;
    }

    @Override
    public FoodDetail updateFoodDetail(String id) {
        Optional<FoodDetail> foodDetail = foodDetailRepository.findById(Long.valueOf(id));
        FoodDetail foodDetail1 = foodDetail.get();
        foodDetail1.setDoneCk(true);
        foodDetailRepository.save(foodDetail1);
        return foodDetail1;
    }


    public double getDistanceBetweenPointsNew(double latitude1, double longitude1, double latitude2, double longitude2) {
        double theta = longitude1 - longitude2;
        double distance = 60 * 1.1515 * (180 / Math.PI) * Math.acos(
                Math.sin(latitude1 * (Math.PI / 180)) * Math.sin(latitude2 * (Math.PI / 180)) +
                        Math.cos(latitude1 * (Math.PI / 180)) * Math.cos(latitude2 * (Math.PI / 180)) * Math.cos(theta * (Math.PI / 180))
        );
        double result = Math.round(distance * 1.609344 * 1000);
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(result)).setScale(0, RoundingMode.HALF_DOWN);
        bigDecimal = bigDecimal.abs();
        return bigDecimal.doubleValue();
    }

}
