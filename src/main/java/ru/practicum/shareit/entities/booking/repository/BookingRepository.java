package ru.practicum.shareit.entities.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.entities.booking.BookingStatus;
import ru.practicum.shareit.entities.booking.model.Booking;
import ru.practicum.shareit.entities.item.model.Item;
import ru.practicum.shareit.entities.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Collection<Booking> findAllByBookerOrderByStartDesc(User booker);

    @Query("select b from Booking b " +
    "where b.booker = :booker and :now between b.start and b.end order by b.start desc ")
    List<Booking> findCurrentByBooker(@Param("booker") User booker, @Param("now") LocalDateTime now);
//    Версия findCurrentByBooker() в JPA:
//    List<Booking> findAllByBookerAndStartBeforeAndEndAfterOrderByStartDesc(User booker, LocalDateTime now, LocalDateTime alsoNow);
//    Здесь у меня получилось, что два раза нужно now передать, что выглядит весьма криво ))

    @Query("select b from Booking b " +
            "where b.booker = :booker and b.end < :now order by b.start desc ")
    List<Booking> findPastByBooker(@Param("booker") User booker, @Param("now") LocalDateTime now);

//    Версия findPastByBooker() в JPA:
    List<Booking> findAllByBookerAndEndBeforeOrderByStartDesc(User booker, LocalDateTime now);

    @Query("select b from Booking b " +
            "where b.booker = :booker and b.start > :now order by b.start desc ")

    List<Booking> findFutureByBooker(@Param("booker") User booker, @Param("now") LocalDateTime now);

//  Версия findFutureByBooker() в JPA:
    List<Booking> findAllByBookerAndStartAfterOrderByStartDesc(User booker, LocalDateTime now);

//  Поля owner в Booking нет, поэтому далее без @Query я не обошелся, кроме уже имеющихся ниже двух методов
    @Query("select b from Booking b " +
            "where b.item.owner = :owner order by b.start desc ")
    Collection<Booking> findAllByOwnerOrderByStartDesc(User owner);

    @Query("select b from Booking b " +
            "where b.item.owner = :owner and :now between b.start and b.end order by b.start desc ")
    List<Booking> findCurrentByOwner(@Param("owner") User owner, @Param("now") LocalDateTime now);

    @Query("select b from Booking b " +
            "where b.item.owner = :owner and b.end < :now order by b.start desc ")
    List<Booking> findPastByOwner(@Param("owner") User owner, @Param("now") LocalDateTime now);

    @Query("select b from Booking b " +
            "where b.item.owner = :owner and b.start > :now order by b.start desc ")
    List<Booking> findFutureByOwner(@Param("owner") User owner, @Param("now") LocalDateTime now);

    List<Booking> findAllByBookerAndStatusOrderByStartDesc(User owner, BookingStatus status);

    @Query("select b from Booking b " +
            "where b.item.owner = :owner and b.status = :status order by b.start desc ")
    List<Booking> findAllByOwnerAndStatus(User owner, BookingStatus status);

    List<Booking> findAllByItemOrderByStartDesc(Item item);

}
