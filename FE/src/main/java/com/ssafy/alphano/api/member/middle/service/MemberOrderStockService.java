package com.ssafy.alphano.api.member.middle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.alphano.api.member.middle.controller.dto.MemberOrderStockResponseDto;
import com.ssafy.alphano.api.member.middle.service.dto.CreateMemberOptionDto;
import com.ssafy.alphano.api.member.middle.service.request.MemberOrderStockServiceRequest;
import com.ssafy.alphano.api.member.service.query.MemberQueryService;
import com.ssafy.alphano.api.member.service.response.MemberPrivateInfoResponse;
import com.ssafy.alphano.api.redis.service.RedisService;
import com.ssafy.alphano.api.stock.service.query.response.StockIndicatorResponse;
import com.ssafy.alphano.api.stock.service.request.CreateStockDailyServiceRequest;
import com.ssafy.alphano.common.error.ErrorCode;
import com.ssafy.alphano.common.error.exception.EntityNotFoundException;
import com.ssafy.alphano.common.error.exception.NicknameIsFoundException;
import com.ssafy.alphano.common.error.exception.StockIsFoundException;
import com.ssafy.alphano.common.response.ApiData;
import com.ssafy.alphano.common.response.ApiResult;
import com.ssafy.alphano.domain.common.Log;
import com.ssafy.alphano.domain.common.repository.LogRepository;
import com.ssafy.alphano.domain.member.Enum.BuySell;
import com.ssafy.alphano.domain.member.Enum.IsOrdered;
import com.ssafy.alphano.domain.member.Enum.OptionInequality;
import com.ssafy.alphano.domain.member.entity.Member;
import com.ssafy.alphano.domain.member.entity.middle.MemberOption;
import com.ssafy.alphano.domain.member.entity.middle.MemberOrderStock;
import com.ssafy.alphano.domain.member.repository.MemberOptionRepository;
import com.ssafy.alphano.domain.member.repository.MemberOrderStockRepository;
import com.ssafy.alphano.domain.member.repository.MemberRepository;
import com.ssafy.alphano.domain.member.repository.query.MemberOrderStockQueryRepository;
import com.ssafy.alphano.domain.member.repository.query.MemberQueryRepository;
import com.ssafy.alphano.domain.stock.entity.Stock;
import com.ssafy.alphano.domain.stock.repository.StockRepository;
import com.ssafy.alphano.domain.stock.repository.query.StockDailyQueryRepository;
import com.ssafy.alphano.domain.stockoption.entity.StockOption;
import com.ssafy.alphano.domain.stockoption.repository.StockOptionRepository;
import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceCondition;
import com.ssafy.alphano.domain.stockrequiredoption.Enum.TargetPriceOptionInequality;
import com.ssafy.alphano.domain.stockrequiredoption.entity.StockRequiredOption;
import com.ssafy.alphano.domain.stockrequiredoption.repository.StockRequiredOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberOrderStockService {

    private final MemberRepository memberRepository;

    private final StockRepository stockRepository;

    private final LogRepository logRepository;

    private final StockOptionRepository stockOptionRepository;

    private final MemberOrderStockQueryRepository memberOrderStockQueryRepository;

    private final MemberOrderStockRepository memberOrderStockRepository;

    private final StockRequiredOptionRepository stockRequiredOptionRepository;

    private final MemberOptionRepository memberOptionRepository;

    private final StockDailyQueryRepository stockDailyQueryRepository;

    private final RedisService redisService;

    private final MemberQueryRepository memberQueryRepository;

    public MemberOrderStockResponseDto createMemberOrderStock(Long memberId, MemberOrderStockServiceRequest request) throws JsonProcessingException {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        Stock stock = stockRepository.findByStockName(request.getStockName()).orElseThrow(() ->
                new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        MemberOrderStock memberOrderStock = memberOrderStockRepository.findByMemberIdAndStockIdAndBuySell(memberId, stock.getId(), request.getBuySell());

        MemberOrderStock checkNickname = memberOrderStockRepository.findByMemberIdAndMemberOrderStockNickname(memberId, request.getMemberOrderStockNickname());

        // 이미 존재하는 닉네임일 때
        if (checkNickname != null) {
            throw new NicknameIsFoundException(ErrorCode.NICKNAME_IS_FOUND);
        }

        // 이미 존재하는 옵션(종목과 매수 또는 매도가 있을 때)
        if (memberOrderStock != null) {
            throw new StockIsFoundException(ErrorCode.STOCK_IS_FOUND);
        }

        return getMemberOrderStockResponseDto(memberId, request, member, stock);
    }

    public MemberOrderStockResponseDto updateMemberOrderStock(Long memberId, MemberOrderStockServiceRequest request) throws JsonProcessingException {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        Stock stock = stockRepository.findByStockName(request.getStockName()).orElseThrow(() ->
                new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        MemberOrderStock oldMemberOrderStock = memberOrderStockRepository.findByMemberIdAndStockIdAndBuySell(memberId, stock.getId(), request.getBuySell());

        // 등록된 주문내역이 있으면 -> log 테이블에 과거 내역 저장하고 기존 테이블에서 삭제해버리기
        if (oldMemberOrderStock != null) {
            List<Log> logs = convertToLog(oldMemberOrderStock);
            logRepository.saveAll(logs);

            MemberOrderStock findMemberOrderStock = getMemberOrderStockEntity(oldMemberOrderStock.getId());
            memberOrderStockRepository.delete(findMemberOrderStock);
        }

        return getMemberOrderStockResponseDto(memberId, request, member, stock);
    }

    /**
     * request로 받아온 정보로
     * 값을 추출하고
     * 주문 옵션 등록하기 위해
     * addNewMemberOrderStock을 호출하기
     */
    private MemberOrderStockResponseDto getMemberOrderStockResponseDto(Long memberId, MemberOrderStockServiceRequest request, Member member, Stock stock) throws JsonProcessingException {
        String memberOrderStockNickname = request.getMemberOrderStockNickname();

        BuySell buysell = request.getBuySell();

        IsOrdered isOrdered = request.getIsOrdered();

        LocalDateTime startTime = LocalDateTime.now();

        LocalDateTime endTime = request.getEndTime();

        int orderCount = request.getOrderCount();

        int targetPrice = request.getTargetPrice();

        TargetPriceCondition targetPriceCondition = request.getTargetPriceCondition();

        TargetPriceOptionInequality targetPriceOptionInequality = request.getTargetPriceOptionInequality();

        int targetOrderPrice = request.getTargetOrderPrice();

        List<CreateMemberOptionDto> memberOptions = request.getMemberOptions();

        return addNewMemberOrderStock(memberId, member, stock, memberOrderStockNickname, buysell, isOrdered, startTime, endTime, orderCount, targetPrice, targetPriceCondition, targetPriceOptionInequality, targetOrderPrice, memberOptions);
    }

    /**
     * 회원 주문 종목 삭제
     * @param memberId, stockName 삭제할 회원 Id, 종목 이름
     * @return 삭제된 주문 종목
     */
    public ApiResult removeMemberOrderStock(Long memberId, String memberOrderStockNickname) {

        MemberOrderStock memberOrderStock = memberOrderStockRepository.findByMemberIdAndMemberOrderStockNickname(memberId, memberOrderStockNickname);

        Long memberOrderStockId = memberOrderStock.getId();

        MemberOrderStock findMemberOrderStock = getMemberOrderStockEntity(memberOrderStockId);

        memberOrderStockRepository.delete(findMemberOrderStock);

        return ApiData.of("삭제되었습니다.");
    }

    /**
     * 주문 옵션 등록하기
     */
    private MemberOrderStockResponseDto addNewMemberOrderStock(Long memberId, Member member, Stock stock, String memberOrderStockNickname, BuySell buysell, IsOrdered isOrdered, LocalDateTime startTime, LocalDateTime endTime, int orderCount, int targetPrice, TargetPriceCondition targetPriceCondition, TargetPriceOptionInequality targetPriceOptionInequality, int targetOrderPrice, List<CreateMemberOptionDto> memberOptions) throws JsonProcessingException {
        addOrderStock(memberOrderStockNickname, member, stock, buysell, isOrdered, startTime, endTime, orderCount);

        MemberOrderStock memberOrderStock = memberOrderStockRepository.findByMemberIdAndMemberOrderStockNickname(memberId, memberOrderStockNickname);

        Long memberOrderStockId = memberOrderStock.getId();

        addStockRequiredOption(
                targetPrice, targetPriceCondition, targetPriceOptionInequality, targetOrderPrice, memberOrderStock
        );

        for (CreateMemberOptionDto memberOption : memberOptions) {
            String optionName = memberOption.getOptionName();
            StockOption stockOption = stockOptionRepository.findByOptionName(optionName).orElseThrow(() ->
                    new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        }

        boolean flag = true;
        for (CreateMemberOptionDto memberOptionItem : memberOptions) {
            StockOption stockOption = stockOptionRepository.findByOptionName(memberOptionItem.getOptionName())
                    .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

            StockIndicatorResponse stockIndicatorResponse = stockDailyQueryRepository.findTodayStockIndicatorsByStockCode(stock.getStockCode());
            if (memberOptionItem.getOptionInequality().equals(OptionInequality.LESS)) {
                if(isGreater(stockIndicatorResponse, memberOptionItem)){
                    System.out.println("값이 큽니다.");
                    flag = false;
                }
            } else if (memberOptionItem.getOptionInequality().equals(OptionInequality.GREATER)) {
                if(isLess(stockIndicatorResponse, memberOptionItem)){
                    System.out.println("값이 작습니다.");
                    flag = false;
                }
            }
            addMemberOption(memberOptionItem, memberOrderStock, stockOption);
        }
        if(flag){
            MemberPrivateInfoResponse memberInfo = memberQueryRepository.findMemberPrivateInfoByMemberId(memberId);

            List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) redisService.getList(stock.getStockCode());
            if(list == null){
                list = new ArrayList<>();
            }
            LinkedHashMap<String, Object> memberMap = new LinkedHashMap<>();
            memberMap.put("memberId", memberId);
            memberMap.put("targetPrice", targetPrice);
            if(buysell.equals(BuySell.B)) {
                memberMap.put("buySell", "VTTC0802U ");
            }else{
                memberMap.put("buySell", "VTTC0801U ");
            }
            memberMap.put("memberOrderStockNickname", memberOrderStockNickname);
            memberMap.put("targetPriceOptionInequality", targetPriceOptionInequality);
            memberMap.put("authorization", memberInfo.getAuthorization());
            memberMap.put("appkey", memberInfo.getAppkey());
            memberMap.put("appsecret", memberInfo.getAppsecret());
            memberMap.put("CANO", memberInfo.getCANO());
            memberMap.put("ACNT_PRDT_CD", memberInfo.getACNT_PRDT_CD());
            memberMap.put("PDNO", stock.getStockCode());
            if(targetPriceCondition.equals(TargetPriceCondition.T)){
                memberMap.put("ORD_DVSN", "00");
            }else{
                memberMap.put("ORD_DVSN", "01");
            }
            memberMap.put("ORD_QTY", orderCount);
            memberMap.put("ORD_UNPR", targetOrderPrice);

            list.add(memberMap);
            System.out.println("redis에 추가 완료!");
            redisService.addValueForTest(stock.getStockCode(), list);
            System.out.println(redisService.getList(stock.getStockCode()));
        }

        return memberOrderStockQueryRepository.findMemberOrderStockResponse(memberOrderStockId);
    }

    private boolean isLess(StockIndicatorResponse stockIndicatorResponse, CreateMemberOptionDto memberOption) {
        return memberOption.getOptionValue() > stockIndicatorResponse.getStockIndicator().get(memberOption.getOptionName());
    }

    private boolean isGreater(StockIndicatorResponse stockIndicatorResponse, CreateMemberOptionDto memberOption) {
        return memberOption.getOptionValue() < stockIndicatorResponse.getStockIndicator().get(memberOption.getOptionName());
    }

    /**
     * 주문 옵션 추가
     */
    private void addOrderStock(String memberOrderStockNickname, Member member, Stock stock, BuySell buySell, IsOrdered isOrdered,
                               LocalDateTime startTime, LocalDateTime endTime,
                               int orderCount) {
        MemberOrderStock memberOrderStock = MemberOrderStock.builder()
                .memberOrderStockNickname(memberOrderStockNickname)
                .member(member)
                .stock(stock)
                .buySell(buySell)
                .isOrdered(isOrdered)
                .startTime(startTime)
                .endTime(endTime)
                .orderCount(orderCount)
                .build();

        memberOrderStockRepository.save(memberOrderStock);
    }

    /**
     * 필수옵션 추가
     */
    private void addStockRequiredOption(int targetPrice, TargetPriceCondition condition,
                                        TargetPriceOptionInequality inequality, int targetOrderPrice,
                                        MemberOrderStock memberOrderStock) {
        StockRequiredOption stockRequiredOption = StockRequiredOption.builder()
                .targetPrice(targetPrice)
                .targetPriceCondition(condition)
                .targetPriceOptionInequality(inequality)
                .targetOrderPrice(targetOrderPrice)
                .memberOrderStock(memberOrderStock)
                .build();

        stockRequiredOptionRepository.save(stockRequiredOption);
    }

    /**
     * 선택 옵션 추가
     */
    private void addMemberOption(CreateMemberOptionDto memberOptionItem, MemberOrderStock memberOrderStock, StockOption stockOption) {
        MemberOption memberOption = MemberOption.builder()
                .optionValue(memberOptionItem.getOptionValue())
                .optionInequality(memberOptionItem.getOptionInequality())
                .memberOrderStock(memberOrderStock)
                .stockOption(stockOption)
                .build();

        memberOptionRepository.save(memberOption);
    }


    /**
     * 회원 주문 옵션 고유키(ID)로 주문 옵션 찾기
     */
    private MemberOrderStock getMemberOrderStockEntity(Long memberOrderStockId) {
        Optional<MemberOrderStock> findMemberOrderStock = memberOrderStockRepository.findById(memberOrderStockId);

        if (findMemberOrderStock.isEmpty()) {
            throw new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND);
        }
        return findMemberOrderStock.get();
    }

    /**
     * 주문 옵션 삭제 시 로그로 전송
     */
    public List<Log> convertToLog(MemberOrderStock memberOrderStock) {
        List<Log> logs = new ArrayList<>();

        for (MemberOption memberOption : memberOrderStock.getMemberOptions()) {
            Log log = Log.builder()
                    .memberId(memberOrderStock.getMember().getId())
                    .orderListId(memberOrderStock.getOrderList() != null ? memberOrderStock.getOrderList().getId() : null)
                    .stockRequiredOptionId(memberOrderStock.getStockRequiredOption() != null ? memberOrderStock.getStockRequiredOption().getId() : null)
                    .stockId(memberOrderStock.getStock().getId())
                    .memberOptionId(memberOption.getId())
                    .buySell(memberOrderStock.getBuySell())
                    .isOrdered(memberOrderStock.getIsOrdered())
                    .startTime(memberOrderStock.getStartTime())
                    .endTime(memberOrderStock.getEndTime())
                    .orderCount(memberOrderStock.getOrderCount())
                    .createdDate(memberOrderStock.getCreatedTime())
                    .updatedDate(memberOrderStock.getUpdatedTime())
                    .count(memberOrderStock.getCount())
                    .member_order_stock_id(memberOrderStock.getId())
                    .orderNumber(memberOrderStock.getOrderNumber())
                    .memberOrderStockNickname(memberOrderStock.getMemberOrderStockNickname())
                    .build();
            logs.add(log);
        }
        return logs;
    }

    @Transactional
    public void updateOrderCount(BuySell buySell, String orderNumber, int orderCount, int stockPrice) {
        if(!memberOrderStockRepository.existsByOrderNumber(orderNumber)){
            return;
        }
        if(memberOrderStockQueryRepository.updateOrderCount(buySell, orderNumber, orderCount, stockPrice)){
            memberOrderStockQueryRepository.updateOrderCondition(buySell, orderNumber);
        }

    }

    @Transactional
    public void createMemberOrderNumber(Long memberId, String memberOrderStockNickname, String buySell, String orderNumber) {
        if(buySell.equals("YTTC0802U")){
            memberOrderStockQueryRepository.updateOrderNumber(memberId, memberOrderStockNickname, BuySell.S, orderNumber);
        }else {
            memberOrderStockQueryRepository.updateOrderNumber(memberId, memberOrderStockNickname, BuySell.B, orderNumber);

        }
    }
}
