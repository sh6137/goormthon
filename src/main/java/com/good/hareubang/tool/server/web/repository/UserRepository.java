package com.good.hareubang.tool.server.web.repository;


import com.good.hareubang.tool.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllById(User user);

}
