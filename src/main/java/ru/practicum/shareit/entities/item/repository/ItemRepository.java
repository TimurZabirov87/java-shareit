package ru.practicum.shareit.entities.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.entities.item.model.Item;
import ru.practicum.shareit.entities.user.model.User;

import java.util.Collection;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(" select i from Item i " +
                  "where upper(i.name) like upper(concat('%', ?1, '%')) " +
                  " or upper(i.description) like upper(concat('%', ?1, '%'))")
    List<Item> searchItems(String text);


    Collection<Item> findAllByOwnerOrderById(User owner);

}
