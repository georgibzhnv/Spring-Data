package com.softuni.springdataadvanced.dao;

import com.softuni.springdataadvanced.entity.Ingredient;
import com.softuni.springdataadvanced.entity.Label;
import com.softuni.springdataadvanced.entity.Shampoo;
import com.softuni.springdataadvanced.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo,Long> {
    List<Shampoo> findBySizeOrderById(Size size);

    List<Shampoo> findBySizeOrLabelOrderByPriceDesc(Size size, Label label);

    List<Shampoo> findByPriceGreaterThanEqual(double minPrice);

    List<Shampoo> findByPriceBetween(double minPrice,double maxPrice);

    int countShampoosByPriceLessThan(double price);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i WHERE i = :ingredient")
    List<Shampoo> findByIngredient(@Param("ingredient") Ingredient ingredient);

    @Query("select s from Shampoo s ,in(s.ingredients) i where i.name in :ingredients_names")
    List<Shampoo>findWithIngredientsInList(@Param("ingredients_names") Iterable<String> ingredients_names);

    @Query("select s from Shampoo s where SIZE(s.ingredients) < :count")
    List<Shampoo>findByCountOfIngredientsLowerThan(@Param("count") int maxCount);


}
