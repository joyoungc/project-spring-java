package io.joyoungc.api.controller;

import io.joyoungc.api.dto.UserDto;
import io.joyoungc.data.domain.user.User;
import io.joyoungc.data.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/***
 * Created by Aiden Jeong on 2021.09.22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public void createUser(@RequestBody @Valid UserDto.RequestUser dto) {
        User user = modelMapper.map(dto, User.class);
        userRepository.save(user);
    }

}
