package stm.user.service;

import stm.user.dto.UserDto;
import stm.user.dto.UserNewDto;

public interface UserService {
    UserDto addUser(UserNewDto dto);
}
