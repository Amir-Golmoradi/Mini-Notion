package dev.amirgol.springtaskbackend.Infrastructure.user.adapter.inside.rest_api;

import dev.amirgol.springtaskbackend.Application.user.dto.request.UserRequestDto;
import dev.amirgol.springtaskbackend.Application.user.dto.response.UserResponseDto;
import dev.amirgol.springtaskbackend.Application.user.port.inside.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> findAllUsers(
            @RequestParam(required = false, defaultValue = "Mike Tyson") String name,
            @RequestParam(required = false, defaultValue = "miketyson@boxing.com") String email,
            Pageable pageable
    ) {
        var requestDto = UserRequestDto
                .builder()
                .name(name)
                .email(email)
                .passwordHash(null)
                .build();

        var pager = userService.findAllUsers(requestDto, pageable);
        return ResponseEntity.ok(pager.orElse(Page.empty()));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Short id) {
        var userResponseDto = userService.findUserById(id);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> findUserByEmail(@PathVariable String email) {
        var userResponseDto = userService.findUserByEmail(email);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserResponseDto> findUserByName(@PathVariable String name) {
        var userResponseDto = userService.findUserByName(name);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        var userResponseDto = userService.registerUser(userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable String email, @RequestBody UserRequestDto userRequestDto) {
        var userResponseDto = userService.editUser(email, userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }
}
