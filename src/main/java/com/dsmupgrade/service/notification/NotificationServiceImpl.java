package com.dsmupgrade.service.notification;

import com.dsmupgrade.domain.entity.Notice;
import com.dsmupgrade.domain.entity.Student;
import com.dsmupgrade.domain.repository.NoticeRepository;
import com.dsmupgrade.domain.entity.Notification;
import com.dsmupgrade.domain.repository.NotificationRepository;
import com.dsmupgrade.domain.entity.vote.Vote;
import com.dsmupgrade.domain.entity.vote.VoteContentRepository;
import com.dsmupgrade.domain.entity.vote.VoteRepository;
import com.dsmupgrade.domain.repository.StudentRepository;
import com.dsmupgrade.dto.response.NotificationResponse;
import com.dsmupgrade.global.error.exception.StudentNotAdminException;
import com.dsmupgrade.global.error.exception.StudentNotFoundException;
import com.dsmupgrade.global.error.exception.StudentNotRegisteredException;
import com.dsmupgrade.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final VoteRepository voteRepository;
    private final NoticeRepository noticeRepository;
    private final VoteContentRepository voteContentRepository;
    private final AuthenticationFacade authenticationFacade;
    private final StudentRepository studentRepository;
    @Override
    public List<NotificationResponse> getNotification() {
        String username = authenticationFacade.getUsername();
        Student student = studentRepository.findByUsername(username).orElseThrow(
                () -> new StudentNotFoundException(username));
        if (!student.getIsRegistered()) throw new StudentNotRegisteredException(username);

        List<NotificationResponse> list = new ArrayList<>();
        for (Notification notification : notificationRepository.findAll()) {
            if (notification.isVote()) {
                Vote vote = voteRepository.findById(notification.getDetailId()).orElseThrow();
                list.add(
                        NotificationResponse.of(notification, vote.getTitle(), vote.getCreatedAt())
                );
            } else {
                Notice notice = noticeRepository.findById(notification.getDetailId()).orElseThrow();
                list.add(
                        NotificationResponse.of(notification, notice.getTitle(), notice.getCreatedAt())
                );
            }

        }

        return list;
    }

    @Override
    public void deleteNotification(Integer id) {
        String adminName = authenticationFacade.getUsername();
        Student admin = studentRepository.findByUsername(adminName).orElseThrow(
                () -> new StudentNotFoundException(adminName));
        if (!admin.getIsAdmin()) throw new StudentNotAdminException(adminName);

        Notification notification = notificationRepository.findById(id).orElseThrow();
        if (notification.isVote()) {

            Vote vote = voteRepository.findById(notification.getDetailId()).orElseThrow();
            voteContentRepository.deleteAllByVote(vote);
            voteRepository.delete(vote);
        } else {
            Notice notice = noticeRepository.findById(notification.getDetailId()).orElseThrow();
            noticeRepository.delete(notice);
        }
        notificationRepository.deleteById(id);
    }
}
