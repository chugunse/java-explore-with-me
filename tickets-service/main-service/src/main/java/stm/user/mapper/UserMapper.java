package stm.user.mapper;

import org.mapstruct.Mapper;
import stm.user.dto.UserDto;
import stm.user.dto.UserNewDto;
import stm.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserNewDto dto);

    UserDto toUserDto(User model);
}
