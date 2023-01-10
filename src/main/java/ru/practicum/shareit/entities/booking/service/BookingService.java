package ru.practicum.shareit.entities.booking.service;

import ru.practicum.shareit.entities.booking.dto.BookingDto;
import ru.practicum.shareit.entities.booking.dto.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(Long bookerId, BookingDto bookingDto);

    BookingResponse updateBooking(Long ownerId, long bookingId, boolean approved);

    BookingResponse findById(Long userId, long bookingId);

    List<BookingResponse> findUsersBookings(Long userId, String state);

    List<BookingResponse> findUsersItemsBookings(Long userId, String state);
}
