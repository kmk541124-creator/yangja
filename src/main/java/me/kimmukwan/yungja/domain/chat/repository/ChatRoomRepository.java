package me.kimmukwan.yungja.domain.chat.repository;

import me.kimmukwan.yungja.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
}
