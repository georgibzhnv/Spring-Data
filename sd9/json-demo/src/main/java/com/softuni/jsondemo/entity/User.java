package com.softuni.jsondemo.entity;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Expose
    @NonNull
    @NotNull
    @Length(max = 20)
    private String firstName;

    @Expose
    @NonNull
    @NotNull
    @Length(min = 3,max =20)
    private String lastName;

    @Expose
    @NonNull
    @NotNull
    @Length(min = 2,max =50)
    private String username;

    @Expose(serialize = false)
    @NonNull
    @NotNull
    @Length(min = 5,max =30)
    private String password;

    @Expose
    @NonNull
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Expose
    @NonNull
    @NotNull
    @URL
    private String imageUrl;

    @Expose
    private LocalDateTime created = LocalDateTime.now();
    @Expose
    private LocalDateTime modified = LocalDateTime.now();
}