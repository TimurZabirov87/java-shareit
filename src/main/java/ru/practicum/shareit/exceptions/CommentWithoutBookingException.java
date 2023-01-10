package ru.practicum.shareit.exceptions;

public class CommentWithoutBookingException extends RuntimeException {

    public CommentWithoutBookingException(String s) {
        super(s);
    }
}
