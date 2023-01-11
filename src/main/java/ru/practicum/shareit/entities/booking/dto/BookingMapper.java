package ru.practicum.shareit.entities.booking.dto;


import ru.practicum.shareit.entities.booking.model.Booking;
import ru.practicum.shareit.entities.item.dto.ItemDtoMapper;
import ru.practicum.shareit.entities.item.model.Item;
import ru.practicum.shareit.entities.user.dto.UserDtoMapper;
import ru.practicum.shareit.entities.user.model.User;


public class BookingMapper {

    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId() != null ? booking.getId() : null,
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getStatus() != null ? booking.getStatus() : null
        );
    }

    public static BookingDtoForItem toBookingDtoForItem(Booking booking) {
        if (booking == null) {
            return null;
        }
        return new BookingDtoForItem(
                booking.getId() != null ? booking.getId() : null,
                booking.getStart(),
                booking.getEnd(),
                booking.getBooker().getId(),
                booking.getStatus() != null ? booking.getStatus() : null
        );
    }

    public static BookingResponse toBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                ItemDtoMapper.toItemDto(booking.getItem()),
                UserDtoMapper.toUserDtoForBooking(booking.getBooker()),
                booking.getStatus()
        );
    }

    public static Booking toBooking(BookingDto bookingDto, User booker, Item item) {
        return new Booking(
                bookingDto.getId() != null ? bookingDto.getId() : null,
                bookingDto.getStart(),
                bookingDto.getEnd(),
                item,
                booker,
                bookingDto.getStatus() != null ? bookingDto.getStatus() : null
        );
    }

    public static Booking toBooking(BookingDtoToUpdate bookingDtoToUpdate, User booker, Item item) {
        return new Booking(
                bookingDtoToUpdate.getId() != null ? bookingDtoToUpdate.getId() : null,
                bookingDtoToUpdate.getStart(),
                bookingDtoToUpdate.getEnd(),
                item,
                booker,
                bookingDtoToUpdate.getStatus() != null ? bookingDtoToUpdate.getStatus() : null
        );
    }
}
