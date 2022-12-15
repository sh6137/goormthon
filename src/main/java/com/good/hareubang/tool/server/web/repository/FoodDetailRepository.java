package com.good.hareubang.tool.server.web.repository;

import com.good.hareubang.tool.server.domain.User;
import com.good.hareubang.tool.server.domain.FoodDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodDetailRepository extends JpaRepository<FoodDetail, Long> {
    List<FoodDetail> findAllByUser(User user);

}
