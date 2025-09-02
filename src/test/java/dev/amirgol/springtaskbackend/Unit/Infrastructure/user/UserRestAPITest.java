package dev.amirgol.springtaskbackend.Unit.Infrastructure.user;

import dev.amirgol.springtaskbackend.Application.user.dto.response.UserResponseDto;
import dev.amirgol.springtaskbackend.Application.user.port.inside.UserService;
import dev.amirgol.springtaskbackend.Infrastructure.user.adapter.inside.rest_api.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("User Rest API Controller Unit Test")
public class UserRestAPITest {

    private static final String BASE_URL = "/api/v1/users";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void shouldReturnUserByEmail() throws Exception {
        String email = "lucyhale@gmail.com";
        // given
        var user = UserResponseDto
                .builder()
                .email(email)
                .build();
        BDDMockito.given(userService.findUserByEmail(email)).willReturn(user);

        // when + then
        mockMvc.perform(
                        get(BASE_URL + "/email/{email}", email)
                )
                .andExpect(status().isOk()
                );
    }

    @Test
    void shouldReturnUserByName() throws Exception {
        String name = "Lucy Hale";

        var user = UserResponseDto
                .builder()
                .name(name)
                .build();
        BDDMockito.given(userService.findUserByName(name)).willReturn(user);

        mockMvc.perform(
                        get(BASE_URL + "/name/{name}", name)
                )
                .andExpect(status().isOk()
                );

    }
}
