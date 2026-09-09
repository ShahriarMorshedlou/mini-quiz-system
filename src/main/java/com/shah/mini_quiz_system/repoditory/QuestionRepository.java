package com.shah.mini_quiz_system.repoditory;

import com.shah.mini_quiz_system.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
}
