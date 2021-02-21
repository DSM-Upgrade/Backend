package com.dsmupgrade.service.vote;

import com.dsmupgrade.domain.entity.vote.*;
import com.dsmupgrade.dto.request.VoteRequest;
import com.dsmupgrade.domain.entity.notification.Notification;
import com.dsmupgrade.domain.entity.notification.NotificationRepository;
import com.dsmupgrade.dto.response.VoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final VoteContentRepository voteContentRepository;
    private final VoteDoRepository voteDoRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public void voteWrite(VoteRequest voteRequest) {
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
//        Integer receiptCode = authenticationFacade.getReceiptCode();

        for (Integer i : choice) {
            voteDoRepository.save(
                    VoteDo.builder()
                            .username("")
                            .voteId(id)
                            .voteContentId(choice[i - 1])
                            .build()
            );
        }
    }

    @Override
    public VoteResponse getVote(Integer id) {
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
