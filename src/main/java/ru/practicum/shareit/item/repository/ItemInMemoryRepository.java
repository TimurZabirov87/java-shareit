package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NoSuchItemException;
import ru.practicum.shareit.exceptions.NoSuchUserException;
import ru.practicum.shareit.item.dto.ItemDtoToUpdate;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemInMemoryRepository implements ItemRepository{
    Map<Long, List<Item>> usersItemsMap = new HashMap<>();
    Long itemIdCounter = 0L;
    private final UserRepository userRepository;

    @Override
    public Collection<Item> getItems(long userId) {
        return usersItemsMap.get(userId);
    }

    @Override
    public Collection<Item> getUsersItems(long userId) {
        return usersItemsMap.get(userId);
    }

    @Override
    public Item createItem(Long userId, Item item) {
        if (userRepository.findById(userId) != null) {
            item.setId(getNextItemId());
            item.setOwnerId(userId);
            if (usersItemsMap.containsKey(userId)) {
                usersItemsMap.get(userId).add(item);
            } else {
                List<Item> itemList = new ArrayList<>();
                itemList.add(item);
                usersItemsMap.put(userId, itemList);
            }
            return item;
        } else {
            throw new NoSuchUserException("Пользователь с id: " + userId + " не найден.");
        }
    }

    @Override
    public Item findItemById(long itemId) {
        Item item = null;
        for (List<Item> itemList : usersItemsMap.values()) {
            for (Item item1 : itemList) {
                if (item1.getId() == itemId) {
                    item = item1;
                }
            }
        }
        if (item != null) {
            return item;
        } else {
            throw new NoSuchItemException("Вещь с id: " + itemId + " не найдена.");
        }
    }

    @Override
    public Item findItemByUserAndId(long userId, long itemId) {
        if (userRepository.findById(userId) == null) {
            throw new NoSuchUserException("Пользователь с id: " + userId + " не найден.");
        }

        Item itemForSend = null;
        if (usersItemsMap.get(userId) != null) {
            for (Item item : usersItemsMap.get(userId)) {
                if (item.getId() == itemId) {
                    itemForSend = item;
                }
            }
        }
        if (itemForSend != null) {
            return itemForSend;
        } else {
            throw new NoSuchItemException("Вещь с id: " + itemId + " у пользователя с id: " + userId + " не найдена.");
        }
    }

    @Override
    public Item updateItem(Long userId, ItemDtoToUpdate item, long itemId) {
        Item itemForUpdate = findItemByUserAndId(userId, itemId);
        if (itemForUpdate != null) {
            usersItemsMap.get(userId).remove(itemForUpdate);
            if (item.getName() != null){
                itemForUpdate.setName(item.getName());
            }
            if (item.getDescription() != null){
                itemForUpdate.setDescription(item.getDescription());
            }
            if (item.isAvailable() != null){
                itemForUpdate.setAvailable(item.isAvailable());
            }
            if (item.getRequestId() != null) {
                itemForUpdate.setRequestId(item.getRequestId());
            }
            usersItemsMap.get(userId).add(itemForUpdate);
            return itemForUpdate;
        } else if (userRepository.findById(userId) == null) {
            throw new NoSuchUserException("Пользователь с id: " + userId + " не найден.");
        } else {
            throw new NoSuchItemException("Вещь с id: " + itemId + " у пользователя с id: " + userId + " не найдена.");
        }
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        if (findItemByUserAndId(userId, itemId) != null) {
            usersItemsMap.get(userId).removeIf(item -> item.getId() == itemId);
        }
    }

    @Override
    public Collection<Item> searchItems(String text) {
        List<Item> foundItems = new ArrayList<>();
        if (text.isBlank() || text.isEmpty()) {
            return foundItems;
        }
        for (List<Item> itemList : usersItemsMap.values()) {
            foundItems.addAll(itemList.stream()
                    .filter(item -> (item.getName().toLowerCase().contains(text.toLowerCase())
                                             || item.getDescription().toLowerCase().contains(text.toLowerCase())) &&
                            item.isAvailable())
                    .collect(Collectors.toList()));
        }
        return foundItems;
    }

    private Long getNextItemId() {
        itemIdCounter++;
        return itemIdCounter;
    }
}
