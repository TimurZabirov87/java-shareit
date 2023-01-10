package ru.practicum.shareit.entities.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.shareit.entities.booking.BookingStatus;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class BookingDtoForItem {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long bookerId;
    private BookingStatus status;
    public BookingDtoForItem(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }
}