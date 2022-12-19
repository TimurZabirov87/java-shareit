package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoToUpdate;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemService {
    Collection<Item> getItems(long userId);

    ItemDto createItem(Long userId, ItemDto itemDto);

    ItemDto updateItem(Long userId, long itemId, ItemDtoToUpdate itemDtoToUpdate);

    void deleteItem(long userId, long itemId);

    Collection<ItemDto> searchItems(String text);

    ItemDto getItemById(long itemId);

    Collection<ItemDto> getUsersItems(long userId);
}
