package ru.practicum.users.service;

import ru.practicum.users.dto.NewUserRequestDto;
import ru.practicum.users.dto.UserDto;

import java.util.List;

public interface UserServiceAdmin {
    List<UserDto> getUsersByIds(List<Long> ids, int from, int size);

    UserDto addUser(NewUserRequestDto newUserRequestDto);

    void deleteUserById(Long userId);
}
