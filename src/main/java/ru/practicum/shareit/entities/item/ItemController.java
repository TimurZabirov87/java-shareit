package ru.practicum.shareit.entities.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.entities.item.comment.dto.CommentDto;
import ru.practicum.shareit.entities.item.dto.ItemDtoToUpdate;
import ru.practicum.shareit.entities.item.dto.ItemDto;
import ru.practicum.shareit.entities.item.dto.ItemDtoWithBookingsAndComments;
import ru.practicum.shareit.entities.item.service.ItemService;

import javax.validation.Valid;
import java.util.Collection;

/**
 *         ++  Добавление новой вещи. Будет происходить по эндпойнту POST /items.
 *             На вход поступает объект ItemDto.
 *             userId в заголовке X-Sharer-User-Id — это идентификатор пользователя, который добавляет вещь.
 *             Именно этот пользователь — владелец вещи.
 *             Идентификатор владельца будет поступать на вход в каждом из запросов, рассмотренных далее.
 *
 *         ++  Редактирование вещи. Эндпойнт PATCH /items/{itemId}.
 *             Изменить можно название, описание и статус доступа к аренде.
 *             Редактировать вещь может только её владелец.
 *
 *         ++  Просмотр информации о конкретной вещи по её идентификатору.
 *             Эндпойнт GET /items/{itemId}.
 *             Информацию о вещи может просмотреть любой пользователь.
 *
 *         ++  Просмотр владельцем списка всех его вещей с указанием названия и описания для каждой.
 *             Эндпойнт GET /items.
 *
 *         ++  Поиск вещи потенциальным арендатором.
 *             Пользователь передаёт в строке запроса текст, и система ищет вещи, содержащие этот текст в названии или описании.
 *             Происходит по эндпойнту /items/search?text={text}, в text передаётся текст для поиска.
 *             Проверьте, что поиск возвращает только доступные для аренды вещи.
 *
 *         --  Комментарий можно добавить по эндпоинту
 *             POST /items/{itemId}/comment, создайте в контроллере метод для него.
 *
 *             Реализуйте логику по добавлению нового комментария к вещи в сервисе ItemServiceImpl.
 *             Для этого также понадобится создать интерфейс CommentRepository.
 *
 *             Не забудьте добавить проверку, что пользователь, который пишет комментарий, действительно брал вещь в аренду.
 *             Осталось разрешить пользователям просматривать комментарии других пользователей.
 *             Отзывы можно будет увидеть по двум эндпоинтам:
 *         --  — по GET /items/{itemId} для одной конкретной вещи
 *         --  — по GET /items для всех вещей данного пользователя.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public Collection<ItemDtoWithBookingsAndComments> get(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getUsersItems(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDtoWithBookingsAndComments getItemById(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @PathVariable long itemId) {
        return itemService.getItemById(itemId, userId);
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

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @Valid@RequestBody CommentDto commentDto,
                                 @PathVariable long itemId) {
        return itemService.addComment(userId, itemId, commentDto);
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
