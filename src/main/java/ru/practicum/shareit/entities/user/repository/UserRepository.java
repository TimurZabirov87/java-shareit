package ru.practicum.shareit.entities.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.entities.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
