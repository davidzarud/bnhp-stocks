package com.bnhp.stock.service;

import com.bnhp.stock.feign.StockFeignClient;
import com.bnhp.stock.model.dto.gemini.GeminiRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final StockFeignClient stockFeignClient;

    public String askGemini(GeminiRequest request) {
        return StringEscapeUtils.unescapeJava(stockFeignClient.askGemini(request));
    }

}
