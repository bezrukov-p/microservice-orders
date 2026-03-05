package com.bezrukov.microserviceorders.controller;

import com.bezrukov.microserviceorders.dto.UserDto;
import com.bezrukov.microserviceorders.service.UserService;
import com.bezrukov.microserviceorders.utils.MapperDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers().stream().map(
                MapperDto::userToDto
        ).collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> deleteUser(@PathVariable UUID id) {
        return ResponseEntity.ok(MapperDto.userToDto(userService.delete(id)));
    }
}
