package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.DataException;
import ru.practicum.shareit.exceptions.DuplicateEmailException;
import ru.practicum.shareit.exceptions.NoSuchUserException;
import ru.practicum.shareit.user.model.User;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserInMemoryRepository implements UserRepository{

    Map<Long, User> allUsersMap = new HashMap<>();
    Long userIdCounter = 0L;

    @Override
    public Collection<User> findAll() {
        return allUsersMap.values();
    }

    @Override
    public User create(User user) {
        if (allUsersMap.values().stream()
                .anyMatch(user1 -> Objects.equals(user1.getEmail(), user.getEmail()))) {
            throw new DuplicateEmailException("Пользователь с такой e-mail уже существует.");
        } else  if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw new DataException("Invalid e-mail");
        } else {
            Long id = getUserId();
            user.setId(id);
            allUsersMap.put(id, user);
            return user;
        }
    }

    @Override
    public User update(long userId, User user) {
        User currentlyUser = findById(userId);
        if (currentlyUser != null) {
            if(user.getName() != null){
                currentlyUser.setName(user.getName());
            }
            if (user.getEmail() != null) {
                if (allUsersMap.values().stream()
                        .anyMatch(user1 -> Objects.equals(user1.getEmail(), user.getEmail()))) {
                    throw new DuplicateEmailException("Пользователь с такой e-mail уже существует.");
                } else  if (!EmailValidator.getInstance().isValid(user.getEmail())) {
                    throw new DataException("Invalid e-mail");
                } else {
                    currentlyUser.setEmail(user.getEmail());
                }
            }
            allUsersMap.put(userId, currentlyUser);
            return currentlyUser;
        } else {
            throw new NoSuchUserException("Пользователь с id: " + userId + " не найден.");
        }
    }

    @Override
    public User findById(long id) {
        User user = allUsersMap.get(id);
        if (user != null) {
            return user;
        } else {
            throw new NoSuchUserException("Пользователь с id: " + id + " не найден.");
        }
    }

    @Override
    public User delete(long id) {
        User user = allUsersMap.get(id);
        if (user != null) {
            allUsersMap.remove(id);
            return user;
        } else {
            throw new NoSuchUserException("Пользователь с id: " + id + " не найден.");
        }
    }


    private long getUserId() {
        userIdCounter++;
        return userIdCounter;
    }

}
