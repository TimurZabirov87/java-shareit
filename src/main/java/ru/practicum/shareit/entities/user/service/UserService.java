package ru.practicum.shareit.entities.user.service;

import ru.practicum.shareit.entities.user.dto.UserDto;
import ru.practicum.shareit.entities.user.dto.UserDtoToUpdate;

import java.util.Collection;

public interface UserService {
    Collection<UserDto> getAllUsers();

    UserDto getUserById(long userId);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDtoToUpdate userDto, long userId);

    void deleteUser(long userId);
}
