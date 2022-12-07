package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoMapper;
import ru.practicum.shareit.item.dto.ItemDtoToUpdate;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Collection<Item> getItems(long userId) {
        return itemRepository.getItems(userId);
    }

    @Override
    public ItemDto createItem(Long userId, ItemDto itemDto) {
        Item item = ItemDtoMapper.toItem(itemDto, userId);
        return ItemDtoMapper.toItemDto(itemRepository.createItem(userId, item));
    }

    @Override
    public ItemDto updateItem(Long userId, long itemId, ItemDtoToUpdate itemDtoToUpdate) {
        return ItemDtoMapper.toItemDto(itemRepository.updateItem(userId, itemDtoToUpdate, itemId));
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        itemRepository.deleteItem(userId, itemId);
    }

    @Override
    public Collection<ItemDto> searchItems(String text) {
        return itemRepository.searchItems(text).stream()
                .map(ItemDtoMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getItemById(long itemId) {
        return ItemDtoMapper.toItemDto(itemRepository.findItemById(itemId));
    }

    @Override
    public Collection<ItemDto> getUsersItems(long userId) {
        return itemRepository.getUsersItems(userId).stream()
               .map(ItemDtoMapper::toItemDto)
               .collect(Collectors.toList());
    }
}
