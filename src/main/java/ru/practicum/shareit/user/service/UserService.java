package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;
import java.util.Collection;

public interface UserService {
    Collection<User> getAllUsers();

    User getUserById(long userId);

    User createUser(User user);

    User updateUser(long userId, User user);

    User deleteUser(long userId);
}
