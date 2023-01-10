package ru.practicum.shareit.entities.user.dto;

import lombok.*;
import ru.practicum.shareit.entities.booking.model.Booking;
import ru.practicum.shareit.entities.item.comment.model.Comment;
import ru.practicum.shareit.entities.item.model.Item;
import ru.practicum.shareit.entities.request.model.ItemRequest;
import javax.validation.constraints.Email;
import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor

public class UserDto {

    private Long id;
    private String name;
    @Email
    private String email;
    private List<Item> items;
    private List<ItemRequest> requests;
    private List<Booking> bookings;
    private List<Comment> comments;

}
