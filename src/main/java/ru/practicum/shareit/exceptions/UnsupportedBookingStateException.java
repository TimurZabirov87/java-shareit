package ru.practicum.shareit.exceptions;

public class UnsupportedBookingStateException extends RuntimeException {

    public UnsupportedBookingStateException(String s) {
        super(s);
    }
}
