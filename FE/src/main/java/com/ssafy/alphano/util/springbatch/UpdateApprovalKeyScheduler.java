package com.ssafy.alphano.util.springbatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.alphano.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class UpdateApprovalKeyScheduler {

    private final MemberService memberService;

//    @Scheduled(cron = "0 */1 * * * *")
    public void updateApprovalKey() throws JsonProcessingException, InterruptedException {
        memberService.updateAllAccessToken();
    }
}
