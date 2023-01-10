package ru.practicum.shareit.entities.item.dto;

import ru.practicum.shareit.entities.booking.dto.BookingDtoForItem;
import ru.practicum.shareit.entities.item.comment.dto.CommentDto;
import ru.practicum.shareit.entities.item.comment.model.Comment;
import ru.practicum.shareit.entities.item.model.Item;
import ru.practicum.shareit.entities.user.model.User;

import java.util.List;


public class ItemDtoMapper {
    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId() != null ? item.getId() : null,
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                item.getRequestId() != null ? item.getRequestId() : null
        );
    }

    public static ItemDtoWithBookings toItemDtoWithBookings(Item item, BookingDtoForItem lastBooking, BookingDtoForItem nextBooking) {
        return new ItemDtoWithBookings(
                item.getId() != null ? item.getId() : null,
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                lastBooking,
                nextBooking,
                item.getRequestId() != null ? item.getRequestId() : null
        );
    }

    public static ItemDtoWithBookingsAndComments toItemDtoWithBookingsAndComments(Item item,
                                                                                  BookingDtoForItem lastBooking,
                                                                                  BookingDtoForItem nextBooking,
                                                                                  List<CommentDto> comments) {
        return new ItemDtoWithBookingsAndComments(
                item.getId() != null ? item.getId() : null,
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                lastBooking,
                nextBooking,
                comments,
                item.getRequestId() != null ? item.getRequestId() : null
        );
    }

    public static ItemDtoWithComments toItemDtoWithComments(Item item, List<Comment> comments) {
        return new ItemDtoWithComments(
                item.getId() != null ? item.getId() : null,
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                comments,
                item.getRequestId() != null ? item.getRequestId() : null
        );
    }

    public static Item toItem(ItemDto itemDto, User owner) {
        return new Item(
                itemDto.getId() != null ? itemDto.getId() : null,
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.isAvailable(),
                owner,
                itemDto.getRequestId() != null ? itemDto.getRequestId() : null
        );
    }

    public static Item toItem(ItemDtoToUpdate itemDtoToUpdate, User owner) {
        return new Item(
                itemDtoToUpdate.getId() != null ? itemDtoToUpdate.getId() : null,
                itemDtoToUpdate.getName(),
                itemDtoToUpdate.getDescription(),
                itemDtoToUpdate.isAvailable(),
                owner,
                itemDtoToUpdate.getRequestId() != null ? itemDtoToUpdate.getRequestId() : null
        );
    }
}
