package me.kimmukwan.yungja.domain.chat.controller;


import lombok.RequiredArgsConstructor;
import me.kimmukwan.yungja.domain.chat.dto.request.ChatRoomRequest;
import me.kimmukwan.yungja.domain.chat.dto.response.ChatRoomResponse;
import me.kimmukwan.yungja.domain.chat.service.ChatRoomService;
import me.kimmukwan.yungja.domain.user.entity.User;
import me.kimmukwan.yungja.global.apiResponse.ApiResponse;
import me.kimmukwan.yungja.global.exception.CustomException;
import me.kimmukwan.yungja.global.exception.ExceptionMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
public class ChatRoomController {

    private ChatRoomService chatRoomService;

    @PostMapping("/create")
    public ApiResponse<ChatRoomResponse> create(@AuthenticationPrincipal User user,
                              @RequestBody ChatRoomRequest request){
        if(request.chatRoomName() == null){
            throw new CustomException(ExceptionMessage.NOT_INFO_CHAT_NAME);
        }
        ChatRoomResponse chatRoomResponse =  chatRoomService.createChatRoom(request, user);

        return ApiResponse.response(201, true, chatRoomResponse, null);
    }

    @PutMapping("/join/{chatId}/{userId}")
    public ApiResponse<Void> joinUser(@PathVariable Long chatId,
                                      @PathVariable Long userId){
        chatRoomService.joinUser(userId, chatId);

        return ApiResponse.response(200, true, null, null);
    }

    @PutMapping("/delete/{chatId}/{userId}")
    public ApiResponse<Void> deleteUser(@PathVariable Long chatId,
                                        @PathVariable Long userId){
        chatRoomService.deleteUser(userId, chatId);

        return ApiResponse.response(200, true, null , null);
    }
}
