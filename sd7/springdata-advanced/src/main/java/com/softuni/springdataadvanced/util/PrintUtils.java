package com.softuni.springdataadvanced.util;

import com.softuni.springdataadvanced.entity.Ingredient;
import com.softuni.springdataadvanced.entity.Shampoo;

import java.util.stream.Collectors;

public class PrintUtils {

    public static void printShampoo(Shampoo s) {
        System.out.format("|%5d | %-20.20s |%-8.8s | %8.2f | %-40.40s | %-50.50s |%n",
                s.getId(), s.getBrand(), s.getSize(), s.getPrice(), s.getLabel().getTitle() + " - " +
                        s.getLabel().getSubtitle(),s.getIngredients().stream().map(Ingredient::getName)
                        .collect(Collectors.joining(", ")));
    }

    public static void printIngredients(Ingredient ingredient) {
        System.out.format("|%5d | %-30.30s |%-8.2f |%n",
                ingredient.getId(),ingredient.getName(),ingredient.getPrice());
    }
}
