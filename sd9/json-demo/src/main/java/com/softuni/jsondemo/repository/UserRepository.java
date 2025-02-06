package com.softuni.jsondemo.repository;

import com.softuni.jsondemo.entity.Post;
import com.softuni.jsondemo.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(@NonNull @NotNull @Length(min = 2,max =50) String username);
}
