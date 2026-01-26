package com.bezrukov.microserviceorders.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = {"orders"})
@EqualsAndHashCode(of = {"id"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String username;
    private String password;
    private Role role;
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<Order> orders = new ArrayList<>();
}
