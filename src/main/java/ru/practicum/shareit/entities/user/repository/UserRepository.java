package ru.practicum.shareit.entities.user.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.entities.user.model.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {


}
