package com.bifrost.ChatApp.service;

import com.bifrost.ChatApp.dto.UserDTO;
import com.bifrost.ChatApp.entitie.User;
import com.bifrost.ChatApp.exception.EmailYaExisteException;
import com.bifrost.ChatApp.exception.UsuarioNoEncontradoException;
import com.bifrost.ChatApp.exception.UsuarioYaExisteException;
import com.bifrost.ChatApp.mapper.UserMapper;
import com.bifrost.ChatApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Usado para encriptar contraseñas
    private final UserMapper userMapper;
    // Registrar nuevo usuario
    public User registerUser(String username, String password, String email) {

        log.info("INICIO---->registerUser :" + "username = " + username);
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsuarioYaExisteException("El usuario ya existe.");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailYaExisteException("El email ya existe.");
        }

        String encryptedPassword = passwordEncoder.encode(password);
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(encryptedPassword);
        newUser.setUsername(username);
        return userRepository.save(newUser);

    }

    // Autenticar usuario
    public User authenticateUser(String username, String password) {
        log.info("INICIO---->authenticateUser :" + "username = " + username);
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            log.info("El usuario no existe o la contraseña es incorrecta");
            throw new UsuarioNoEncontradoException("Nombre de usuario o contraseña incorrectos.");
        }
        log.info(user.get().toString());
        return user.get();
    }

    public List<User> findUsersByIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    public List<UserDTO> findUsersDTOsByIds(List<Long> userIds) {
       return userMapper.getDTOUserList(findUsersByIds(userIds));
    }

    public String getUsername(Long senderId) {
        log.info("INICIO---->getUsername :"+"senderId = " + senderId);
        return userRepository.findUsernameById(senderId);
    }
}
