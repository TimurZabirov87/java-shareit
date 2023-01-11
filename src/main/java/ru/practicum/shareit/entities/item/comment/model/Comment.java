package ru.practicum.shareit.entities.item.comment.model;


import lombok.*;
import ru.practicum.shareit.entities.item.model.Item;
import ru.practicum.shareit.entities.user.model.User;
import ru.practicum.shareit.validator.ValidCommentsText;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments", schema = "public")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false, updatable = false, unique = true)
    private Long id;
    @ValidCommentsText
    @Column (name = "text", nullable = false, length = 512)
    private String text;
    @ManyToOne
    @JoinColumn (name = "item_id", referencedColumnName = "id")
    @NonNull
    private Item item;
    @ManyToOne
    @JoinColumn (name = "author_id", referencedColumnName = "id")
    @NonNull
    private User author;
    @Column (name = "created", nullable = false)
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
