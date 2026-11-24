package me.kimmukwan.yungja.global;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@SuperBuilder
public class BaseEntity {

    @CreatedDate
    @Column(name = "create_date")
    private LocalDate createDate;

    @LastModifiedDate
    @Column(name = "modify_date")
    private LocalDate modifyDate;

}
