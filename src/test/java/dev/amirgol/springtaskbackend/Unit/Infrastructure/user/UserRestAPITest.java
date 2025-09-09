package dev.amirgol.springtaskbackend.Unit.Infrastructure.user;

import dev.amirgol.springtaskbackend.user.application.dto.request.UserRequestDto;
import dev.amirgol.springtaskbackend.user.application.dto.response.UserResponseDto;
import dev.amirgol.springtaskbackend.user.application.port.inside.UserService;
import dev.amirgol.springtaskbackend.user.infrastructure.adapter.inside.rest_api.UserRestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
@DisplayName("User Rest API Controller Unit Test")
public class UserRestAPITest {

    private static final String BASE_URL = "/api/v1/users";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void shouldFindUserByEmail() throws Exception {
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
    void shouldFindUserByName() throws Exception {
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

    @Test
    void shouldRegisterNewUser() throws Exception {
        var request = UserRequestDto
                .builder()
                .name("Taylor Swift")
                .email("taylorswift@gmail.com")
                .passwordHash("swifty123456")
                .build();

        var response = UserResponseDto
                .builder()
                .name("Taylor Swift")
                .email("taylorswift@gmail.com")
                .build();

        BDDMockito.given(userService.registerUser(request)).willReturn(response);

        mockMvc.perform(
                        get(BASE_URL))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateCurrentUser() throws Exception {
        String name = "Taylor Swift";
        String email = "taylorswift@gmail.com";
        String passwordHash = "swifty123456";
        var request = UserRequestDto
                .builder()
                .name(name)
                .email(email)
                .passwordHash(passwordHash)
                .build();

        var response = UserResponseDto
                .builder()
                .name("Adonis Creed")
                .email("adonis_creed_boxing@gmail.com")
                .build();

        BDDMockito.given(userService.editUser(email, request)).willReturn(response);

        mockMvc.perform(get(BASE_URL + "/email/{email}", email))
                .andExpect(status().isOk());

    }
}
