package ru.practicum.shareit.item.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.shareit.validator.ValidItemsDescription;
import ru.practicum.shareit.validator.ValidItemsName;


/**
 * id — уникальный идентификатор вещи;
 * name — краткое название;
 * description — развёрнутое описание;
 * available — статус о том, доступна или нет вещь для аренды;
 * owner — владелец вещи;
 * request — если вещь была создана по запросу другого пользователя, то в этом
 * поле будет храниться ссылка на соответствующий запрос
 */

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
public class Item {
    private Long id;
    @NonNull
    @ValidItemsName
    private String name;
    @ValidItemsDescription
    private String description;
    @NonNull
    private Boolean available;
    @NonNull
    private Long ownerId;
    private Long requestId;

    public Item(@NonNull String name, String description, Boolean available, @NonNull Long userId, Long requestId) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.ownerId = userId;
        this.requestId = requestId;
    }


    public Boolean isAvailable() {
        return available;
    }
}
