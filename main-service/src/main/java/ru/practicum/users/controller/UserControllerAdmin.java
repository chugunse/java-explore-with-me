package ru.practicum.users.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.users.dto.NewUserRequestDto;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.service.UserServiceAdmin;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@Validated
@RequiredArgsConstructor
public class UserControllerAdmin {
    private final UserServiceAdmin userServiceAdmin;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto addUser(@RequestBody @Validated NewUserRequestDto newUserRequestDto) {
        log.info("добавление юзера {}", newUserRequestDto);
        return userServiceAdmin.addUser(newUserRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<UserDto> getUsersByIds(@RequestParam(required = false, name = "ids") List<Long> ids,
                                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("получить юзеров по параметрам ids {}, from {}, size {}", ids, from, size);
        return userServiceAdmin.getUsersByIds(ids, from, size);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserById(@PathVariable Long userId) {
        userServiceAdmin.deleteUserById(userId);
    }

}
