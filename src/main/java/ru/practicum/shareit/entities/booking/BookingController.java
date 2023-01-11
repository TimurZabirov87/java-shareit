package ru.practicum.shareit.entities.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.entities.booking.dto.BookingDto;
import ru.practicum.shareit.entities.booking.dto.BookingResponse;
import ru.practicum.shareit.entities.booking.service.BookingService;

import javax.validation.Valid;
import java.util.List;

/**
 * Добавлена возможность брать вещи в аренду на определённые даты.
 * Вот основные сценарии и эндпоинты:
 *
 * ++Добавление нового запроса на бронирование. Запрос может быть создан любым пользователем, а затем подтверждён владельцем вещи.
 *   Эндпоинт — POST /bookings. После создания запрос находится в статусе WAITING — «ожидает подтверждения».
 *
 * - Подтверждение или отклонение запроса на бронирование.
 *   Может быть выполнено только владельцем вещи.
 *   Затем статус бронирования становится либо APPROVED, либо REJECTED.
 *   Эндпоинт — PATCH /bookings/{bookingId}?approved={approved}, параметр approved может принимать значения true или false.
 *
 * - Получение данных о конкретном бронировании (включая его статус).
 *   Может быть выполнено либо автором бронирования, либо владельцем вещи, к которой относится бронирование.
 *   Эндпоинт — GET /bookings/{bookingId}.
 *
 * - Получение списка всех бронирований текущего пользователя.
 *   Эндпоинт — GET /bookings?state={state}. Параметр state необязательный и по умолчанию равен ALL (англ. «все»).
 *   Также он может принимать значения:
 *   CURRENT (англ. «текущие»),
 *   **PAST** (англ. «завершённые»),
 *   FUTURE (англ. «будущие»),
 *   WAITING (англ. «ожидающие подтверждения»),
 *   REJECTED (англ. «отклонённые»).
 *   Бронирования должны возвращаться отсортированными по дате от более новых к более старым.
 *
 * - Получение списка бронирований для всех вещей текущего пользователя.
 *   Эндпоинт — GET /bookings/owner?state={state}.
 *   Этот запрос имеет смысл для владельца хотя бы одной вещи.
 *   Работа параметра state аналогична его работе в предыдущем сценарии.
 */
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingResponse add(@RequestHeader("X-Sharer-User-Id") Long bookerId,
                               @Valid @RequestBody BookingDto bookingDto) {
        return bookingService.createBooking(bookerId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingResponse update(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                             @PathVariable long bookingId,
                             @RequestParam boolean approved) {
        return bookingService.updateBooking(ownerId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingResponse findById(@RequestHeader("X-Sharer-User-Id") Long userId,
                            @PathVariable long bookingId) {
        return bookingService.findById(userId, bookingId);
    }

    @GetMapping
    public List<BookingResponse> findUsersBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
                            @RequestParam(value = "state", defaultValue = "ALL", required = false) String state) {
        return bookingService.findUsersBookings(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingResponse> findById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestParam(value = "state", defaultValue = "ALL", required = false) String state) {
        return bookingService.findUsersItemsBookings(userId, state);
    }

}
