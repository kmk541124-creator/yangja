package me.kimmukwan.yungja.domain.chat.service;


import lombok.RequiredArgsConstructor;
import me.kimmukwan.yungja.domain.chat.dto.request.ChatRoomRequest;
import me.kimmukwan.yungja.domain.chat.dto.response.ChatRoomResponse;
import me.kimmukwan.yungja.domain.chat.entity.ChatRoom;
import me.kimmukwan.yungja.domain.chat.repository.ChatRoomRepository;
import me.kimmukwan.yungja.domain.user.entity.User;
import me.kimmukwan.yungja.domain.user.repository.UserRepository;
import me.kimmukwan.yungja.global.exception.CustomException;
import me.kimmukwan.yungja.global.exception.ExceptionMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {


    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatRoomResponse createChatRoom(ChatRoomRequest request, User user){
        ChatRoom chatRoom = ChatRoom.builder()
                .name(request.chatRoomName()).hostUser(user).build();

        chatRoomRepository.save(chatRoom);

        return ChatRoomResponse.from(chatRoom.getName());
    }

    @Transactional
    public void joinUser(Long userId, Long chatId){
        ChatRoom chatRoom = findByChatId(chatId);
        User user = findByUserId(userId);

        chatRoom.addUser(user);
    }

    @Transactional
    public void deleteUser(Long userId, Long chatId){
        ChatRoom chatRoom = findByChatId(chatId);
        User user = findByUserId(userId);

        chatRoom.deleteUser(user);
    }


    public ChatRoom findByChatId(Long chatId){
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(chatId);

        if(chatRoom.isEmpty()){
            throw new CustomException(ExceptionMessage.NOT_FOUND);
        }

        return chatRoom.get();
    }

    public User findByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new CustomException(ExceptionMessage.NOT_FOUND);
        }

        return user.get();
    }
}
