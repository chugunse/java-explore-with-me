package ru.practicum.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exception.model.BadRequestException;
import ru.practicum.exception.model.ConflictException;
import ru.practicum.exception.model.ResourceNotFoundException;
import ru.practicum.users.dto.NewUserRequestDto;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.model.User;
import ru.practicum.users.mapper.UserMapper;
import ru.practicum.users.storage.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceAdminImpl implements UserServiceAdmin {
    private final UserRepository userRepository;
//    private final FindObjectInRepository findObjectInRepository;

    @Override
    public List<UserDto> getUsersByIds(List<Long> ids, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        List<User> users;
        if (ids != null && !ids.isEmpty()) {
            users = userRepository.findAllUsersByIds(ids, pageable);
        } else {
            users = userRepository.findAllBy(pageable);
        }
        return users.stream().map(UserMapper::userToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto addUser(NewUserRequestDto newUserRequestDto) {
        User user = UserMapper.newUserRequestToUser(newUserRequestDto);
        try {
            return UserMapper.userToDto(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Почта" + newUserRequestDto.getEmail() + " или имя пользователя " +
                    newUserRequestDto.getName() + " уже используется");
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Запрос на добавление пользователя " + newUserRequestDto + " составлен не корректно");
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("Пользователь c id = " + userId + " не найден");
        }
    }
}