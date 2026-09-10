package com.shah.mini_quiz_system.service;

import com.shah.mini_quiz_system.domain.Quiz;
import com.shah.mini_quiz_system.domain.Submission;
import com.shah.mini_quiz_system.dto.request.SubmissionRequest;
import com.shah.mini_quiz_system.dto.response.SubmissionResponse;
import com.shah.mini_quiz_system.exception.QuizNotFoundException;
import com.shah.mini_quiz_system.mapper.SubmissionMapper;
import com.shah.mini_quiz_system.repoditory.QuizRepository;
import com.shah.mini_quiz_system.repoditory.SubmissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final QuizRepository quizRepository;
    private final SubmissionMapper submissionMapper;

    public SubmissionService(
            SubmissionRepository submissionRepository,
            QuizRepository quizRepository,
            SubmissionMapper submissionMapper
    ) {
        this.submissionRepository = submissionRepository;
        this.quizRepository = quizRepository;
        this.submissionMapper = submissionMapper;
    }

    @Transactional
    public SubmissionResponse createSubmission(SubmissionRequest request) {

        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() ->
                        new QuizNotFoundException(
                                "Not Found Quiz With Id: " + request.getQuizId()
                        ));

        Submission submission = new Submission();
        submission.setStartTime(LocalDateTime.now());
        submission.setQuiz(quiz);

        Submission savedSubmission = submissionRepository.save(submission);

        return submissionMapper.toResponse(savedSubmission);
    }

    public List<SubmissionResponse> getAllSubmissions() {

        List<Submission> submissionList = submissionRepository.findAll();

        return submissionMapper.toResponseList(submissionList);
    }
}
