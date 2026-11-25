package me.kimmukwan.yungja.domain.chat.dto.response;

public record ChatRoomResponse(
        String chatRoomName
) {

    public static ChatRoomResponse from(String roomName){
        return new ChatRoomResponse(roomName);
    }
}
