package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.shareit.validator.ValidItemsToUpdateDescription;
import ru.practicum.shareit.validator.ValidItemsToUpdateName;

@Data
@Jacksonized
@Builder
public class ItemDtoToUpdate {

    Long id;

    @ValidItemsToUpdateName
    String name;
    @ValidItemsToUpdateDescription
    String description;

    Boolean available;
    Long requestId;

    public Boolean isAvailable() {
        return available;
    }

}
