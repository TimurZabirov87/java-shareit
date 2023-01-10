package ru.practicum.shareit.entities.item.model;

import lombok.*;
import ru.practicum.shareit.entities.booking.model.Booking;
import ru.practicum.shareit.entities.item.comment.model.Comment;
import ru.practicum.shareit.entities.user.model.User;
import ru.practicum.shareit.validator.ValidItemsDescription;
import ru.practicum.shareit.validator.ValidItemsName;

import javax.persistence.*;
import java.util.List;


/**
 * id — уникальный идентификатор вещи;
 * name — краткое название;
 * description — развёрнутое описание;
 * available — статус о том, доступна или нет вещь для аренды;
 * owner — владелец вещи;
 * request — если вещь была создана по запросу другого пользователя, то в этом
 * поле будет храниться ссылка на соответствующий запрос
 */

@Entity
@Table(name = "items", schema = "public")
@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, updatable = false, unique = true)
    private Long id;
    @Column(name = "name", nullable = false)
    @ValidItemsName
    private String name;
    @Column(name = "description", nullable = false)
    @ValidItemsDescription
    private String description;
    @Column(name = "is_available", nullable = false)
    private Boolean available;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @Column(name = "request_id")
    private Long requestId;
    @OneToMany(mappedBy = "item")
    private List<Booking> bookings;
    @OneToMany(mappedBy = "item")
    private List<Comment> comments;


    public Item(String name, String description, Boolean available, User owner, Long requestId) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
        this.requestId = requestId;
    }

    public Item(Long id, String name, String description, Boolean available, User owner, Long requestId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
        this.requestId = requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        return id != null && id.equals(((Item) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    public Boolean isAvailable() {
        return available;
    }
}
