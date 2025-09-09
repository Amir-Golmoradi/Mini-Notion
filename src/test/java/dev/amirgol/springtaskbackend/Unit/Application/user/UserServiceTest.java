package dev.amirgol.springtaskbackend.Unit.Application.user;

import dev.amirgol.springtaskbackend.user.application.dto.request.UserRequestDto;
import dev.amirgol.springtaskbackend.user.application.port.outside.UserServiceImpl;
import dev.amirgol.springtaskbackend.user.domain.model.User;
import dev.amirgol.springtaskbackend.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("It should return the users when they exists")
    void itShouldReturnUserWhenExists() {
        var pageable = PageRequest.of(1, 2);
        String name1 = "John Doe1";
        String name2 = "John Doe2";
        String email1 = "johnDoe1@example.com";
        String password1 = "password1";
        var user = new User();

        var requestDto = UserRequestDto
                .builder()
                .name(name1)
                .email(email1)
                .passwordHash(password1)
                .build();

        Page<User> users = new PageImpl<>(List.of(
                User
                        .builder()
                        .id(Short.valueOf("1"))
                        .name(name1)
                        .build(),
                User
                        .builder()
                        .id(Short.valueOf("2"))
                        .name(name2)
                        .build()
        ));


        when(userRepository.findUsersByNameAndEmail(name1, email1, pageable)).thenReturn(users);

        var result = userService.findAllUsers(requestDto, pageable);

        assertTrue(result.isPresent());
        assertEquals(2,result.get().getTotalElements());
        assertEquals(name1,result.get().getContent().get(1).name());
        assertEquals(name2,result.get().getContent().get(2).name());
    }

}
