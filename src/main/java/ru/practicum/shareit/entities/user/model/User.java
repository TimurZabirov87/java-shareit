package ru.practicum.shareit.entities.user.model;

import lombok.*;
import ru.practicum.shareit.entities.booking.model.Booking;
import ru.practicum.shareit.entities.item.comment.model.Comment;
import ru.practicum.shareit.entities.item.model.Item;
import ru.practicum.shareit.entities.request.model.ItemRequest;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "users", schema = "public")
@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;
    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;
    @Column(name = "email", nullable = false)
    @NotBlank
    @Email
    private String email;
    @OneToMany(mappedBy = "owner")
    private List<Item> items;
    @OneToMany(mappedBy = "requestor")
    private List<ItemRequest> requests;
    @OneToMany(mappedBy = "booker")
    private List<Booking> bookings;
    @OneToMany(mappedBy = "author")
    private List<Comment> comments;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return id != null && id.equals(((User) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
