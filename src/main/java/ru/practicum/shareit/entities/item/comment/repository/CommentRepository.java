package ru.practicum.shareit.entities.item.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.entities.item.comment.model.Comment;
import ru.practicum.shareit.entities.item.model.Item;
import ru.practicum.shareit.entities.user.model.User;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByItemOrderById(Item item);

    List<Comment> findAllByAuthorOrderById(User author);
}

