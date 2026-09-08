package com.shah.mini_quiz_system.repoditory;

import com.shah.mini_quiz_system.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository  extends JpaRepository<Quiz,Long> {
}
