package dev.amirgol.springtaskbackend.Application.user.port.inside;

import dev.amirgol.springtaskbackend.Application.user.dto.request.UserRequestDto;
import dev.amirgol.springtaskbackend.Application.user.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    Optional<Page<UserResponseDto>> findAllUsers(UserRequestDto requestDto, Pageable pageable);

    UserResponseDto findUserById(Short id);

    UserResponseDto findUserByEmail(String email);

    UserResponseDto findUserByName(String username);

    UserResponseDto registerUser(UserRequestDto requestDto);

    UserResponseDto editUser(String email, UserRequestDto requestDto);
}
