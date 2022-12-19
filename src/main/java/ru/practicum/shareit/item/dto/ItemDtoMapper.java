package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;


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

    public static Item toItem(ItemDto itemDto, Long userId) {
        return new Item(
                itemDto.getId() != null ? itemDto.getId() : null,
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.isAvailable(),
                userId,
                itemDto.getRequestId() != null ? itemDto.getRequestId() : null
        );
    }
}
