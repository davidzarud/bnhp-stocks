package com.bnhp.stock.controller.gemini;

import com.bnhp.stock.model.dto.gemini.GeminiRequest;
import com.bnhp.stock.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class GeminiController {

    private final ChatService chatService;

    @PostMapping("/ask")
    public ResponseEntity<String> askGemini(@RequestBody GeminiRequest request) {
        return ResponseEntity.ok(chatService.askGemini(request));
    }
}
