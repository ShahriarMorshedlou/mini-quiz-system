package com.shah.mini_quiz_system.service;

import com.shah.mini_quiz_system.domain.Answer;
import com.shah.mini_quiz_system.domain.Quiz;
import com.shah.mini_quiz_system.domain.QuizStatus;
import com.shah.mini_quiz_system.domain.Submission;
import com.shah.mini_quiz_system.dto.request.SubmissionRequest;
import com.shah.mini_quiz_system.dto.response.SubmissionResponse;
import com.shah.mini_quiz_system.exception.BusinessException;
import com.shah.mini_quiz_system.exception.QuizNotFoundException;
import com.shah.mini_quiz_system.exception.SubmissionNotFoundException;
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

    public SubmissionResponse getSubmissionById(Long id) {

        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() ->
                        new SubmissionNotFoundException(
                                "Not Found Submission With Id: " + id
                        ));

        return submissionMapper.toResponse(submission);
    }

    @Transactional
    public void deleteSubmissionById(Long id) {

        if (!submissionRepository.existsById(id)) {
            throw new SubmissionNotFoundException(
                    "Not Found Submission With Id: " + id
            );
        }

        submissionRepository.deleteById(id);
    }

    @Transactional
    public SubmissionResponse updateSubmission(
            SubmissionRequest request,
            Long id
    ) {

        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() ->
                        new SubmissionNotFoundException(
                                "Not Found Submission With Id: " + id
                        ));

        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() ->
                        new QuizNotFoundException(
                                "Not Found Quiz With Id: " + request.getQuizId()
                        ));

        submission.setQuiz(quiz);

        return submissionMapper.toResponse(submission);
    }

    public SubmissionResponse startQuiz(Long quizId) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz Not Found With Id: " + quizId));

        if (quiz.getStatus() != QuizStatus.PUBLISHED) {
            throw new BusinessException("Quiz Not Available");

        }

        Submission submission = new Submission();

        submission.setStartTime(LocalDateTime.now());
        submission.setQuiz(quiz);

        Submission savedSubmission = submissionRepository.save(submission);

        return submissionMapper.toResponse(savedSubmission);


    }


    public Integer calculateScore(Submission submission) {

        Integer score = 0;

        for (Answer answer : submission.getAnswers()) {

            if (answer.getChoice().isCorrect()) {

                score += answer.getQuestion().getScore();
            }
        }
        return score;

    }

    @Transactional
    public SubmissionResponse finishSubmission(Long submissionId) {

        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException("Submission Not Found With Id: " + submissionId));

        if (submission.getEndTime() != null) {
            throw new BusinessException("Submission has already been finished");
        }

        LocalDateTime deadline = submission.getStartTime()
                .plusMinutes(submission.getQuiz().getDuration());

        if (LocalDateTime.now().isAfter(deadline)) {
            throw new BusinessException("Submission time limit has been exceeded");
        }


        boolean hasUnansweredQuestion = submission.getQuiz().getQuestions()
                .stream()
                .anyMatch(question ->
                        submission.getAnswers().stream()
                                .noneMatch(answer ->
                                        answer.getQuestion().getId().equals(question.getId())
                                )
                );
        if (hasUnansweredQuestion) {
            throw new BusinessException("All Questions Must Be Answered Before Finishing");
        }

        submission.setEndTime(LocalDateTime.now());

        Integer score = calculateScore(submission);
        submission.setScore(score);

        return submissionMapper.toResponse(submission);


    }
}
