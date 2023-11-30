package com.ssafy.alphano.domain.member.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.alphano.api.member.middle.controller.dto.MemberOrderStockResponseDto;
import com.ssafy.alphano.api.member.middle.service.dto.CreateMemberOptionDto;
import com.ssafy.alphano.api.member.middle.service.response.MemberOrderStockServiceResponse;
import com.ssafy.alphano.domain.member.Enum.BuySell;
import com.ssafy.alphano.domain.member.Enum.IsOrdered;
import com.ssafy.alphano.domain.member.entity.middle.QMemberOption;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.alphano.domain.member.entity.middle.QMemberOrderStock.memberOrderStock;

@Repository
@RequiredArgsConstructor
public class MemberOrderStockQueryRepository {

    private final JPAQueryFactory queryFactory;

    QMemberOption memberOption = QMemberOption.memberOption;

    public List<MemberOrderStockResponseDto> findMemberOrderStock(Long memberId) {
        List<MemberOrderStockResponseDto> memberOrderStockResponseDtoList =
                queryFactory
                        .select(Projections.bean(MemberOrderStockResponseDto.class,
                                memberOrderStock.id.as("memberOrderStockId"),
                                memberOrderStock.memberOrderStockNickname,
                                memberOrderStock.stock.stockName,
                                memberOrderStock.buySell,
                                memberOrderStock.isOrdered,
                                memberOrderStock.startTime,
                                memberOrderStock.endTime,
                                memberOrderStock.orderCount,
                                memberOrderStock.stockRequiredOption.targetPrice,
                                memberOrderStock.stockRequiredOption.targetPriceCondition,
                                memberOrderStock.stockRequiredOption.targetPriceOptionInequality,
                                memberOrderStock.stockRequiredOption.targetOrderPrice,
                                memberOrderStock.count
                        ))
                        .from(memberOrderStock)
                        .where(memberOrderStock.member.id.eq(memberId))
                        .orderBy(memberOrderStock.isOrdered.desc(), memberOrderStock.startTime.desc())
                        .fetch();
        if (!memberOrderStockResponseDtoList.isEmpty()) {
            for (MemberOrderStockResponseDto dto : memberOrderStockResponseDtoList) {
                Long memberOrderStockId = dto.getMemberOrderStockId();
                if(memberOrderStockId != null) {
                    List<CreateMemberOptionDto> memberOptions = queryFactory
                            .select(Projections.fields(CreateMemberOptionDto.class,
                                    memberOption.optionValue.as("optionValue"),
                                    memberOption.optionInequality.as("optionInequality"),
                                    memberOption.stockOption.optionName.as("optionName")
                            ))
                            .from(memberOption)
                            .where(memberOption.memberOrderStock.id.eq(memberOrderStockId))
                            .fetch();
                    dto.setMemberOptions(memberOptions);
                }
            }
        }

        return memberOrderStockResponseDtoList;
    }

    public List<MemberOrderStockServiceResponse> findMemberOrderStockNickname(Long memberId, String memberOrderStockNickname) {
        return queryFactory
                .select(Projections.bean(MemberOrderStockServiceResponse.class,
                        memberOrderStock.stock.stockName.as("stockName"),
                        memberOrderStock.memberOrderStockNickname.as("memberOrderStockNickname"),
                        memberOrderStock.buySell.as("buySell"),
                        memberOrderStock.isOrdered.as("isOrdered"),
                        memberOrderStock.startTime.as("startTime"),
                        memberOrderStock.endTime.as("endTime"),
                        memberOrderStock.orderCount.as("orderCount"),
                        memberOrderStock.createdTime.as("createdTime"),
                        memberOrderStock.updatedTime.as("updatedTime")
                ))
                .from(memberOrderStock)
                .where(memberOrderStock.member.id.eq(memberId), memberOrderStock.memberOrderStockNickname.eq(memberOrderStockNickname))
                .fetch();
    }

