package ru.practicum.shareit.entities.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.entities.booking.BookingState;
import ru.practicum.shareit.entities.booking.BookingStatus;
import ru.practicum.shareit.entities.booking.dto.BookingDto;
import ru.practicum.shareit.entities.booking.dto.BookingMapper;
import ru.practicum.shareit.entities.booking.dto.BookingResponse;
import ru.practicum.shareit.entities.booking.model.Booking;
import ru.practicum.shareit.entities.booking.repository.BookingRepository;
import ru.practicum.shareit.entities.item.model.Item;
import ru.practicum.shareit.entities.item.repository.ItemRepository;
import ru.practicum.shareit.entities.user.model.User;
import ru.practicum.shareit.entities.user.repository.UserRepository;
import ru.practicum.shareit.exceptions.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;


    @Transactional
    @Override
    public BookingResponse createBooking(Long bookerId, BookingDto bookingDto) {
        Item item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new NoSuchItemException("Item with id: " + bookingDto.getItemId() + " not found."));
        if (!item.isAvailable()) {
            throw new ItemUnavailableException("Item with id: " + bookingDto.getItemId() + " unavailable.");
        }
        if (Objects.equals(item.getOwner().getId(), bookerId)) {
            throw new NoSuchItemException("User with id: " + bookerId + " is owner of item with id: " + item.getId());
        }

        User booker = userRepository.findById(bookerId)
                .orElseThrow(() -> new NoSuchUserException("User with id: " + bookerId + " not found."));

        bookingDto.setStatus(BookingStatus.WAITING);
        Booking booking = BookingMapper.toBooking(bookingDto, booker, item);
        validateBookingTime(booking, item.getBookings());

        return BookingMapper.toBookingResponse(bookingRepository.save(booking));
    }

    @Transactional
    @Override
    public BookingResponse updateBooking(Long ownerId, long bookingId, boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NoSuchBookingException("Booking with id: " + bookingId + " not found."));

        if (booking.getStatus().equals(BookingStatus.APPROVED) && approved) {
            throw new DoubleApproveException("This booking has already been approved");
        }

        Long itemId = booking.getItem().getId();

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new NoSuchUserException("User with id: " + ownerId + " not found."));

        if (owner.getItems().stream()
                .anyMatch(i -> Objects.equals(i.getId(), itemId))) {
            if (approved) {
                booking.setStatus(BookingStatus.APPROVED);
            } else {
                booking.setStatus(BookingStatus.REJECTED);
            }
            return BookingMapper.toBookingResponse(bookingRepository.save(booking));
        } else {
            throw new NoSuchItemException("Item with id: " + booking.getItem().getId() + " for user with id: " + ownerId + " not found");
        }
    }

    @Override
    public BookingResponse findById(Long userId, long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NoSuchBookingException("Booking with id: " + bookingId + " not found."));
        if (Objects.equals(userId, booking.getBooker().getId()) || Objects.equals(userId, booking.getItem().getOwner().getId())) {
            return BookingMapper.toBookingResponse(bookingRepository.findById(bookingId).get());
        } else {
            throw new NoSuchItemException("Booking with id: " + bookingId + " for user with id: " + userId + " not found");
        }
    }

    @Override
    public List<BookingResponse> findUsersBookings(Long userId, String state) {

        LocalDateTime requestTime = LocalDateTime.now();
        BookingState bookingState = BookingState.of(state);
        User booker = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException("User with id: " + userId + " not found."));
        Collection<Booking> result;

        switch (bookingState) {
            case ALL:
                result = bookingRepository.findAllByBookerOrderByStartDesc(booker);
                break;
            case CURRENT:
                result = bookingRepository.findCurrentByBooker(booker, requestTime);
                break;
            case PAST:
                result = bookingRepository.findPastByBooker(booker, requestTime);
                break;
            case FUTURE:
                result = bookingRepository.findFutureByBooker(booker, requestTime);
                break;
            case WAITING:
                result = bookingRepository.findAllByBookerAndStatusOrderByStartDesc(booker, BookingStatus.WAITING);
                break;
            case REJECTED:
                result = bookingRepository.findAllByBookerAndStatusOrderByStartDesc(booker, BookingStatus.REJECTED);
                break;
            case UNKNOWN:
            default:
                throw new UnsupportedBookingStateException("Unknown state: UNSUPPORTED_STATUS");
        }

        return result.stream()
                .map(BookingMapper::toBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> findUsersItemsBookings(Long userId, String state) {

        LocalDateTime requestTime = LocalDateTime.now();
        BookingState bookingState = BookingState.of(state);
        Collection<Booking> result;
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException("User with id: " + userId + " not found."));

        if (owner.getItems().isEmpty() || owner.getItems() == null) {
            throw new  NoSuchItemException("Items for user with id: " + userId + " not found.");
        }

        switch (bookingState) {
            case ALL:
                result = bookingRepository.findAllByOwnerOrderByStartDesc(owner);
                break;
            case CURRENT:
                result = bookingRepository.findCurrentByOwner(owner, requestTime);
                break;
            case PAST:
                result = bookingRepository.findPastByOwner(owner, requestTime);
                break;
            case FUTURE:
                result = bookingRepository.findFutureByOwner(owner, requestTime);
                break;
            case WAITING:
                result = bookingRepository.findAllByOwnerAndStatus(owner, BookingStatus.WAITING);
                break;
            case REJECTED:
                result = bookingRepository.findAllByOwnerAndStatus(owner, BookingStatus.REJECTED);
                break;
            case UNKNOWN:
            default:
                throw new UnsupportedBookingStateException("Unknown state: UNSUPPORTED_STATUS");
        }

        return result.stream()
                .map(BookingMapper::toBookingResponse)
                .collect(Collectors.toList());
    }

    private boolean isNoBookingTimeIntersections(Booking booking1, Booking booking2) {
        return booking1.getStart().isAfter(booking2.getEnd()) ||
                (booking1.getEnd().isBefore(booking2.getStart()));
    }

    private void validateBookingTime(Booking booking, List<Booking> bookingList) {

        if (booking.getStart().isBefore(LocalDateTime.now()) || booking.getEnd().isBefore(LocalDateTime.now())) {
            throw new BookingTimeException("Booking cannot be made in the past");
        }

        if (booking.getEnd().isBefore(booking.getStart())) {
            throw new BookingTimeException("The start of the booking must be before the end");
        }

        if (bookingList == null || bookingList.isEmpty()) {
            return;
        }

        for (Booking booking2 : bookingList) {
            boolean isNoIntersections = isNoBookingTimeIntersections(booking, booking2);
            if (!isNoIntersections) {
                throw new BookingTimeException("Item with id: " + booking.getItem().getId() + " unavailable at this time.");
            }
        }
    }
}
