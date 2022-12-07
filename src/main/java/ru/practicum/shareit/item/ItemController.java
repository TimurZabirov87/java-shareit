package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoToUpdate;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    /*
    Вот основные сценарии, которые должно поддерживать приложение.

        ++  Добавление новой вещи. Будет происходить по эндпойнту POST /items.
            На вход поступает объект ItemDto.
            userId в заголовке X-Sharer-User-Id — это идентификатор пользователя, который добавляет вещь.
            Именно этот пользователь — владелец вещи.
            Идентификатор владельца будет поступать на вход в каждом из запросов, рассмотренных далее.

        ++  Редактирование вещи. Эндпойнт PATCH /items/{itemId}.
            Изменить можно название, описание и статус доступа к аренде.
            Редактировать вещь может только её владелец.

        ++  Просмотр информации о конкретной вещи по её идентификатору.
            Эндпойнт GET /items/{itemId}.
            Информацию о вещи может просмотреть любой пользователь.

        ++  Просмотр владельцем списка всех его вещей с указанием названия и описания для каждой.
            Эндпойнт GET /items.

        ++  Поиск вещи потенциальным арендатором.
            Пользователь передаёт в строке запроса текст, и система ищет вещи, содержащие этот текст в названии или описании.
            Происходит по эндпойнту /items/search?text={text}, в text передаётся текст для поиска.
            Проверьте, что поиск возвращает только доступные для аренды вещи.
     */

    private final ItemService itemService;

    @GetMapping
    public Collection<ItemDto> get(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getUsersItems(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-Id") long userId,
                               @PathVariable long itemId) {
        return itemService.getItemById(itemId);
    }

    @PostMapping
    public ItemDto add(@RequestHeader("X-Sharer-User-Id") Long userId,
                       @Valid@RequestBody ItemDto itemDto) {
        return itemService.createItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                                  @Valid@RequestBody ItemDtoToUpdate itemDtoToUpdate,
                                  @PathVariable long itemId) {
        return itemService.updateItem(userId, itemId, itemDtoToUpdate);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Later-User-Id") long userId,
                           @PathVariable long itemId) {
        itemService.deleteItem(userId, itemId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> searchItems(@RequestParam String text) {
        return itemService.searchItems(text);
    }
}
