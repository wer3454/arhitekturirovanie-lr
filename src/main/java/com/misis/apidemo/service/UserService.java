package com.misis.apidemo.service;

import com.misis.apidemo.db.User;
import com.misis.apidemo.db.UserRepository;
import com.misis.apidemo.dto.UserCreateDTO;
import com.misis.apidemo.dto.UserDTO;
import com.misis.apidemo.dto.UserUpdateDTO;
import com.misis.apidemo.dto.mapper.UserMapper;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(
        UserRepository userRepository,
        UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getAllUsers() {
        return userMapper.toDTOList(userRepository.findAll());
    }

    public UserDTO getUserById(UUID id) {
        return userRepository.findById(id).map(userMapper::toDTO)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        User user = userMapper.toEntity(userCreateDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO updateUser(UUID id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (userUpdateDTO.name().isPresent()) {
            user.setName(userUpdateDTO.name().get());
        }

        if (userUpdateDTO.email().isPresent()) {
            user.setEmail(userUpdateDTO.email().get());
        }

        return userMapper.toDTO(userRepository.save(user));
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}
