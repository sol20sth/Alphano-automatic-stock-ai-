package com.ssafy.alphano.api.stock.service;


//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvValidationException;
import com.ssafy.alphano.api.stock.service.query.StockQueryService;
import com.ssafy.alphano.api.stock.service.request.CreateStockDailyServiceRequest;
import com.ssafy.alphano.common.error.ErrorCode;
import com.ssafy.alphano.common.error.exception.EntityNotFoundException;
import com.ssafy.alphano.domain.stock.entity.Stock;
import com.ssafy.alphano.domain.stock.entity.StockDaily;
import com.ssafy.alphano.domain.stock.entity.StockDate;
import com.ssafy.alphano.domain.stock.repository.StockDailyRepository;
import com.ssafy.alphano.domain.stock.repository.StockDateRepository;
import com.ssafy.alphano.domain.stock.repository.StockRepository;
import com.ssafy.alphano.domain.stock.repository.query.StockDailyQueryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StockDailyService {
    private final StockDailyRepository stockDailyRepository;

    private final StockDailyQueryRepository stockDailyQueryRepository;

    private final StockRepository stockRepository;

    private final StockDateRepository stockDateRepository;

    private final StockQueryService stockQueryService;
    public void saveStockDaily(CreateStockDailyServiceRequest request, String requestDate) {

        Stock stock = stockRepository.findByStockName(request.getStockName()).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        StockDate findStockDate = stockDateRepository.findById(requestDate).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        StockDaily stockDaily = StockDaily.builder()
                .stockDate(findStockDate)
                .stock(stock)
                .startPrice(request.getStartPrice())
                .endPrice(request.getEndPrice())
                .maxPrice(request.getMaxPrice())
                .minPrice(request.getMinPrice())
                .build();

        stockDailyRepository.save(stockDaily);
    }

    @Transactional
    public void updateOption(String stockCode, Double rsi, Double macd, String stockDate) {
        Stock stock = stockRepository.findByStockCode(stockCode).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        stockDailyQueryRepository.updateOption(stock, rsi, macd, stockDate);

    }


//    public ApiResult pushData() {
//
//        List<StockResponse> stockList = stockQueryService.findByStockCode();
//
//        for (StockResponse stock: stockList) {
//            String getStockCode = stock.getStockCode();
//            String csvFilePath = "C:/Users/SSAFY/Downloads/results23/results23/" + getStockCode + ".csv";
//            Long stockId = stock.getId();
//            try (
//                    CSVReader reader = new CSVReader(new FileReader(csvFilePath));
//            ) {
//                reader.readNext();
//
//                String[] nextLine;
//
//                while ((nextLine = reader.readNext()) != null) {
//                    String stockDateId = nextLine[0];
//
//                    Optional<StockDaily> optionalStockDaily = stockDailyRepository.findByStockIdAndStockDateId(stockId, stockDateId);
//
//                    if(optionalStockDaily.isPresent()) {
//                        StockDaily stockDaily = optionalStockDaily.get();
////                        float aiMaxReliability = Float.parseFloat(nextLine[4]);
////                        float aiMinReliability = Float.parseFloat(nextLine[5]);
////                        float aiEndReliability = Float.parseFloat(nextLine[6]);
////                        float aiReliability = Float.parseFloat(nextLine[7]);
////                        float aiWeeklyReliability = Float.parseFloat(nextLine[8]);
//                        int maxPrice = Integer.parseInt(nextLine[1]);
//                        int minPrice = Integer.parseInt(nextLine[2]);
//                        int endPrice = Integer.parseInt(nextLine[3]);
//
//                        stockDaily.setAiMaxPrice(maxPrice);
//                        stockDaily.setAiMinPrice(minPrice);
//                        stockDaily.setAiEndPrice(endPrice);
//
////                        stockDaily.setAiMaxReliability(aiMaxReliability);
////                        stockDaily.setAiMinReliability(aiMinReliability);
////                        stockDaily.setAiEndReliability(aiEndReliability);
////                        stockDaily.setAiReliability(aiReliability);
////                        stockDaily.setAiWeeklyReliability(aiWeeklyReliability);
//
//                        stockDailyRepository.save(stockDaily);
//                    } else {
//                        System.out.println("오류떴다");
//                    }
//
//                }
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            } catch (CsvValidationException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return ApiData.of("success");
//    }
}
