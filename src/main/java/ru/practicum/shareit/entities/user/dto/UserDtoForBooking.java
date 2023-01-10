package ru.practicum.shareit.entities.user.dto;

import lombok.*;



@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor

public class UserDtoForBooking {
    private Long id;
    private String name;
}
