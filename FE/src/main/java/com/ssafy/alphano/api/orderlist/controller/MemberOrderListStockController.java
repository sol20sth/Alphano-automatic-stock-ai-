package com.ssafy.alphano.api.orderlist.controller;

import com.ssafy.alphano.domain.member.Enum.BuySell;
import com.ssafy.alphano.domain.member.Enum.IsOrdered;
import com.ssafy.alphano.domain.member.entity.middle.MemberOrderStock;
import com.ssafy.alphano.domain.member.repository.MemberOrderStockRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class MemberOrderListStockController {

    private final MemberOrderStockRepository memberOrderStockRepository;

    @GetMapping("/memberstock")
    @Operation(summary = "주식 주문", description = "주식 매수주문")
    public void getItem() {
        memberOrderStockRepository.save(MemberOrderStock.builder().buySell(BuySell.B).isOrdered(IsOrdered.WAIT).startTime(LocalDateTime.now()).endTime(LocalDateTime.now()).orderCount(10).build());
    }

}
