package ru.practicum.shareit.exceptions;

public class NoSuchItemRequestException extends RuntimeException {

    public NoSuchItemRequestException(String s) {
        super(s);
    }
}
