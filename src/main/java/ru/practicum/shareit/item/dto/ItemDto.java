package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.shareit.validator.ValidItemsDescription;
import ru.practicum.shareit.validator.ValidItemsName;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class ItemDto {
    private Long id;
    @NonNull
    @ValidItemsName
    private String name;
    @ValidItemsDescription
    private String description;
    @NonNull
    private Boolean available;
    private Long requestId;

    public ItemDto(String name, String description, Boolean available, Long requestId) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.requestId = requestId;
    }

    public ItemDto(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public Boolean isAvailable() {
        return available;
    }

}
