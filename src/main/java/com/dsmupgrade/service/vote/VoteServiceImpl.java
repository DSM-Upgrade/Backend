package com.dsmupgrade.service.vote;

import com.dsmupgrade.dto.request.VoteRequest;
import com.dsmupgrade.domain.entity.notification.Notification;
import com.dsmupgrade.domain.entity.notification.NotificationRepository;
import com.dsmupgrade.domain.entity.vote.Vote;
import com.dsmupgrade.domain.entity.vote.VoteContent;
import com.dsmupgrade.domain.entity.vote.VoteContentRepository;
import com.dsmupgrade.domain.entity.vote.VoteRepository;
import com.dsmupgrade.dto.response.VoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final VoteContentRepository voteContentRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public void voteWrite(VoteRequest voteRequest) {
        Vote vote = voteRepository.save(
                Vote.builder()
                        .isEven(voteRequest.isEven())
                        .count(voteRequest.getCount())
                        .title(voteRequest.getTitle())
                        .build()
        );
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
    }

    @Override
    public VoteResponse getVote(Integer id) {
        return null;
    }
}
