package me.kimmukwan.yungja.domain.chat.entity;


import jakarta.persistence.*;
import lombok.*;
import me.kimmukwan.yungja.domain.user.entity.User;
import me.kimmukwan.yungja.global.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_room")
@Getter
@SuperBuilder
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_chat_room",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @Builder
    public ChatRoom(String name, User hostUser){
        this.name = name;
        this.users.add(hostUser);
    }

    @Transactional
    public void addUser(User joinUser){
        this.users.add(joinUser);
    }

    @Transactional
    public void deleteUser(User user){
        this.users.remove(user);
    }
}
