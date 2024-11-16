package com.liven.market.controller;

import com.liven.market.LivenMarketApplication;
import com.liven.market.enums.MessageEnum;
import com.liven.market.exceptions.ApiErrorException;
import com.liven.market.service.dto.request.CreateSessionRequestDTO;
import com.liven.market.service.dto.request.CreateUserRequestDTO;
import com.liven.market.service.dto.response.Response;
import com.liven.market.service.dto.response.SessionResponseDTO;
import com.liven.market.service.dto.response.UserDetailResponseDTO;
import com.liven.market.utils.TokenUtils;
import com.liven.market.utils.UsuarioMock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@ResetDatabase
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LivenMarketApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    UserController userController;

    @Autowired
    private TokenUtils tokenUtils;

    private String baseUserEmail = "teste@test.com";
    private String baseUserPassword = "123456";
    private String baseUserName = "User Test";
    private String authenticateUserFailMessage = "Usuário inexistente ou senha inválida";
    private String userAuthenticationToken;

    @BeforeEach
    public void beforeEach() {
        UsuarioMock.builder()
                .setEmail(baseUserEmail)
                .setUserName(baseUserName)
                .build();
    }

    @Test
    @Order(1)
    public void createUserNoErrorTest() throws ApiErrorException {
        CreateUserRequestDTO createUserRequestDTO = new CreateUserRequestDTO(baseUserEmail, baseUserName, baseUserPassword);
        Response<UserDetailResponseDTO> createdUser = userController.createUser(createUserRequestDTO);
        assertEquals(baseUserEmail, createdUser.getBody().getEmail());
        assertEquals(baseUserName, createdUser.getBody().getName());
    }

    @Test
    @Order(2)
    public void createUserDTOErrorValidationTest() {
        CreateUserRequestDTO createUserRequestDTO = new CreateUserRequestDTO("asd", baseUserName, baseUserPassword);
        ApiErrorException exception = assertThrows(ApiErrorException.class, () -> {
            userController.createUser(createUserRequestDTO);
        });
        assertEquals(MessageEnum.VALIDATION_ERROR, exception.getMessageApiEnum());
    }


    @Test
    @Order(3)
    public void createUserSession() throws ApiErrorException {
        CreateSessionRequestDTO testSessionData = new CreateSessionRequestDTO(baseUserEmail, baseUserPassword);
        Response<SessionResponseDTO> response = userController.createSession(testSessionData);
        assertEquals(200, response.getStatusCode());
        userAuthenticationToken = response.getBody().getAuthToken();
        String emailFromToken = tokenUtils.validateToken(userAuthenticationToken);
        assertEquals(baseUserEmail, emailFromToken);
    }

    @Test
    @Order(4)
    public void createUserSessionWithNoUserEmail() {
        CreateSessionRequestDTO testSessionData = new CreateSessionRequestDTO("wrongEmail@email.com", baseUserPassword);
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            userController.createSession(testSessionData);
        });
        assertEquals(authenticateUserFailMessage, exception.getMessage());
    }

    @Test
    @Order(5)
    public void getUserFromToken() {
        Response<UserDetailResponseDTO> response = userController.getMe();
        assertEquals(200, response.getStatusCode());
        assertEquals(baseUserEmail, response.getBody().getEmail());
    }

}
