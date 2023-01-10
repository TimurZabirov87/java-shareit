package ru.practicum.shareit.entities.item.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.shareit.entities.booking.dto.BookingDtoForItem;
import ru.practicum.shareit.entities.item.comment.dto.CommentDto;


import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class ItemDtoWithBookingsAndComments {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private BookingDtoForItem lastBooking;
    private BookingDtoForItem nextBooking;
    private List<CommentDto> comments;
    private Long requestId;

    public ItemDtoWithBookingsAndComments(String name, String description, Boolean available, Long requestId) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.requestId = requestId;
    }

    public ItemDtoWithBookingsAndComments(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public Boolean isAvailable() {
        return available;
    }
}