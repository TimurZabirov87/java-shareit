package ru.practicum.shareit.entities.item.service;

import ru.practicum.shareit.entities.item.comment.dto.CommentDto;
import ru.practicum.shareit.entities.item.dto.ItemDto;
import ru.practicum.shareit.entities.item.dto.ItemDtoToUpdate;
import ru.practicum.shareit.entities.item.dto.ItemDtoWithBookings;
import ru.practicum.shareit.entities.item.dto.ItemDtoWithBookingsAndComments;
import ru.practicum.shareit.entities.item.model.Item;

import java.util.Collection;

public interface ItemService {
    Collection<Item> getItems(long userId);

    ItemDto createItem(Long userId, ItemDto itemDto);

    ItemDto updateItem(Long userId, long itemId, ItemDtoToUpdate itemDtoToUpdate);

    void deleteItem(long userId, long itemId);

    Collection<ItemDto> searchItems(String text);

    ItemDtoWithBookingsAndComments getItemById(long itemId, long userId);

    Collection<ItemDtoWithBookingsAndComments> getUsersItems(long userId);

    CommentDto addComment(Long userId, long itemId, CommentDto commentDto);
}
