package io.joyoungc.api.service;

import io.joyoungc.api.dto.UserDto;
import io.joyoungc.config.cache.ServerCacheConfig;
import io.joyoungc.data.domain.user.User;
import io.joyoungc.data.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static io.joyoungc.api.ApiCodes.Cache.Constants.CACHE_USER_LIST;

/***
 * Created by Aiden Jeong on 2021.11.14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public UserDto.ResponseUser getUser(long userId) {
        User user = userRepository.getById(userId);
        return modelMapper.map(user, UserDto.ResponseUser.class);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = CACHE_USER_LIST)
    public List<UserDto.ResponseUser> getUsers() {
        List<User> all = userRepository.findAll();
        return all.stream().map(o -> modelMapper.map(o, UserDto.ResponseUser.class)).collect(Collectors.toList());
    }

//    @Cacheable(value = CACHE_USER_LIST, cacheManager = ServerCacheConfig.CACHE_JDK_MANAGER)
    @Transactional(readOnly = true)
    public UserDto.ResponseUser2 getUserByJdkCache(long userId) {
        User user = userRepository.getById(userId);
        return modelMapper.map(user, UserDto.ResponseUser2.class);
    }

    @Transactional
    public Long createUser(UserDto.RequestUser dto) {
        User user = modelMapper.map(dto, User.class);
        User save = userRepository.save(user);
        return save.getId();
    }

}
