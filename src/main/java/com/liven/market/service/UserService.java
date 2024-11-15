package com.liven.market.service;

import com.liven.market.enums.MessageEnum;
import com.liven.market.exceptions.ApiErrorException;
import com.liven.market.model.User;
import com.liven.market.repository.UserRepository;
import com.liven.market.service.dto.request.CreateUserRequestDTO;
import com.liven.market.service.dto.response.UserDetailResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class UserService extends AbstractServiceRepo<UserRepository, User, UUID> {
    public UserService(UserRepository repository) {
        super(repository);
    }

    public UserDetailResponseDTO getUserInfo() {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new UserDetailResponseDTO(loggedUser);
    }

    public User findUserByEmail(String userEmail) throws ApiErrorException {
        log.info("Buscando usuário por email");
        return repository.findUsersByEmail(userEmail).orElseThrow(() -> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.USER_NOT_FOUND));
    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();
    }

    public UserDetailResponseDTO createUser(CreateUserRequestDTO createUserRequest) throws ApiErrorException {
        validateCreateUserRequest(createUserRequest);
        log.info("Creating new user");
        String encryptedPass = new BCryptPasswordEncoder().encode(createUserRequest.getPassword());
        User newUser = new User();
        newUser.setEmail(createUserRequest.getEmail());
        newUser.setName(createUserRequest.getName());
        newUser.setPassword(encryptedPass);
        repository.save(newUser);
        log.info("User created");
        return new UserDetailResponseDTO(newUser);
    }

    private void validateCreateUserRequest(CreateUserRequestDTO createUserRequest) throws ApiErrorException {
        log.info("Verifying if email is already registered");
        Optional<User> userAux = repository.findUsersByEmail(createUserRequest.getEmail());
        if (userAux.isPresent()) {
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.USER_ALREADY_EXISTS);
        }
    }
}
