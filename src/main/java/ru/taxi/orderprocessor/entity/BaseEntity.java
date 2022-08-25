package ru.taxi.orderprocessor.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

//@Entity
@Getter
@Setter
@MappedSuperclass // позволяет наследовать значения persistance context
public class BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    protected UUID id;

    protected LocalDateTime createdAt;
    protected LocalDateTime updateAt;

    @PrePersist
    public void onCreate(){
        this.createdAt =this.updateAt=LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.updateAt=LocalDateTime.now();
    }
}
