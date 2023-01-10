package ru.practicum.shareit.entities.booking.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.shareit.entities.booking.BookingStatus;
import ru.practicum.shareit.entities.item.dto.ItemDto;
import ru.practicum.shareit.entities.user.dto.UserDtoForBooking;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class BookingResponse {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private ItemDto item;
    private UserDtoForBooking booker;
    private BookingStatus status;
}
