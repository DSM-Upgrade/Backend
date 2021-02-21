package com.dsmupgrade.service.notification;

import com.dsmupgrade.domain.entity.notice.Notice;
import com.dsmupgrade.domain.entity.notice.NoticeRepository;
import com.dsmupgrade.domain.entity.notification.Notification;
import com.dsmupgrade.domain.entity.notification.NotificationRepository;
import com.dsmupgrade.domain.entity.vote.Vote;
import com.dsmupgrade.domain.entity.vote.VoteContent;
import com.dsmupgrade.domain.entity.vote.VoteContentRepository;
import com.dsmupgrade.domain.entity.vote.VoteRepository;
import com.dsmupgrade.dto.response.NotificationResponse;
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

    @Override
    public List<NotificationResponse> getNotification() {

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
