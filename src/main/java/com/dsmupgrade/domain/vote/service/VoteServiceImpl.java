package com.dsmupgrade.domain.vote.service;

import com.dsmupgrade.domain.student.domain.Student;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import com.dsmupgrade.domain.vote.dto.request.VoteRequest;
import com.dsmupgrade.domain.notification.domain.Notification;
import com.dsmupgrade.domain.notification.domain.NotificationRepository;
import com.dsmupgrade.domain.vote.dto.response.VoteResponse;
import com.dsmupgrade.global.error.exception.StudentNotAdminException;
import com.dsmupgrade.global.error.exception.StudentNotFoundException;
import com.dsmupgrade.global.error.exception.StudentNotRegisteredException;
import com.dsmupgrade.global.security.AuthenticationFacade;
import com.dsmupgrade.domain.vote.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final VoteContentRepository voteContentRepository;
    private final VoteDoRepository voteDoRepository;
    private final NotificationRepository notificationRepository;
    private final AuthenticationFacade authenticationFacade;
    private final StudentRepository studentRepository;

    @Override
    public void voteWrite(VoteRequest voteRequest) {
        String adminName = authenticationFacade.getUsername();
        Student admin = studentRepository.findByUsername(adminName).orElseThrow(
                () -> new StudentNotFoundException(adminName));
        if (admin.getIsAdmin()) throw new StudentNotAdminException(admin.getUsername());

        Vote vote = voteRepository.save(
                Vote.builder()
                        .isDead(false)
                        .isEven(voteRequest.isEven())
                        .count(voteRequest.getCount())
                        .title(voteRequest.getTitle())
                        .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .deadLine(voteRequest.getDeadLine())
                        .build()
        );
        System.out.println(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        for (int i = 0; i < voteRequest.getCount(); i++) {
            voteContentRepository.save(
                    VoteContent.builder()
                            .content(voteRequest.getContent()[i])
                            .vote(vote)
                            .build()
            );
        }
        notificationRepository.save(
                Notification.builder()
                        .isVote(true)
                        .detailId(vote.getId())
                        .build()
        );

        new Thread(() -> {
            long time = vote.getDeadLine().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli() - System.currentTimeMillis();
            try {
                if (time < 0) {
                    time = 0;
                }
                Thread.sleep(time);
                voteRepository.save(vote.deadIsDead());
            } catch (Exception exception) {
            }
        }).start();

    }

    @Override
    public void voteDo(Integer[] choice, Integer id) {
        String username = authenticationFacade.getUsername();
        Student student = studentRepository.findByUsername(username).orElseThrow(
                () -> new StudentNotFoundException(username));
        if (!student.getIsRegistered()) throw new StudentNotRegisteredException(username);

        if (voteDoRepository.existsByUsernameAndVoteId(username, id)) {
            for (VoteDo voteDo : voteDoRepository.findAllByUsernameAndVoteId(username, id)) {
                voteDoRepository.deleteById(voteDo.getId());
                voteDoRepository.delete(voteDo);
            }
        }
        for (Integer i : choice) {
            voteDoRepository.save(
                    VoteDo.builder()
                            .username(student.getName())
                            .voteId(id)
                            .voteContentId(choice[i - 1])
                            .build()
            );

        }
    }

    @Override
    public VoteResponse getVote(Integer id) {
        String username = authenticationFacade.getUsername();
        Student student = studentRepository.findByUsername(username).orElseThrow(
                () -> new StudentNotFoundException(username));

        if (student.getIsRegistered()) throw new StudentNotRegisteredException(student.getUsername());
        Notification notification = notificationRepository.findById(id).orElseThrow();
        Vote vote = voteRepository.findById(notification.getDetailId()).orElseThrow();
        String[] content = new String[vote.getCount()];
        int i = 0;
        for (VoteContent voteContent : voteContentRepository.findAllByVote(vote)) {
            content[i] = voteContent.getContent();
            i++;
        }
        return VoteResponse.of(vote, content);
    }

    @Override
    public void voteUpdate(VoteRequest voteRequest, Integer id) {
        String adminName = authenticationFacade.getUsername();
        Student admin = studentRepository.findByUsername(adminName).orElseThrow(
                () -> new StudentNotFoundException(adminName));
        if (admin.getIsAdmin()) throw new StudentNotAdminException(adminName);

        Notification notification = notificationRepository.findById(id).orElseThrow();
        Vote vote = voteRepository.findById(notification.getDetailId()).orElseThrow();

        voteRepository.save(vote.update(voteRequest.getTitle(), voteRequest.isEven(), voteRequest.getCount(),
                voteRequest.getDeadLine(), LocalDateTime.now()));
        int i = 0;
        for (VoteContent voteContent : voteContentRepository.findAllByVote(vote)) {
            voteContentRepository.save(voteContent.update(voteRequest.getContent()[i], vote));
            i++;
        }

    }
}
