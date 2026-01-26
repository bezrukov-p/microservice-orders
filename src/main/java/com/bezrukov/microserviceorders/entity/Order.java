package com.bezrukov.microserviceorders.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString(exclude = "user")
@EqualsAndHashCode(of = {"id"})
public class Order {
    @Id
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
}
