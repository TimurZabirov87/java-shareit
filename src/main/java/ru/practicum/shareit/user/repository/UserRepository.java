package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository {
    Collection<User> findAll();

    User create(User user);

    User update(long userId, User user);

    User findById(long id);

    User delete(long id);
}
