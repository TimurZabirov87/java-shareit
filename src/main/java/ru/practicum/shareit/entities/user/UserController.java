package ru.practicum.shareit.entities.user;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.entities.user.dto.UserDto;
import ru.practicum.shareit.entities.user.dto.UserDtoToUpdate;
import ru.practicum.shareit.entities.user.service.UserService;
import ru.practicum.shareit.entities.user.model.User;

import java.util.Collection;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@NumberFormat @PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@RequestBody UserDtoToUpdate userDtoToUpdate,
                           @PathVariable long userId) {
        return userService.updateUser(userDtoToUpdate, userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@NumberFormat @PathVariable long userId) {
        userService.deleteUser(userId);
    }
}
