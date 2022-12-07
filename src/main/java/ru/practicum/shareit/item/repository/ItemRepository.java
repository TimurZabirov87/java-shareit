package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.dto.ItemDtoToUpdate;
import ru.practicum.shareit.item.model.Item;
import java.util.Collection;

public interface ItemRepository {
    Collection<Item> getItems(long userId);

    Collection<Item> getUsersItems(long userId);

    Item createItem(Long userId, Item item);

    Item findItemById(long itemId);

    Item findItemByUserAndId(long userId, long itemId);

    Item updateItem(Long userId, ItemDtoToUpdate item, long itemId);

    void deleteItem(long userId, long itemId);

    Collection<Item> searchItems(String text);
}
