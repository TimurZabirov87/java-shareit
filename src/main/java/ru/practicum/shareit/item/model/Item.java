package ru.practicum.shareit.item.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validator.ValidItemsDescription;
import ru.practicum.shareit.validator.ValidItemsName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    Long id;
    @NonNull
    @ValidItemsName
    String name;
    @ValidItemsDescription
    String description;
    @NonNull
    Boolean available;
    @NonNull
    Long ownerId;
    Long requestId;

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
