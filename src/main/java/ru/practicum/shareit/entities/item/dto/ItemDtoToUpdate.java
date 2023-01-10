package ru.practicum.shareit.entities.item.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.shareit.validator.ValidItemsToUpdateDescription;
import ru.practicum.shareit.validator.ValidItemsToUpdateName;

@Data
@Jacksonized
@Builder
public class ItemDtoToUpdate {

    private Long id;

    @ValidItemsToUpdateName
    private String name;
    @ValidItemsToUpdateDescription
    private String description;

    private Boolean available;
    private Long requestId;

    public Boolean isAvailable() {
        return available;
    }

}
