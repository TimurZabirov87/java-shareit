package ru.practicum.shareit.entities.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.entities.user.dto.UserDto;
import ru.practicum.shareit.entities.user.dto.UserDtoMapper;
import ru.practicum.shareit.entities.user.dto.UserDtoToUpdate;
import ru.practicum.shareit.entities.user.model.User;
import ru.practicum.shareit.entities.user.repository.UserRepository;
import ru.practicum.shareit.exceptions.NoSuchUserException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDtoMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(long userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() ->new NoSuchUserException("User with id: " + userId + " not found."));
        return UserDtoMapper.toUserDto(user);
    }

    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserDtoMapper.toUser(userDto);
        return UserDtoMapper.toUserDto(userRepository.save(user));
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDtoToUpdate user, long userId) {
        if (userRepository.findById(userId).isPresent()) {
            User userForUpdate = userRepository.findById(userId).get();
            userForUpdate.setId(userId);
            if (user.getEmail() != null) {
                userForUpdate.setEmail(user.getEmail());
            }
            if (user.getName() != null) {
                userForUpdate.setName(user.getName());
            }
            if (user.getBookings() != null) {
                userForUpdate.setBookings(user.getBookings());
            }
            if (user.getComments() != null) {
                userForUpdate.setComments(user.getComments());
            }
            if (user.getRequests() != null) {
                userForUpdate.setRequests(user.getRequests());
            }
            if (user.getItems() != null) {
                userForUpdate.setItems(user.getItems());
            }
            userRepository.save(userForUpdate);
            return UserDtoMapper.toUserDto(userRepository.findById(userId).get());
        } else {
            throw new NoSuchUserException("User with id: " + userId + " not found.");
        }

    }

    @Transactional
    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }


}
