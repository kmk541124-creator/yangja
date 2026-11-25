package me.kimmukwan.yungja.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import me.kimmukwan.yungja.global.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@SuperBuilder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "meail")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "Role", columnDefinition = "varchar(20) default 'USER'")
    private String role;

    @Builder
    public User(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = "USER";
    }
}
