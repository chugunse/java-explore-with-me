package ru.practicum.users.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.users.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.id IN (:ids)")
    List<User> findAllUsersByIds(List<Long> ids, Pageable pageable);

    List<User> findAllBy(Pageable pageable);
}
