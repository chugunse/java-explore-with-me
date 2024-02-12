package stm.user.storage;

import stm.user.model.User;

public interface UserRepository {
    User save(User user);
}
