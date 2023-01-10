package ru.practicum.shareit.entities.user.dto;

import ru.practicum.shareit.entities.user.model.User;


public class UserDtoMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId() != null ? user.getId() : null,
                user.getName(),
                user.getEmail(),
                user.getItems() != null ? user.getItems() : null,
                user.getRequests() != null ? user.getRequests() : null,
                user.getBookings() != null ? user.getBookings() : null,
                user.getComments() != null ? user.getComments() : null
        );
    }

    public static UserDtoForBooking toUserDtoForBooking(User user) {
        return new UserDtoForBooking(
                user.getId() != null ? user.getId() : null,
                user.getName()
        );
    }


    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId() != null ? userDto.getId() : null,
                userDto.getName(),
                userDto.getEmail(),
                userDto.getItems() != null ? userDto.getItems() : null,
                userDto.getRequests() != null ? userDto.getRequests() : null,
                userDto.getBookings() != null ? userDto.getBookings() : null,
                userDto.getComments() != null ? userDto.getComments() : null
        );
    }

    public static User toUser(UserDtoToUpdate userDtoToUpdate) {
        return new User(
                userDtoToUpdate.getId() != null ? userDtoToUpdate.getId() : null,
                userDtoToUpdate.getName(),
                userDtoToUpdate.getEmail(),
                userDtoToUpdate.getItems() != null ? userDtoToUpdate.getItems() : null,
                userDtoToUpdate.getRequests() != null ? userDtoToUpdate.getRequests() : null,
                userDtoToUpdate.getBookings() != null ? userDtoToUpdate.getBookings() : null,
                userDtoToUpdate.getComments() != null ? userDtoToUpdate.getComments() : null
        );
    }
}
