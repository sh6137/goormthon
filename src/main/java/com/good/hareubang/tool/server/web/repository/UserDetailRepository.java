package com.good.hareubang.tool.server.web.repository;

import com.good.hareubang.tool.server.domain.User;
import com.good.hareubang.tool.server.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    List<UserDetail> findAllByUser(User user);
}
