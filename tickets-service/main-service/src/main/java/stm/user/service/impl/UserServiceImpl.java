package stm.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import stm.exception.model.ConflictException;
import stm.user.dto.UserDto;
import stm.user.dto.UserNewDto;
import stm.user.mapper.UserMapper;
import stm.user.model.User;
import stm.user.service.UserService;
import stm.user.storage.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto addUser(UserNewDto dto) {
        try {
            User user = userRepository.save(userMapper.toUser(dto));
            return userMapper.toUserDto(user);
        } catch (DuplicateKeyException e) {
            throw new ConflictException("логин '" + dto.getLogin() + "' занят");
        }
    }
}
