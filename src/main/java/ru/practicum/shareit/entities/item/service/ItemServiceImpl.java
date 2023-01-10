package ru.practicum.shareit.entities.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.entities.booking.BookingByEndComparator;
import ru.practicum.shareit.entities.booking.BookingByStartComparator;
import ru.practicum.shareit.entities.booking.dto.BookingMapper;
import ru.practicum.shareit.entities.booking.model.Booking;
import ru.practicum.shareit.entities.booking.repository.BookingRepository;
import ru.practicum.shareit.entities.item.comment.dto.CommentDto;
import ru.practicum.shareit.entities.item.comment.dto.CommentMapper;
import ru.practicum.shareit.entities.item.comment.repository.CommentRepository;
import ru.practicum.shareit.entities.item.dto.*;
import ru.practicum.shareit.entities.item.model.Item;
import ru.practicum.shareit.entities.item.repository.ItemRepository;
import ru.practicum.shareit.entities.user.model.User;
import ru.practicum.shareit.entities.user.repository.UserRepository;
import ru.practicum.shareit.exceptions.CommentWithoutBookingException;
import ru.practicum.shareit.exceptions.NoSuchItemException;
import ru.practicum.shareit.exceptions.NoSuchUserException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private final BookingRepository bookingRepository;

    @Override
    public Collection<Item> getItems(long userId) {
        User owner = userRepository.findById(userId)
                                 .orElseThrow(() -> new NoSuchUserException("User with id: " + userId + " not found."));
        return itemRepository.findAllByOwnerOrderById(owner);
    }

    @Transactional
    @Override
    public ItemDto createItem(Long userId, ItemDto itemDto) {
        User owner = userRepository.findById(userId)
                                 .orElseThrow(() -> new NoSuchUserException("User with id: " + userId + " not found."));
        Item item = ItemDtoMapper.toItem(itemDto, owner);
        return ItemDtoMapper.toItemDto(itemRepository.save(item));
    }

    @Transactional
    @Override
    public ItemDto updateItem(Long userId, long itemId, ItemDtoToUpdate itemDtoToUpdate) {

        if (itemRepository.findById(itemId).isPresent()) {
            Item itemForUpdate = itemRepository.findById(itemId).get();
            User owner = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchUserException("User with id: " + userId + " not found."));
            if (getUsersItemsForUpdateAndDelete(userId).stream()
                    .map(itemDto -> ItemDtoMapper.toItem(itemDto, owner))
                    .anyMatch(item -> item.getId() == itemId)) {
                    if (itemDtoToUpdate.getName() != null) {
                        itemForUpdate.setName(itemDtoToUpdate.getName());
                    }
                    if (itemDtoToUpdate.getDescription() != null) {
                        itemForUpdate.setDescription(itemDtoToUpdate.getDescription());
                    }
                    if (itemDtoToUpdate.isAvailable() != null) {
                        itemForUpdate.setAvailable(itemDtoToUpdate.isAvailable());
                    }
                    if (itemDtoToUpdate.getRequestId() != null) {
                        itemForUpdate.setRequestId(itemDtoToUpdate.getRequestId());
                    }
                return ItemDtoMapper.toItemDto(itemRepository.save(itemForUpdate));
            } else {
                throw new NoSuchItemException("Item with id: " + itemId + " for user with id: " + userId + " not found");
            }
        } else {
            throw new NoSuchItemException("Item with id: " + itemId + " not found.");
        }
    }

    @Transactional
    @Override
    public void deleteItem(long userId, long itemId) {
        if (itemRepository.findById(itemId).isPresent()) {
            User owner = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchUserException("User with id: " + userId + " not found."));
            if (getUsersItemsForUpdateAndDelete(userId).stream()
                    .map(itemDto -> ItemDtoMapper.toItem(itemDto, owner))
                    .anyMatch(item -> item.getId() == itemId)) {
                itemRepository.delete(itemRepository.findById(itemId).get());
            } else {
                throw new NoSuchItemException("Item with id: " + itemId + " for user with id: " + userId + " not found");
            }
        } else {
        throw new NoSuchItemException("Item with id: " + itemId + " not found.");
        }
    }

    @Override
    public Collection<ItemDto> searchItems(String text) {
        if (!text.isBlank() && !text.isEmpty()) {
            return itemRepository.searchItems(text).stream()
                    .map(ItemDtoMapper::toItemDto)
                    .filter(ItemDto::isAvailable)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public ItemDtoWithBookingsAndComments getItemById(long itemId, long userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchItemException("Item with id: " + itemId + " not found."));

        List<Booking> allItemsBookings = bookingRepository.findAllByItemOrderByStartDesc(item);
        Booking lastBooking = null;
        Booking nextBooking = null;
        List<CommentDto> comments = commentRepository.findAllByItemOrderById(item).stream()
                                                                                  .map(CommentMapper::toCommentDto)
                                                                                  .collect(Collectors.toList());
        LocalDateTime now = LocalDateTime.now();

        if (item.getOwner().getId() == userId) {
            if(!allItemsBookings.isEmpty()) {

                BookingByEndComparator bookingByEndComparator = new BookingByEndComparator();
                BookingByStartComparator bookingByStartComparator = new BookingByStartComparator();

                lastBooking = allItemsBookings.stream()
                        .filter(booking -> booking.getEnd().isBefore(now))
                        .min(bookingByEndComparator)
                        .orElse(null);
                nextBooking = allItemsBookings.stream()
                        .filter(booking -> booking.getStart().isAfter(now))
                        .max(bookingByStartComparator)
                        .orElse(null);
            }
        }


        return ItemDtoMapper.toItemDtoWithBookingsAndComments(item,
                                                   BookingMapper.toBookingDtoForItem(lastBooking),
                                                   BookingMapper.toBookingDtoForItem(nextBooking),
                                                   comments);

    }

    @Override
    public Collection<ItemDtoWithBookingsAndComments> getUsersItems(long userId) {
        return getItems(userId).stream()
               .map(item -> getItemById(item.getId(), userId))
               .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CommentDto addComment(Long userId, long itemId, CommentDto commentDto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchItemException("Item with id: " + itemId + " not found."));
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException("User with id: " + userId + " not found."));
        commentDto.setCreated(LocalDateTime.now());

        if (bookingRepository.findPastByBooker(author, LocalDateTime.now()).stream()
                .anyMatch(booking -> booking.getItem().getId().equals(itemId))) {
            return CommentMapper.toCommentDto(commentRepository.save(CommentMapper.toComment(commentDto, author, item)));
        } else {
            throw new CommentWithoutBookingException(
                    "Booking for user with id: " + userId + "and item with id: " + itemId + " not found.");
        }
    }

    private Collection<ItemDto> getUsersItemsForUpdateAndDelete (long userId) {
        return getItems(userId).stream()
                .map(ItemDtoMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
