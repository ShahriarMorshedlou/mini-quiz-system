package com.shah.mini_quiz_system.repoditory;

import com.shah.mini_quiz_system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
}
