package me.kimmukwan.yungja.global.config;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{roomId}")
    public void send(@DestinationVariable Long roomId, ChatMessageRequest request){
        messagingTemplate.convertAndSend("/sub/chat/" + roomId, request);
    }
}
