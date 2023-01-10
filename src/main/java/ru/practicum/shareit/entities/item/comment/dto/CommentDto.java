package ru.practicum.shareit.entities.item.comment.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.shareit.entities.item.comment.model.Comment;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Jacksonized
@Builder
public class CommentDto {

    private Long id;
    private String text;
    private String authorName;
    private LocalDateTime created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        return id != null && id.equals(((Comment) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
