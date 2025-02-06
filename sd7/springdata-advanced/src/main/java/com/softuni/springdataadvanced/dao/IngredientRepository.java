package com.softuni.springdataadvanced.dao;

import com.softuni.springdataadvanced.entity.Ingredient;
import com.softuni.springdataadvanced.entity.Shampoo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
    List<Ingredient> findIngredientsByNameIn(Iterable<String> names);

    int deleteAllByName(String name);

    Optional<Ingredient> findByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient i set i.price=i.price* (1.0+:percentage) where i.name IN :names")
    int updatePriceOfIngredientsInList(@Param("percentage")double percentage
            ,@Param("names")Iterable<String>ingredient_names);

}
