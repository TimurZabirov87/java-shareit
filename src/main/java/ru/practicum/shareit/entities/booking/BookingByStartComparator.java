package ru.practicum.shareit.entities.booking;

import ru.practicum.shareit.entities.booking.model.Booking;

import java.util.Comparator;

public class BookingByStartComparator implements Comparator<Booking> {

    @Override
    public int compare(Booking o1, Booking o2) {
        if (o1 == o2) return 0;
        if (o1.getStart().isAfter(o2.getStart())) {
            return 1;

        } else if (o1.getStart().isBefore(o2.getStart())) {
            return -1;

        } else {
            return 0;
        }
    }

    @Override
    public Comparator<Booking> reversed() {
        return Comparator.super.reversed();
    }
}
