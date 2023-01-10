package ru.practicum.shareit.entities.request.model;

import lombok.*;
import ru.practicum.shareit.entities.user.model.User;
import ru.practicum.shareit.validator.ValidItemsDescription;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * id — уникальный идентификатор запроса;
 * description — текст запроса, содержащий описание требуемой вещи;
 * requestor — пользователь, создавший запрос;
 * created — дата и время создания запроса.
 */

@Entity
@Table(name = "requests", schema = "public")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor

public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, updatable = false, unique = true)
    private Long id;
    @Column(name = "description", nullable = false)
    @ValidItemsDescription
    private String description;
    @ManyToOne
    @JoinColumn (name="requestor_id", referencedColumnName = "id")
    private User requestor;
    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemRequest)) return false;
        return id != null && id.equals(((ItemRequest) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
