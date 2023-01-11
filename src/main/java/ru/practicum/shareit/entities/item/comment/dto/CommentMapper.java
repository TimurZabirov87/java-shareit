package ru.practicum.shareit.entities.item.comment.dto;

import ru.practicum.shareit.entities.item.comment.model.Comment;
import ru.practicum.shareit.entities.item.model.Item;
import ru.practicum.shareit.entities.user.model.User;


public class CommentMapper {

    public static Comment toComment(CommentDto commentDto, User author, Item item) {
        return new Comment(
                commentDto.getId() != null ? commentDto.getId() : null,
                commentDto.getText(),
                item,
                author,
                commentDto.getCreated() != null ? commentDto.getCreated() : null
        );
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId() != null ? comment.getId() : null,
                comment.getText(),
                comment.getAuthor().getName(),
                comment.getCreated()
        );
    }
}