    public MemberOrderStockResponseDto findMemberOrderStockResponse(Long memberOrderStockId) {
        MemberOrderStockResponseDto memberOrderStockResponseDto =
                queryFactory
                    .select(Projections.bean(MemberOrderStockResponseDto.class,
                            memberOrderStock.memberOrderStockNickname,
                            memberOrderStock.stock.stockName,
                            memberOrderStock.buySell,
                            memberOrderStock.isOrdered,
                            memberOrderStock.startTime,
                            memberOrderStock.endTime,
                            memberOrderStock.orderCount,
                            memberOrderStock.stockRequiredOption.targetPrice,
                            memberOrderStock.stockRequiredOption.targetPriceCondition,
                            memberOrderStock.stockRequiredOption.targetPriceOptionInequality,
                            memberOrderStock.stockRequiredOption.targetOrderPrice
                    ))
                    .from(memberOrderStock)
                    .where(memberOrderStock.id.eq(memberOrderStockId))
                        .orderBy(memberOrderStock.isOrdered.desc(), memberOrderStock.startTime.desc())
                    .fetchOne();
        if (memberOrderStockResponseDto != null) {
            List<CreateMemberOptionDto> memberOptions = queryFactory
                    .select(Projections.fields(CreateMemberOptionDto.class,
                            memberOption.optionValue.as("optionValue"),
                            memberOption.optionInequality.as("optionInequality"),
                        memberOption.stockOption.optionName.as("optionName")
                    ))
                    .from(memberOption)
                    .where(memberOption.memberOrderStock.id.eq(memberOrderStockId))
                    .fetch();
            memberOrderStockResponseDto.setMemberOptions(memberOptions);
        }

        return memberOrderStockResponseDto;
    }

    public List<MemberOrderStockResponseDto> findAllMemberOrderStock(String stockCode) {
        List<MemberOrderStockResponseDto> memberOrderStockResponseDtoList =
                queryFactory
                        .select(Projections.bean(MemberOrderStockResponseDto.class,
                                memberOrderStock.id.as("memberOrderStockId"),
                                memberOrderStock.memberOrderStockNickname,
                                memberOrderStock.stock.stockName,
                                memberOrderStock.buySell,
                                memberOrderStock.isOrdered,
                                memberOrderStock.startTime,
                                memberOrderStock.endTime,
                                memberOrderStock.orderCount,
                                memberOrderStock.stockRequiredOption.targetPrice,
                                memberOrderStock.stockRequiredOption.targetPriceCondition,
                                memberOrderStock.stockRequiredOption.targetPriceOptionInequality,
                                memberOrderStock.stockRequiredOption.targetOrderPrice
                        ))
                        .from(memberOrderStock)
                        .where(memberOrderStock.stock.stockCode.eq(stockCode), memberOrderStock.isOrdered.eq(IsOrdered.WAIT))
                        .orderBy(memberOrderStock.startTime.asc())
                        .fetch();
        if (!memberOrderStockResponseDtoList.isEmpty()) {
            for (MemberOrderStockResponseDto dto : memberOrderStockResponseDtoList) {
                Long memberOrderStockId = dto.getMemberOrderStockId();
                if(memberOrderStockId != null) {
                    List<CreateMemberOptionDto> memberOptions = queryFactory
                            .select(Projections.fields(CreateMemberOptionDto.class,
                                    memberOption.optionValue.as("optionValue"),
                                    memberOption.optionInequality.as("optionInequality"),
                                    memberOption.stockOption.optionName.as("optionName")
                            ))
                            .from(memberOption)
                            .where(memberOption.memberOrderStock.id.eq(memberOrderStockId))
                            .fetch();
                    dto.setMemberOptions(memberOptions);
                }
            }
        }

        return memberOrderStockResponseDtoList;
    }

    /**
     * 
     * @param orderNumber
     * @param orderCount
     * @param stockPrice
     * orderNumber에 해당하는 member_order_stock의 count와 price를 param값으로 받은 만큼 더함
     */
    @Modifying
    public Boolean updateOrderCount(BuySell buySell, String orderNumber, int orderCount, int stockPrice) {
        queryFactory.update(memberOrderStock)
                .where(memberOrderStock.orderNumber.eq(orderNumber), memberOrderStock.buySell.eq(buySell))
                .set(memberOrderStock.count, memberOrderStock.count.add(orderCount))
                .set(memberOrderStock.price, memberOrderStock.price.add(stockPrice))
                .execute();

        BooleanExpression equalityExpression = memberOrderStock.orderCount.eq(memberOrderStock.count);


        Boolean result = queryFactory.select(equalityExpression)
                .from(memberOrderStock)
                .where(memberOrderStock.orderNumber.eq(orderNumber))
                .fetchOne();

        return result != null && result;


    }

    @Modifying
    public void updateOrderCondition(BuySell buySell, String orderNumber) {
        queryFactory.update(memberOrderStock)
                .where(memberOrderStock.orderNumber.eq(orderNumber), memberOrderStock.buySell.eq(buySell))
                .set(memberOrderStock.isOrdered, IsOrdered.SUCCESS)
                .execute();
    }

    @Modifying
    public void updateOrderNumber(Long memberId, String memberOrderStockNickname, BuySell buySell, String orderNumber) {
        queryFactory.update(memberOrderStock)
                .where(memberOrderStock.member.id.eq(memberId), memberOrderStock.buySell.eq(buySell), memberOrderStock.memberOrderStockNickname.eq(memberOrderStockNickname))
                .set(memberOrderStock.orderNumber, orderNumber)
                .execute();
    }
}
