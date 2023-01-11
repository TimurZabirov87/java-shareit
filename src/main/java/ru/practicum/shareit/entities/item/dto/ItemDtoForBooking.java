package ru.practicum.shareit.entities.item.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class ItemDtoForBooking {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;

    public ItemDtoForBooking(String name, String description, Boolean available, Long requestId) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.requestId = requestId;
    }

    public ItemDtoForBooking(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public Boolean isAvailable() {
        return available;
    }

}
